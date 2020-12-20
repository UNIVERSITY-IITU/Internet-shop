package com.Internet_shop.services;

import com.Internet_shop.entities.Categories;
import com.Internet_shop.entities.Roles;
import org.springframework.stereotype.Service;

import java.util.List;


public interface RolesService {
    List<Roles> getAllRoles();
    List<Roles> getRolesByID(List<Long> id);
    Roles getRoleById(Long id);
}
