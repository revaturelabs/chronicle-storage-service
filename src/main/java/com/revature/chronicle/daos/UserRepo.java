package com.revature.chronicle.daos;

import com.revature.chronicle.models.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Data access object interface for user data
 */
@Repository
public interface UserRepo extends JpaRepository<User, Integer>{
    //User findByUserID(int id);
    //List<User> findAll();
}
