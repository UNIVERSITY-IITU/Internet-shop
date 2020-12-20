package com.Internet_shop.repositories;

import com.Internet_shop.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;


@Repository
@Transactional
public interface UsersRepository extends JpaRepository<Users, Long> {
    Users findByEmail(String email);
    Users findByIdEquals(Long id);
}
