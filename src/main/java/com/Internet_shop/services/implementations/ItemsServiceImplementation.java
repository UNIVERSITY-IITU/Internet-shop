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


}
