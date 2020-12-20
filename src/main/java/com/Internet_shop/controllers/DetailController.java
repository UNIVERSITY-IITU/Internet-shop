package com.Internet_shop.controllers;

import com.Internet_shop.entities.*;
import com.Internet_shop.services.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller(value = "/detail")
public class DetailController {
    @Autowired
    private ItemsService itemsService;

    @Autowired
    private BrandsService brandsService;

    @Autowired
    private CountriesService countriesService;

    @Autowired
    private CategoriesService categoriesService;

    @Autowired
    private UsersService usersService;

    @Autowired
    private RolesService rolesService;

    @Autowired
    private PicturesService picturesService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Value("${file.images.viewPath}")
    private String viewPath;

    @Value("${file.images.uploadPath}")
    private String uploadPath;

    @Value("${file.images.defaultImage}")
    private String defaultImage;


    @GetMapping(value = "/detail/devices/{id}")
    public String deviceDetail(Model model, @PathVariable(name = "id") Long id){
        try {
            Items items = itemsService.getItem(id);
            assert items!= null;
            List<Pictures> itemPictures = picturesService.getPictures(items);
            List<Brands> allBrands = brandsService.getAllBrands();
            allBrands.remove(items.getBrand());
            List<Categories> allCategories = categoriesService.getAllCategories();
            List<Categories> itemCategories = items.getCategories();

            for (Categories c:itemCategories) {
                for (Categories i: allCategories) {
                    if (c.equals(i)){
                        allCategories.remove(c);
                        break;
                    }
                }
            }
            model.addAttribute("allCategories", allCategories);
            model.addAttribute("itemCategories", itemCategories);
            model.addAttribute("items", items);
            model.addAttribute("brands", allBrands);
            model.addAttribute("itemPictures", itemPictures);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "admin_devices_detail";
    }

    @GetMapping(value = "/detail/users/{id}")
    public String userDetail(Model model, @PathVariable(name = "id") Long id){
        Users users = usersService.getUserById(id);
        List<Roles> allRoles = rolesService.getAllRoles();
        List<Roles> userRoles = users.getRoles();

        for (Roles user_role: userRoles) {
            for (Roles all_role: allRoles) {
                if (all_role.equals(user_role)){
                    allRoles.remove(user_role);
                    break;
                }
            }
        }

        model.addAttribute("users", users);
        model.addAttribute("userRoles", userRoles);
        model.addAttribute("unregisteredRoles", allRoles);
        return "admin_users_detail";
    }

    @PostMapping(value = "/detail/profile/change_password")
    public String changePassword(@RequestParam(name = "old_password") String old_password, @RequestParam(name = "new_password") String new_password){
        Users current_user = getUserData();
        assert passwordEncoder.matches(current_user.getPassword(), old_password);
        System.out.println("+++++++++++++ " + new_password + " +++++++++++");
        current_user.setPassword(passwordEncoder.encode(new_password));
        usersService.updateUser(current_user);
        return "redirect:/profile";
    }

    @PostMapping(value = "/detail/devices/add_comment")
    public String addComment(Model model, @RequestParam(name = "comment_text") String comment_text, @RequestParam(name = "item_id") Long item_id){
        try {
            commentService.saveComment(new Comment(null, comment_text, null, itemsService.getItem(item_id), getUserData()));
        }catch (Exception e){
            e.printStackTrace();
        }
        return "redirect:/device/" + item_id;
    }

    @PostMapping(value = "/detail/devices/edit_comment")
    public String editComment(Model model,@RequestParam(name = "comment_id") Long comment_id, @RequestParam(name = "comment_text") String comment_text, @RequestParam(name = "item_id") Long item_id){

        try{
            Comment comment = commentService.getCommentById(comment_id);
            comment.setComment(comment_text);
            commentService.saveComment(comment);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "redirect:/device/" + item_id;
    }

    @PostMapping(value = "/detail/devices/remove_comment")
    public String editComment(@RequestParam(name = "comment_id") Long comment_id, @RequestParam(name = "item_id") Long item_id){
        try{
            commentService.deleteComment(comment_id);
        }catch (Exception e){
            e.printStackTrace();
        }

        return "redirect:/device/" + item_id;
    }

    @PostMapping(value = "/detail/devices/update_device")
    public String updateDevice(@RequestParam(name = "id") Long id,@RequestParam(name = "name") String name,@RequestParam(name = "description") String description,@RequestParam(name = "price") Double price,@RequestParam(name = "stars") int stars,@RequestParam(name = "smallPicURL") String smallPicURL,@RequestParam(name = "largePicURL") String largePicURL,@RequestParam(name = "brand_id") Long brand_id,@RequestParam(name = "inTopPage") boolean inTopPage){
        try{
            Brands brand = brandsService.getBrand(brand_id);
            if (brand != null){
                Items items = itemsService.getItem(id);
                itemsService.updateItem(new Items(id,name,description,price,stars,smallPicURL,largePicURL,null,inTopPage, brand, items.getCategories()));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return "redirect:/detail/devices/" + id;
    }

    @PostMapping(value = "/detail/users/remove_role")
    public String removeUserRole(Model model, @RequestParam(name = "user_id") Long user_id, @RequestParam(name = "role_id") Long role_id){
        try{
            Roles roles = rolesService.getRoleById(role_id);
            Users users = usersService.getUserById(user_id);
            assert roles!=null && users != null;
            List<Roles> userRoles = users.getRoles();
            userRoles.remove(roles);
            users.setRoles(userRoles);
            usersService.createAdminUsers(users);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "redirect:/detail/users/" + user_id;
    }

    @PostMapping(value = "/detail/users/add_role")
    public String addUserRole(Model model, @RequestParam(name = "user_id") Long user_id, @RequestParam(name = "role_id") Long role_id){
        try {
            Roles roles = rolesService.getRoleById(role_id);
            Users users = usersService.getUserById(user_id);
            assert roles!=null && users != null;
            List<Roles> userRoles = users.getRoles();
            userRoles.add(roles);
            users.setRoles(userRoles);
            usersService.createAdminUsers(users);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "redirect:detail/users/" + user_id;
    }

    @PostMapping(value = "/detail/devices/remove_picture")
    public String removeDevicePicture(@RequestParam(name = "picture_id") Long picture_id, @RequestParam(name = "item_id") Long item_id){
        try{
            picturesService.removePicture(picture_id);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "redirect:/detail/devices/" + item_id;
    }

    @PostMapping(value = "/detail/devices/remove_category")
    public String removeItemCategory(@RequestParam(name = "category_id") Long category_id, @RequestParam("item_id") Long item_id){
        try {
            Items items = itemsService.getItem(item_id);
            Categories categories = categoriesService.getCategory(category_id);
            List<Categories> itemsCategories = items.getCategories();
            itemsCategories.remove(categories);
            items.setCategories(itemsCategories);
            itemsService.updateItem(items);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "redirect:/detail/devices/" + item_id;
    }

    @PostMapping(value = "/detail/devices/add_category")
    public String addItemCategory(@RequestParam(name = "category_id") Long category_id, @RequestParam("item_id") Long item_id){
        try{
            Items items = itemsService.getItem(item_id);
            Categories categories = categoriesService.getCategory(category_id);
            List<Categories> itemsCategories = items.getCategories();
            itemsCategories.add(categories);
            items.setCategories(itemsCategories);
            itemsService.updateItem(items);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "redirect:/detail/devices/" + item_id;
    }

    public Users getUserData(){
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            assert !(authentication instanceof AnonymousAuthenticationToken);
            User secUser = (User)authentication.getPrincipal();
            Users users = usersService.getUserByEmail(secUser.getUsername());
            return users;
        }catch (Exception e){
            return null;
        }
    }
}
