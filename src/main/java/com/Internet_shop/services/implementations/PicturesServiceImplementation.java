package com.Internet_shop.services.implementations;

import com.Internet_shop.entities.Items;
import com.Internet_shop.entities.Pictures;
import com.Internet_shop.repositories.PicturesRepository;
import com.Internet_shop.services.PicturesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PicturesServiceImplementation implements PicturesService {
    @Autowired
    PicturesRepository repository;

    @Override
    public List<Pictures> getPictures(Items item) {
        return repository.findAllByItems_Id(item.getId());
    }

    @Override
    public Pictures getPicture(Long id) {
        return repository.findByIdEquals(id);
    }

    @Override
    public Pictures addPictures(Pictures pictures) {
        return repository.save(pictures);
    }

    @Override
    public void removePicture(Long id) {
        repository.deleteById(id);
    }
}
