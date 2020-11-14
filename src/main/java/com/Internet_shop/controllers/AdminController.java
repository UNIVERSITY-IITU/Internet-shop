package com.Internet_shop.controllers;


import com.Internet_shop.entities.Brands;
import com.Internet_shop.entities.Categories;
import com.Internet_shop.entities.Countries;
import com.Internet_shop.entities.Items;
import com.Internet_shop.services.BrandsService;
import com.Internet_shop.services.CategoriesService;
import com.Internet_shop.services.CountriesService;
import com.Internet_shop.services.ItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    // READ
    @GetMapping(value = "/admin/devices")
    public String adminDevice(Model model){
        model.addAttribute("categories", categoriesService.getAllCategories());
        model.addAttribute("devices", itemsService.getAllItems());
        model.addAttribute("brands", brandsService.getAllBrands());
        model.addAttribute("page", "devices");
        return "admin_devices";
    }

    @GetMapping(value = "/admin/brands")
    public String adminBrand(Model model){
        model.addAttribute("brands", brandsService.getAllBrands());
        model.addAttribute("countries", countriesService.getAllCountries());
        model.addAttribute("page", "brands");
        return "admin_brands";
    }

    @GetMapping(value = "/admin/countries")
    public String adminCountry(Model model){
        model.addAttribute("countries", countriesService.getAllCountries());
        model.addAttribute("page", "countries");
        return "admin_countries";
    }

    @GetMapping(value = "/admin/categories")
    public String adminCategory(Model model){
        model.addAttribute("categories", categoriesService.getAllCategories());
        model.addAttribute("page", "categories");
        return "admin_categories";
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
