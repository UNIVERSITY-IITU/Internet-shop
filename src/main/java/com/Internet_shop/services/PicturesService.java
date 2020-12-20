package com.Internet_shop.services;

import com.Internet_shop.entities.Items;
import com.Internet_shop.entities.Pictures;

import java.util.List;

public interface PicturesService {
    List<Pictures> getPictures(Items item);
    Pictures getPicture(Long id);
    Pictures addPictures(Pictures pictures);
    void removePicture(Long id);
}
