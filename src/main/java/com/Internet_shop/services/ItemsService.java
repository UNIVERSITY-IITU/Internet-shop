package com.Internet_shop.services;

import com.Internet_shop.entities.Brands;
import com.Internet_shop.entities.Categories;
import com.Internet_shop.entities.Items;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

public interface ItemsService {
    void createItem(Items item);

    List<Items> getAllItems();

    List<Items> getItemsById(List<Long> id);

    Items getItem(Long id);

    Items updateItem(Items item);

    void deleteItem(Long id);

    List<Items> defaultFilter(String text, double price1, double price2);

    List<Items> brandsFilter(String text, double price1, double price2, Brands brand);

    List<Items> sortBy(boolean asc);

    List<Items> byBrands(Brands brand);

    List<Items> byCategories(Categories category);

}
