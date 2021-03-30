package com.tsvlad.restapi.service;

import com.tsvlad.restapi.entity.User;

import java.util.Collection;
import java.util.List;

public interface UserService {
    Collection<User> getAllUsers();
    void saveOrUpdateUser(User user);
    User getUser(long id);
    void deleteUser(long id);

    List<User> getUsersByName(String name);
}
