package com.Internet_shop.services.implementations;

import com.Internet_shop.entities.Roles;
import com.Internet_shop.entities.Users;
import com.Internet_shop.repositories.RolesRepository;
import com.Internet_shop.repositories.UsersRepository;
import com.Internet_shop.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersServiceImplementation implements UsersService {

    @Autowired
    UsersRepository repository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    RolesRepository rolesRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Users myUser = repository.findByEmail(s);

        if (myUser!=null){
            User secUser = new User(myUser.getEmail(), myUser.getPassword(), myUser.getRoles());
            return secUser;
        }

        throw new UsernameNotFoundException("user not found");
    }

    @Override
    public Users getUserByEmail(String email) {
        return repository.findByEmail(email);
    }

    @Override
    public Users getUserById(Long id) {
        return repository.findByIdEquals(id);
    }

    @Override
    public List<Users> getAllUsers() {
        return repository.findAll();
    }

    @Override
    public Users createUsers(Users users) {
        try {
            System.out.println(users);
            Users existsUser = repository.findByEmail(users.getEmail());
            assert existsUser == null;
            List<Roles> userRoles = rolesRepository.findAllByRoleEquals("ROLE_USER");
            assert userRoles!=null;
            users.setRoles(userRoles);
            users.setPassword(bCryptPasswordEncoder.encode(users.getPassword()));
            System.out.println("user created!!!!!!!!!");
            return repository.save(users);
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public Users createAdminUsers(Users users) {
        try{
            System.out.println(users +  "))))))))))))))))))))))))");
            Users existsUser = repository.findByEmail(users.getEmail());
            assert existsUser == null;
            users.setPassword(bCryptPasswordEncoder.encode(users.getPassword()));
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return repository.save(users);
    }


    @Override
    public Users updateUser(Users users) {
        return repository.save(users);
    }

    @Override
    public void deleteUserById(Long id) {
        repository.deleteById(id);
    }
}
