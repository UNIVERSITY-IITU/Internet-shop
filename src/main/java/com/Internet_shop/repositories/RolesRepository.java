package com.Internet_shop.repositories;

import com.Internet_shop.entities.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface RolesRepository extends JpaRepository<Roles, Long> {
    List<Roles> findAllByIdIn(List<Long> id);
    List<Roles> findAllByRoleEquals(String role);
    Roles findByIdEquals(Long id);
}
