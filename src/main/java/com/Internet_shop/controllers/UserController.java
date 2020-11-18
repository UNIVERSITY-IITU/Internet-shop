package com.Internet_shop.controllers;


import com.Internet_shop.services.BrandsService;
import com.Internet_shop.services.CategoriesService;
import com.Internet_shop.services.CountriesService;
import com.Internet_shop.services.ItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

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

    @GetMapping(value = "/")
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


        model.addAttribute("brands", brandsService.getAllBrands());
        model.addAttribute("categories", categoriesService.getAllCategories());
        return "index";
    }


    @GetMapping(value = "/device/{id}")
    public String device(Model model, @PathVariable(name = "id") Long id){
        model.addAttribute("device", itemsService.getItem(id));
        return "device";
    }
}
