package com.Internet_shop.services;

import com.Internet_shop.entities.Items;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

public interface ItemsService {
    void createItem(Items item);

    List<Items> getAllItems();


    Items getItem(Long id);

    Items updateItem(Items item);

    void deleteItem(Long id);

    List<Items> filter(String name, double price1, double price2, boolean asc);

    List<Items> sort(boolean asc);
}
