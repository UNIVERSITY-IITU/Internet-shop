package com.Internet_shop.services.implementations;

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
    public List<Items> getAllItems() {
        return repository.findAllByOrderByInTopPageDesc();
    }

    @Override
    public Items getItem(Long id) {
        return repository.getOne(id);
    }

    @Override
    public Items updateItem(Items item) {
        return repository.save(item);
    }

    @Override
    public void deleteItem(Long id) {
        repository.deleteByIdEquals(id);
    }

    @Override
    public List<Items> filter(String name, double price1, double price2, boolean asc) {

        if (asc){
            if (name.length() != 0)
                return repository.findAllByNameLikeAndPriceBetweenOrderByPriceAsc(name, price2, price1);
            return repository.findAllByPriceBetweenOrderByPriceAsc(price2, price1);

        }else {
            if (name.length() != 0)
                return repository.findAllByNameLikeAndPriceBetweenOrderByPriceDesc(name, price2, price1);
            return repository.findAllByPriceBetweenOrderByPriceDesc(price2, price1);
        }
    }

    @Override
    public List<Items> sort(boolean asc) {
        if (asc)
            return repository.findAllByOrderByPriceAsc();
        return repository.findAllByOrderByPriceDesc();
    }


}
