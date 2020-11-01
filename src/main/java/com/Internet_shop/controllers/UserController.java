package com.Internet_shop.controllers;


import com.Internet_shop.services.ItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller()
public class UserController {
    @Autowired
    ItemsService itemsService;

    @GetMapping(value = "/")
    public String index(Model model){
        model.addAttribute("devices", itemsService.getAllItems());
        return "index";
    }

    @GetMapping(value = "/device/{id}")
    public String device(Model model, @PathVariable(name = "id") Long id){
        model.addAttribute("device", itemsService.getItem(id));
        return "device";
    }
}
