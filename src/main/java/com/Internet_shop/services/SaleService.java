package com.Internet_shop.services;

import com.Internet_shop.entities.Items;
import com.Internet_shop.entities.Sale;
import com.Internet_shop.entities.Users;

import java.util.List;

public interface SaleService {
    List<Sale> getAllSales();
    Sale getSaleByItem(Items items);
    Sale saveSale(Sale sale);
    void deleteSale(Long id);
    List<Users> getSaleUsers(Items items);
}
