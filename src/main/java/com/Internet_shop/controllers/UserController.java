package com.Internet_shop.controllers;


import com.Internet_shop.services.BrandsService;
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

    @GetMapping(value = "/")
    public String index(Model model,
            @RequestParam(name = "search",  defaultValue = "",             required = true) String search,
            @RequestParam(name = "text",    defaultValue = "",              required = true) String text,
            @RequestParam(name = "brand_id",    defaultValue = "",              required = true) Long brand_id,
            @RequestParam(name = "from",    defaultValue = "0.0",             required = true) double from,
            @RequestParam(name = "to",      defaultValue = "100000000.0", required = true) double to,
            @RequestParam(name = "order",   defaultValue = "",             required = true) String order
    ){
        boolean order_asc = order.equals("asc");

        if (search.equals("true")){
            System.out.println("\n\n"+itemsService.filter(text, to, from, order_asc) + "\n\n");
            model.addAttribute("devices", itemsService.filter(text, to, from, order_asc));
        }else if (!order.isEmpty()){
            model.addAttribute("devices", itemsService.sort(order_asc));
        }else {
            model.addAttribute("devices", itemsService.getAllItems());
        }

        model.addAttribute("brands", brandsService.getAllBrands());
        return "index";
    }


    @GetMapping(value = "/device/{id}")
    public String device(Model model, @PathVariable(name = "id") Long id){
        model.addAttribute("device", itemsService.getItem(id));
        return "device";
    }
}
