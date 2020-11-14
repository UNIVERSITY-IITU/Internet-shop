package com.Internet_shop.services.implementations;

import com.Internet_shop.entities.Countries;
import com.Internet_shop.repositories.CountriesRepository;
import com.Internet_shop.services.CountriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountriesServiceImplementation implements CountriesService {

    @Autowired
    CountriesRepository repository;

    @Override
    public List<Countries> getAllCountries() {
        return repository.findAll();
    }

    @Override
    public Countries getCountry(Long id) {
        return repository.getOne(id);
    }

    @Override
    public Countries updateCountry(Countries country) {
        return repository.save(country);
    }

    @Override
    public void deleteCountry(Long id) {
        repository.deleteById(id);
    }
}
