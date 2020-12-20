package com.Internet_shop.services.implementations;

import com.Internet_shop.entities.Roles;
import com.Internet_shop.repositories.RolesRepository;
import com.Internet_shop.services.RolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolesServiceImplementation implements RolesService {

    @Autowired
    RolesRepository repository;

    @Override
    public List<Roles> getAllRoles() {
        return repository.findAll();
    }

    @Override
    public List<Roles> getRolesByID(List<Long> id) {
        return repository.findAllByIdIn(id);
    }

    @Override
    public Roles getRoleById(Long id) {
        return repository.findByIdEquals(id);
    }
}
