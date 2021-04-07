package com.tsvlad.restapi.dao;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tsvlad.restapi.entity.PhoneBook;
import com.tsvlad.restapi.entity.PhoneBookEntry;
import com.tsvlad.restapi.entity.User;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@Repository
public class DataStorageImpl implements DataStorage {
    private Map<Long, User> users;
    private long currentId;

    public DataStorageImpl() {
        init();
    }

    private void init() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            File file = ResourceUtils.getFile("classpath:init_data.json");
            this.users = objectMapper.readValue(file, new TypeReference<HashMap<Long, User>>() {});
            this.currentId = this.users.size() + 1;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Collection<User> getAllUsers() {
        return users.values();
    }

    @Override
    public void saveOrUpdateUser(User user) {
        if (user.getId() == 0) {
            user.setId(currentId);
            currentId++;
        }
        if (user.getPhoneBook() == null) {
            user.setPhoneBook(new PhoneBook());
        }
        if (user.getName().isEmpty()) {
            throw new IllegalArgumentException("Name is empty!");
        }
        users.put(user.getId(), user);
    }

    @Override
    public User getUser(long id) {
        User user = users.get(id);
        if (user == null) {
            throw new NoSuchElementException("Not found user with id=" + id);
        }
        return user;
    }

    @Override
    public void deleteUser(long id) {
        if (!users.containsKey(id)) {
            throw new NoSuchElementException("Not found user with id=" + id);
        }
        users.remove(id);
    }

    @Override
    public Collection<PhoneBookEntry> getAllEntriesForUser(long id) {
        User user = users.get(id);
        if (user == null) {
            throw new NoSuchElementException("Not found user with id=" + id);
        }
        return user.getAllEntries();
    }

    @Override
    public PhoneBookEntry getPhoneBookEntry(long userId, long id) {
        User user = users.get(userId);
        if (user == null) {
            throw new NoSuchElementException("Not found user with id=" + userId);
        }
        PhoneBookEntry entry = user.getPhoneBookEntry(id);
        if (entry == null) {
            throw new NoSuchElementException("Not found entry with id=" + id + " for user with id=" + userId);
        }
        return entry;
    }

    @Override
    public void saveOrUpdateEntry(long userId, PhoneBookEntry entry) {
        User user = users.get(userId);
        if (user == null) {
            throw new NoSuchElementException("Not found user with id=" + userId);
        }
        if (entry.getName() == null || entry.getName().isEmpty()) {
            throw new IllegalArgumentException("Contact name is empty");
        }
        if (entry.getPhoneNumber() == null || entry.getPhoneNumber().isEmpty()) {
            throw new IllegalArgumentException("Contact number is empty");
        }
//        if (Pattern.matches("^[+]?[0-9]?\\(?([0-9]{3})\\)?([ .-]?)([0-9]{3})\\2([0-9]{4})$", entry.getPhoneNumber())) {
//            throw new IllegalArgumentException("Incorrect number format: " + entry.getPhoneNumber());
//        }
        user.saveOrUpdatePhoneBookEntry(entry);
    }

    @Override
    public void deleteEntry(long userId, long id) {
        User user = users.get(userId);
        if (user == null) {
            throw new NoSuchElementException("Not found user with id=" + userId);
        }
        PhoneBookEntry entry = user.getPhoneBookEntry(id);
        if (entry == null) {
            throw new NoSuchElementException();
        }
        user.deletePhoneBookEntry(id);
    }
}
