package com.tsvlad.restapi.service;

import com.tsvlad.restapi.dao.DataStorage;
import com.tsvlad.restapi.entity.PhoneBookEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class PhoneBookServiceImpl implements PhoneBookService {

    private DataStorage dataStorage;

    @Autowired
    public PhoneBookServiceImpl(DataStorage dataStorage) {
        this.dataStorage = dataStorage;
    }

    @Override
    public Collection<PhoneBookEntry> getAllEntriesForUser(long id) {
        return dataStorage.getAllEntriesForUser(id);
    }

    @Override
    public PhoneBookEntry getPhoneBookEntry(long userId, long id) {
        return dataStorage.getPhoneBookEntry(userId, id);
    }

    @Override
    public void saveOrUpdateEntry(long userId, PhoneBookEntry entry) {
        dataStorage.saveOrUpdateEntry(userId, entry);
    }

    @Override
    public void deleteEntry(long userId, long id) {
        dataStorage.deleteEntry(userId, id);
    }

    @Override
    public PhoneBookEntry getEntryByNumber(long userId, String number) {
        Collection<PhoneBookEntry> entries = dataStorage.getAllEntriesForUser(userId);
        for (PhoneBookEntry entry : entries) {
            if (entry.getPhoneNumber().equals(number)) {
                return entry;
            }
        }
        return null;
    }
}
