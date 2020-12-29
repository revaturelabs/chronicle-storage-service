package com.revature.chronicle.daos;

import com.revature.chronicle.models.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Service to handle user related any business logic needed prior to data access layer
 */
public class UserService {

    @Autowired
    public UserRepo userRepo;

    public UserService(UserRepo userRepo){
        this.userRepo = userRepo;
    }

    public User findByUsername(String username){
        return userRepo.findByUsername(username);
    }

    public boolean save(User user){
        userRepo.save(user);
        return true;
    }

    public boolean update(User user){
        //Check to see if exists? If not?
        userRepo.save(user);
        return true;
    }

    public boolean delete(User user){
        userRepo.delete(user);
        return true;
    }
}
