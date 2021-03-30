package com.tsvlad.restapi.service;

import com.tsvlad.restapi.entity.PhoneBookEntry;

import java.util.Collection;

public interface PhoneBookService {
    Collection<PhoneBookEntry> getAllEntriesForUser(long id);
    PhoneBookEntry getPhoneBookEntry(long userId, long id);
    void saveOrUpdateEntry(long userId, PhoneBookEntry entry);
    void deleteEntry(long userId, long id);

    PhoneBookEntry getEntryByNumber(long userId, String number);
}
