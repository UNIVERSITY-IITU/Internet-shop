package com.Internet_shop.services;

import com.Internet_shop.entities.Categories;

import java.util.List;

public interface CategoriesService {

    List<Categories> getCategoriesById(List<Long> id);

    List<Categories> getAllCategories();

    Categories getCategory(Long id);

    Categories updateCategory(Categories category);

    void deleteCategory(Long id);
}
