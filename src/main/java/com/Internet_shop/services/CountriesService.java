package com.Internet_shop.services;



import com.Internet_shop.entities.Countries;

import java.util.List;

public interface CountriesService {
    List<Countries> getAllCountries();


    Countries getCountry(Long id);

    Countries updateCountry(Countries country);

    void deleteCountry(Long id);
}
