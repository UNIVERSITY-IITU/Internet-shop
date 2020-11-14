package com.Internet_shop.services.implementations;

import com.Internet_shop.entities.Brands;
import com.Internet_shop.repositories.BrandsRepository;
import com.Internet_shop.services.BrandsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandsServiceImplementation implements BrandsService {
    @Autowired
    BrandsRepository repository;



    @Override
    public List<Brands> getAllBrands() {
        return repository.findAll();
    }

    @Override
    public void insertBrand(Brands brand) {
        repository.save(brand);
    }

    @Override
    public Brands getBrand(Long id) {
        return repository.getOne(id);
    }

    @Override
    public Brands updateBrand(Brands brand) {
        return repository.save(brand);
    }

    @Override
    public void deleteBrand(Long id) {
        repository.deleteById(id);
    }
}
