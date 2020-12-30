package com.revature.chronicle.services;

import com.revature.chronicle.daos.UserRepo;
import com.revature.chronicle.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Service to handle user related any business logic needed prior to data access layer for Users
 */
@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

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
