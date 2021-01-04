package com.revature.chronicle.daos;

import com.revature.chronicle.models.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Data access object interface for user data
 * Implemented using Spring Data JPA where not specified
 */
@Repository
public interface UserRepo extends JpaRepository<User, Integer> {
    public User findByUsername(String username);
    public User save(User user);
}
