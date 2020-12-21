package com.Internet_shop.controllers;


import com.Internet_shop.entities.Items;
import com.Internet_shop.entities.Pictures;
import com.Internet_shop.entities.Sale;
import com.Internet_shop.entities.Users;
import com.Internet_shop.services.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller()
public class UserController {
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
    private PicturesService picturesService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private HttpSession httpSession;

    @Autowired
    private SaleService saleService;

    @Value("${file.images.viewPath}")
    private String viewPath;

    @Value("${file.images.uploadPath}")
    private String uploadPath;

    @Value("${file.images.defaultImage}")
    private String defaultImage;

    public Map<Long, Long>  readAllCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        Map<Long, Long> dictionary = new HashMap<>();
        if (cookies!=null) {
            for (Cookie cookie : cookies) {
                dictionary.put(Long.parseLong(cookie.getName()), Long.parseLong(cookie.getValue()));
            }
            System.out.println(dictionary + "+++++++++++++++++++++");
            return dictionary;
        }

        System.out.println("------------------");
        return null;
    }

    @GetMapping(value = "/")
    @PreAuthorize("isAuthenticated()")
    public String index(Model model,

            @RequestParam(name = "text",    defaultValue = "",              required = true) String text,
            @RequestParam(name = "brand_id",    defaultValue = "0",              required = true) Long brand_id,
            @RequestParam(name = "category_id",    defaultValue = "0",              required = true) Long category_id,
            @RequestParam(name = "from",    defaultValue = "0.0",             required = true) double from,
            @RequestParam(name = "to",      defaultValue = "100000000.0", required = true) double to,
            @RequestParam(name = "order",   defaultValue = "",             required = true) String order
    ){

        try {
            if (brand_id>0){
                System.out.println("1");
                model.addAttribute("devices", itemsService.brandsFilter(text, from, to, brandsService.getBrand(brand_id)));
            }else if(category_id>0){
                System.out.println("2");
                model.addAttribute("devices", itemsService.byCategories(categoriesService.getCategory(category_id)));
            }else if (!order.isEmpty()){
                model.addAttribute("devices", itemsService.sortBy(Boolean.parseBoolean(order)));
            }
            else{
                System.out.println("3");
                model.addAttribute("devices", itemsService.defaultFilter(text, from, to));
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        Map<Long, Long> session_items = (Map<Long, Long>) httpSession.getAttribute("basket");
        if (session_items!=null){
            model.addAttribute("basket_size", session_items.size());
        }else{
            model.addAttribute("basket_size", 0);
        }
        model.addAttribute("currentUser", getUserData());
        model.addAttribute("brands", brandsService.getAllBrands());
        model.addAttribute("categories", categoriesService.getAllCategories());
        return "index";
    }


    @GetMapping(value = "/device/{id}")
    @PreAuthorize("isAuthenticated()")
    public String device(Model model, @PathVariable(name = "id") Long id){
        Map<Long, Long> session_items = (Map<Long, Long>) httpSession.getAttribute("basket");
        Items items = itemsService.getItem(id);
        List<Pictures> picturesList = picturesService.getPictures(items);

        if (session_items!=null){
            model.addAttribute("basket_size", session_items.size());
        }else{
            model.addAttribute("basket_size", 0);
        }
        System.out.println(commentService.getCommentsByItem(items));
        model.addAttribute("comments", commentService.getCommentsByItem(items));
        model.addAttribute("picturesList", picturesList);
        model.addAttribute("currentUser", getUserData());
        model.addAttribute("device", items);
        model.addAttribute("brands", brandsService.getAllBrands());
        model.addAttribute("categories", categoriesService.getAllCategories());
        return "device";
    }

    @GetMapping(value = "/basket")
    public String basket(Model model, HttpServletRequest request){
        Map<Long, Long> session_items = (Map<Long, Long>) httpSession.getAttribute("basket");
        final Map<Items, Long> itemsLongMap = new HashMap<>();
        final double[] totalPrice = {0.0};
        try{
            assert session_items!=null;
            session_items.forEach((k,v)->itemsLongMap.put(itemsService.getItem(k), v));
            session_items.forEach((k,v)-> totalPrice[0] += (v *(itemsService.getItem(k.longValue()).getPrice())));
        }catch (Exception e){
            e.printStackTrace();
        }

        if (session_items!=null){
            model.addAttribute("basket_size", session_items.size());
        }else{
            model.addAttribute("basket_size", 0);
        }

        model.addAttribute("totalPrice", totalPrice[0]);
        model.addAttribute("items", itemsLongMap);
        model.addAttribute("currentUser", getUserData());
        model.addAttribute("brands", brandsService.getAllBrands());
        model.addAttribute("categories", categoriesService.getAllCategories());
        return "basket";
    }


    @PostMapping(value = "/device/add_sale")
    public String addSale(){
        Map<Long, Long> session_items = (Map<Long, Long>) httpSession.getAttribute("basket");
        Users currentUser = getUserData();
        try{
            assert session_items!=null;
            List<Users> users = new ArrayList<>();
            users.add(currentUser);

            for (Long item_id : session_items.keySet()) {
                Items items = itemsService.getItem(item_id);
                Sale sale = saleService.getSaleByItem(items);
                if (sale!=null){
                    Long amount = sale.getAmount();
                    amount += session_items.get(item_id);
                    List<Users> saleUsers = sale.getUsers();
                    if (!saleUsers.contains(currentUser)) {
                        saleUsers.add(currentUser);
                    }
                    sale.setUsers(saleUsers);
                    sale.setAmount(amount);
                }else {
                    sale = new Sale(null, session_items.get(item_id),null, items, users);
                }
                saleService.saveSale(sale);
            }
            httpSession.setAttribute("basket", null);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "redirect:/basket";
    }

    @PostMapping(value = "/device/add_basket")
    public String addBasket(Model model,HttpServletRequest request, HttpServletResponse response, @RequestParam(name = "item_id") Long item_id){
        Map<Long, Long> session_items = (HashMap) httpSession.getAttribute("basket");
        System.out.println("_______________");
        System.out.println(session_items);
        try{
            assert session_items!=null;
            Long amount = session_items.get(item_id);
            if (amount!=null){
                amount++;
                session_items.put(item_id, amount);
            }else{
                session_items.put(item_id, 1L);
            }
        }catch (Exception e){
            e.printStackTrace();
            session_items = new HashMap<>();
            session_items.put(item_id, 1L);
        }

        model.addAttribute("currentUser", getUserData());
        httpSession.setAttribute("basket", session_items);
        return "redirect:/device/" + item_id;
    }

    @PostMapping(value = "/device/clean_basket")
    public String cleanBasket(){
        httpSession.setAttribute("basket", null);
        return "redirect:/basket";
    }

    @PostMapping(value = "/device/plus_basket")
    public String plusBasket(HttpServletRequest request, HttpServletResponse response, @RequestParam(name = "item_id") Long item_id){
        Map<Long, Long> session_items = (Map<Long, Long>) httpSession.getAttribute("basket");
        if (session_items!=null){
            Long amount = session_items.get(item_id);
            amount++;
            session_items.put(item_id, amount);
            httpSession.setAttribute("basket", session_items);
        }
        return "redirect:/basket";
    }

    @PostMapping(value = "/device/minus_basket")
    public String minusBasket(HttpServletRequest request, HttpServletResponse response, @RequestParam(name = "item_id") Long item_id) {
        Map<Long, Long> session_items = (Map<Long, Long>) httpSession.getAttribute("basket");
        if (session_items!=null){
            Long amount = session_items.get(item_id);
            if (amount>1){
                amount--;
                session_items.put(item_id, amount);
            }else {
                session_items.remove(item_id);
            }
            httpSession.setAttribute("basket", session_items);
        }
        return "redirect:/basket";
    }

    @GetMapping(value = "/403")
    private String accessDenied(Model model){
        model.addAttribute("currentUser", getUserData());
        return "403";
    }

    @GetMapping(value = "/login")
    private String login(Model model){
        model.addAttribute("currentUser", getUserData());
        return "login";
    }

    @GetMapping(value = "/register")
    private String registerPage(Model model){
        model.addAttribute("currentUser", getUserData());
        return "register";
    }

    @PostMapping(value = "/register")
    private String registerPost(Model model,
                                @RequestParam(name = "user_email") String email,
                                @RequestParam(name = "user_password") String password,
                                @RequestParam(name = "user_fullname") String fullname){

        Users users = new Users(null,email, password, fullname, null, null);
        if (usersService.createUsers(users)!=null){
            return "redirect:/register?statuc=success";
        }

        return "redirect:/register?statuc=error";
    }


    @GetMapping(value = "/profile")
    public String profilePage(Model model){
        Map<Long, Long> session_items = (Map<Long, Long>) httpSession.getAttribute("basket");
        if (session_items!=null) {
            model.addAttribute("basket_size", session_items.size());
        }else{
            model.addAttribute("basket_size", 0);
        }
        model.addAttribute("currentUser", getUserData());
        model.addAttribute("brands", brandsService.getAllBrands());
        model.addAttribute("categories", categoriesService.getAllCategories());
        return "profile";
    }

    @PostMapping(value = "/upload_image")
    public String uploadImage(@RequestParam(name = "image") MultipartFile file){
        try{
            assert file.getContentType().equals("image/jpeg");
            assert file.getContentType().equals("image/png");
            byte[] bytes = file.getBytes();
            Users currentUser = getUserData();
            String hash = DigestUtils.sha1Hex("image_" + currentUser.getId());
            Path path = Paths.get(uploadPath +  hash + ".jpg");

            Files.write(path, bytes);
            currentUser.setImageUrl(hash);

            return "redirect:/profile";
        }catch (Exception e){
            e.printStackTrace();
        }
        return "redirect:/403";
    }

    @PostMapping(value = "/upload_device_image")
    public String uploadImage(@RequestParam(name = "image") MultipartFile file, @RequestParam(name = "item_id") Long item_id){
        try{
            String uuid = UUID.randomUUID().toString();
            assert file.getContentType().equals("image/jpeg");
            assert file.getContentType().equals("image/png");
            byte[] bytes = file.getBytes();
            Items items = itemsService.getItem(item_id);
            String hash = DigestUtils.sha1Hex("image_" + uuid);
            Path path = Paths.get(uploadPath +  hash + ".jpg");
            Files.write(path, bytes);

            Pictures pictures = new Pictures(null, hash, null, items);
            picturesService.addPictures(pictures);
            return "redirect:/detail/devices/" + item_id;
        }catch (Exception e){
            e.printStackTrace();
        }
        return "redirect:/403";
    }

    @GetMapping(value = "/view_image/{url}", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] viewProfileImage(@PathVariable(name = "url") String url) throws IOException {
        InputStream in;
        String image_url = viewPath + url + ".jpg";
        try{
            assert url != null;
            System.out.println(url + " image url");
            ClassPathResource resource = new ClassPathResource(image_url);
            in = resource.getInputStream();
        }catch (Exception e){
            ClassPathResource resource = new ClassPathResource(viewPath + defaultImage);
            in = resource.getInputStream();
            e.printStackTrace();
        }
        return IOUtils.toByteArray(in);
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

