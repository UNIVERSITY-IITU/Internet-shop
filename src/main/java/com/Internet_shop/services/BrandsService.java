package com.Internet_shop.services;

import com.Internet_shop.entities.Brands;

import java.util.List;

public interface BrandsService {
    List<Brands> getAllBrands();

    void insertBrand(Brands brand);

    Brands getBrand(Long id);

    Brands updateBrand(Brands brand);

    void deleteBrand(Long id);
}
