package com.tsvlad.restapi.service;

import com.tsvlad.restapi.dao.DataStorage;
import com.tsvlad.restapi.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserServiceImpl implements UserService {
    private DataStorage dataStorage;

    @Autowired
    public UserServiceImpl(DataStorage dataStorage) {
        this.dataStorage = dataStorage;
    }

    @Override
    public Collection<User> getAllUsers() {
        return dataStorage.getAllUsers();
    }

    @Override
    public void saveOrUpdateUser(User user) throws IllegalArgumentException {
        dataStorage.saveOrUpdateUser(user);
    }

    @Override
    public User getUser(long id) throws NoSuchElementException {
        return dataStorage.getUser(id);
    }

    @Override
    public void deleteUser(long id) {
        dataStorage.deleteUser(id);
    }

    @Override
    public List<User> getUsersByName(String name) {
        name = name.toLowerCase();
        Collection<User> allUsers = dataStorage.getAllUsers();
        List<User> users = new ArrayList<>();
        for(User user : allUsers) {
            if (user.getName().toLowerCase().contains(name)) {
                users.add(user);
            }
        }
        return users;
    }
}
