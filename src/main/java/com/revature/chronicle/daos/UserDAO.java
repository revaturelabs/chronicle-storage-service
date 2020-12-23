package com.revature.chronicle.daos;

import com.revature.chronicle.models.User;

import java.util.List;

/**
 * Data access object interface for user data
 */
public interface UserDAO {
    public List<User> getUsers();
    public User getUser(int id);

    public boolean addUser(User user);
    public boolean updateUser(User user);
    public boolean deleteUser(int id);
}
