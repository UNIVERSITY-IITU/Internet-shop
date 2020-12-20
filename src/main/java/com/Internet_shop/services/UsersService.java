package com.Internet_shop.services;

import com.Internet_shop.entities.Users;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UsersService extends UserDetailsService {
    Users getUserByEmail(String email);
    Users getUserById(Long id);

    List<Users> getAllUsers();
    Users createUsers(Users users);
    Users createAdminUsers(Users users);
    Users updateUser (Users users);
    void deleteUserById(Long id);
}
