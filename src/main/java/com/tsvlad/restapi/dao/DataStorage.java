package com.tsvlad.restapi.dao;

import com.tsvlad.restapi.entity.PhoneBookEntry;
import com.tsvlad.restapi.entity.User;

import java.util.Collection;

public interface DataStorage {
    Collection<User> getAllUsers();
    void saveOrUpdateUser(User user);
    User getUser(long id);
    void deleteUser(long id);

    Collection<PhoneBookEntry> getAllEntriesForUser(long id);
    PhoneBookEntry getPhoneBookEntry(long userId, long id);
    void saveOrUpdateEntry(long userId, PhoneBookEntry entry);
    void deleteEntry(long userId, long id);
}
