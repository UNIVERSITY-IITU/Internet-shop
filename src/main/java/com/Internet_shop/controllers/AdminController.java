package com.Internet_shop.controllers;


import com.Internet_shop.entities.*;
import com.Internet_shop.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller(value = "/admin")
public class AdminController {

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
    private SaleService saleService;

    @GetMapping(value = "/admin/sales")
    public String adminSales(Model model){
        model.addAttribute("sales", saleService.getAllSales());
        return "admin_sales";
    }

    // READ
    @GetMapping(value = "/admin/devices")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    public String adminDevice(Model model){
        model.addAttribute("categories", categoriesService.getAllCategories());
        model.addAttribute("devices", itemsService.getAllItems());
        model.addAttribute("brands", brandsService.getAllBrands());
        model.addAttribute("page", "devices");
        return "admin_devices";
    }

    @GetMapping(value = "/admin/brands")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    public String adminBrand(Model model){
        model.addAttribute("brands", brandsService.getAllBrands());
        model.addAttribute("countries", countriesService.getAllCountries());
        model.addAttribute("page", "brands");
        return "admin_brands";
    }

    @GetMapping(value = "/admin/countries")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    public String adminCountry(Model model){
        model.addAttribute("countries", countriesService.getAllCountries());
        model.addAttribute("page", "countries");
        return "admin_countries";
    }

    @GetMapping(value = "/admin/categories")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    public String adminCategory(Model model){
        model.addAttribute("categories", categoriesService.getAllCategories());
        model.addAttribute("page", "categories");
        return "admin_categories";
    }

    @GetMapping(value = "/admin/users")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    public String adminUsers(Model model){
        model.addAttribute("users", usersService.getAllUsers());
        model.addAttribute("roles", rolesService.getAllRoles());
        model.addAttribute("page", "users");
        return "admin_users";
    }

    // CREATE
    @PostMapping(value = "admin/add_device")
    public String addDevice(
            @RequestParam(name = "name",                                required = false) String name,
            @RequestParam(name = "description",                         required = false) String description,
            @RequestParam(name = "price",                               required = false) Double price,
            @RequestParam(name = "stars",       defaultValue = "1",     required = false) int stars,
            @RequestParam(name = "smallPicURL",                         required = false) String smallPicURL,
            @RequestParam(name = "largePicURL",                         required = false) String largePicURL,
            @RequestParam(name = "inTopPage",   defaultValue = "false", required = false) boolean inTopPage,
            @RequestParam(name = "brand_id",    defaultValue = "",      required = false) Long brand_id,
            @RequestParam(name = "categories_id",                       required = false) List<Long> categories_id
    ){

        Brands brand = brandsService.getBrand(brand_id);
        if (brand != null)
            itemsService.updateItem(new Items(null,name,description,price,stars,smallPicURL,largePicURL,null,inTopPage, brand, categoriesService.getCategoriesById(categories_id)));
        return "redirect:/admin/devices";
    }

    @PostMapping(value = "admin/add_brand")
    private String addBrand(
            @RequestParam(name = "name",                                required = false) String name,
            @RequestParam(name = "countries_id",    defaultValue = "",  required = false) Long countries_id
    ){
        Countries country = countriesService.getCountry(countries_id);
        if (country!=null)
            brandsService.insertBrand(new Brands(null,name, country));
        return "redirect:/admin/brands";
    }

    @PostMapping(value = "admin/add_country")
    private String addCountry(
            @RequestParam(name = "name",                                required = false) String name,
            @RequestParam(name = "code",    defaultValue = "",          required = false) String code
    ){
        countriesService.updateCountry(new Countries(null,name, code));
        return "redirect:/admin/countries";
    }

    @PostMapping(value = "admin/add_category")
    private String addCategory(
            @RequestParam(name = "name",                                required = false) String name,
            @RequestParam(name = "logoURL",                                required = false) String logoURL
    ){
        categoriesService.updateCategory(new Categories(null,name, logoURL));
        return "redirect:/admin/categories";
    }

    @PostMapping(value = "admin/add_users")
    private String addUser(
            @RequestParam(name = "email",                                required = false) String email,
            @RequestParam(name = "password",                                required = false) String password,
            @RequestParam(name = "fullname",                                required = false) String fullname,
            @RequestParam(name = "roles_id",                       required = false) List<Long> roles_id
    ){
        System.out.println(email + password + fullname);
        try {
            assert roles_id.size() != 0;
            List<Roles> user_roles = rolesService.getRolesByID(roles_id);
            System.out.println(user_roles + "-----------");
            assert user_roles.size() != 0;
            Users users = new Users(null,email, password, fullname, user_roles, null);
//            assert usersService.createAdminUsers(users)!=null;
            usersService.createAdminUsers(users);
            return "redirect:/admin/users?status=success";
        }catch (Exception e){
            e.printStackTrace();
            return "redirect:/admin/users?status=error";
        }


    }


    // DELETE
    @PostMapping(value = "admin/{page}/delete")
    public String delete(@RequestParam(name = "id",                     required = false) Long id,
                         @PathVariable(name = "page")                   String page){

        switch (page) {
            case "devices":
                itemsService.deleteItem(id);
                break;
            case "brands":
                brandsService.deleteBrand(id);
                break;
            case "countries":
                countriesService.deleteCountry(id);
                break;
            case "categories":
                categoriesService.deleteCategory(id);
                break;
            case "users":
                usersService.deleteUserById(id);
        }
        return "redirect:/admin/" + page;
    }

    // UPDATE
    @PostMapping(value = "/admin/update_device")
    public String updateDevice(
            @RequestParam(name = "id",                                  required = false) Long id,
            @RequestParam(name = "name",                                required = false) String name,
            @RequestParam(name = "description",                         required = false) String description,
            @RequestParam(name = "price",                               required = false) Double price,
            @RequestParam(name = "stars",       defaultValue = "1",     required = false) int stars,
            @RequestParam(name = "smallPicURL",                         required = false) String smallPicURL,
            @RequestParam(name = "largePicURL",                         required = false) String largePicURL,
            @RequestParam(name = "inTopPage",   defaultValue = "false", required = false) boolean inTopPage,
            @RequestParam(name = "brand_id",    defaultValue = "",      required = false) Long brand_id,
            @RequestParam(name = "categories_id",                       required = false) List<Long> categories_id
    ){
        Brands brand = brandsService.getBrand(brand_id);


        if (brand != null){
            itemsService.updateItem(new Items(id,name,description,price,stars,smallPicURL,largePicURL,null,inTopPage, brand, categoriesService.getCategoriesById(categories_id)));
        }
        return "redirect:/admin/devices";
    }

    @PostMapping(value = "admin/update_brand")
    public String updateBrand(
            @RequestParam(name = "id",                                  required = false) Long id,
            @RequestParam(name = "name",                                required = false) String name,
            @RequestParam(name = "countries_id",    defaultValue = "",  required = false) Long countries_id
    ){
        brandsService.updateBrand(new Brands(id, name, countriesService.getCountry(countries_id)));
        return "redirect:/admin/brands";
    }

    @PostMapping(value = "admin/update_country")
    private String updateCountry(
            @RequestParam(name = "id",                                  required = false) Long id,
            @RequestParam(name = "name",                                required = false) String name,
            @RequestParam(name = "code",    defaultValue = "",          required = false) String code
    ){
        countriesService.updateCountry(new Countries(id, name, code));
        return "redirect:/admin/countries";
    }

    @PostMapping(value = "admin/update_category")
    private String updateCategory(
            @RequestParam(name = "id",                                  required = false) Long id,
            @RequestParam(name = "name",                                required = false) String name,
            @RequestParam(name = "logoURL",                             required = false) String logoURL
    ){
        categoriesService.updateCategory(new Categories(id,name, logoURL));
        return "redirect:/admin/categories";
    }

}
