package com.Internet_shop.services.implementations;

import com.Internet_shop.entities.Brands;
import com.Internet_shop.entities.Categories;
import com.Internet_shop.entities.Items;
import com.Internet_shop.repositories.ItemsRepository;
import com.Internet_shop.services.ItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemsServiceImplementation implements ItemsService {

    @Autowired
    private ItemsRepository repository;



    @Override
    public void createItem(Items item) {
        repository.save(item);
    }
    @Override
    public Items getItem(Long id) {
        return repository.getOne(id);
    }
    @Override
    public List<Items> getAllItems() {return repository.findAll();}

    @Override
    public List<Items> getItemsById(List<Long> id) {
        return repository.findAllById(id);
    }

    @Override
    public Items updateItem(Items item) {
        return repository.save(item);
    }
    @Override
    public void deleteItem(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<Items> defaultFilter(String text, double price1, double price2) {
        text = '%' + text + '%';
        return repository.findAllByNameLikeAndPriceBetweenOrderByPriceAsc(text, price1, price2);
    }

    @Override
    public List<Items> brandsFilter(String text, double price1, double price2, Brands brand) {
        text = '%' + text + '%';
        return repository.findAllByNameLikeAndPriceBetweenAndBrandIsOrderByPriceDesc(text, price1, price2, brand.getId());
    }

    @Override
    public List<Items> sortBy(boolean asc) {
        if (asc)
            return repository.findAllByOrderByPriceAsc();
        return repository.findAllByOrderByPriceDesc();
    }

    @Override
    public List<Items> byBrands(Brands brand) {
        return repository.findAllByBrandIs(brand);
    }

    @Override
    public List<Items> byCategories(Categories category) {
        return repository.findAllByCategories(category);
    }
}
