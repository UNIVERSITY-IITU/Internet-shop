package com.Internet_shop.controllers;


import com.Internet_shop.entities.Items;
import com.Internet_shop.services.ItemsService;
import com.Internet_shop.services.implementations.ItemsServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller(value = "/admin")
public class AdminController {

    @Autowired
    private ItemsService itemsService;

    @GetMapping(value = "/admin")
    public String admin(Model model){
        model.addAttribute("devices", itemsService.getAllItems());
        return "admin";
    }

    @PostMapping(value = "/admin/add_device")
    public String addDevice(
            @RequestParam(name = "name",                                required = false) String name,
            @RequestParam(name = "description",                         required = false) String description,
            @RequestParam(name = "price",                               required = false) Double price,
            @RequestParam(name = "stars",       defaultValue = "1",     required = false) int stars,
            @RequestParam(name = "smallPicURL",                         required = false) String smallPicURL,
            @RequestParam(name = "largePicURL",                         required = false) String largePicURL,
            @RequestParam(name = "inTopPage",   defaultValue = "false", required = false) boolean inTopPage
    ){
        itemsService.createItem(new Items(name,description,price,stars,smallPicURL,largePicURL,inTopPage));
        return "redirect:/admin";
    }

    @PostMapping(value = "/admin/delete_device")
    public String deleteDevice(
            @RequestParam(name = "id",                                  required = false) Long id
    ){
        itemsService.deleteItem(id);
        return "redirect:/admin";
    }

    @PostMapping(value = "/admin/update_device")
    public String updateDevice(
            @RequestParam(name = "id",                                  required = false) Long id,
            @RequestParam(name = "name",                                required = false) String name,
            @RequestParam(name = "description",                         required = false) String description,
            @RequestParam(name = "price",                               required = false) Double price,
            @RequestParam(name = "stars",       defaultValue = "1",     required = false) int stars,
            @RequestParam(name = "smallPicURL",                         required = false) String smallPicURL,
            @RequestParam(name = "largePicURL",                         required = false) String largePicURL,
            @RequestParam(name = "inTopPage",   defaultValue = "false", required = false) boolean inTopPage
    ){
        itemsService.updateItem(new Items(id,name,description,price,stars,smallPicURL,largePicURL,inTopPage));
        return "redirect:/admin";
    }
}
