package com.Internet_shop.services.implementations;

import com.Internet_shop.entities.Items;
import com.Internet_shop.entities.Sale;
import com.Internet_shop.entities.Users;
import com.Internet_shop.repositories.SaleRepository;
import com.Internet_shop.services.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SaleServiceImplementation implements SaleService{

    @Autowired
    SaleRepository repository;


    @Override
    public List<Sale> getAllSales() {
        return repository.findAll();
    }

    @Override
    public Sale getSaleByItem(Items items) {
        return repository.findByItems_Id(items.getId());
    }

    @Override
    public Sale saveSale(Sale sale) {
        return repository.save(sale);
    }

    @Override
    public void deleteSale(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<Users> getSaleUsers(Items items) {
        return repository.findAllByItems_Id(items.getId());
    }
}
