package com.Internet_shop.services.implementations;

import com.Internet_shop.entities.Categories;
import com.Internet_shop.repositories.CategoriesRepository;
import com.Internet_shop.services.CategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriesServiceImplementation implements CategoriesService {
    @Autowired
    CategoriesRepository repository;


    @Override
    public List<Categories> getCategoriesById(List<Long> id) {
        return repository.findAllByIdIn(id);
    }

    @Override
    public List<Categories> getAllCategories() {
        return repository.findAll();
    }

    @Override
    public Categories getCategory(Long id) {
        return repository.getOne(id);
    }

    @Override
    public Categories updateCategory(Categories category) {
        return repository.save(category);
    }

    @Override
    public void deleteCategory(Long id) {
        repository.deleteById(id);
    }
}
