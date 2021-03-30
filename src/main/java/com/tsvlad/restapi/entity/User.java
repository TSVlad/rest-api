package com.tsvlad.restapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Collection;

public class User {
    private long id;
    private String name;
    private PhoneBook phoneBook;

    public User(long id, String name) {
        this.id = id;
        this.name = name;
        phoneBook = new PhoneBook();
    }

    public User() {
    }

    public void saveOrUpdatePhoneBookEntry(PhoneBookEntry entry) {
        phoneBook.saveOrUpdatePhoneBookEntry(entry);
    }

    public PhoneBookEntry getPhoneBookEntry(long id) {
        return phoneBook.getPhoneBookEntry(id);
    }

    public void deletePhoneBookEntry(long id) {
        phoneBook.deletePhoneBookEntry(id);
    }

    @JsonIgnore
    public Collection<PhoneBookEntry> getAllEntries() {
        return phoneBook.getAllEntries();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PhoneBook getPhoneBook() {
        return phoneBook;
    }

    public void setPhoneBook(PhoneBook phoneBook) {
        this.phoneBook = phoneBook;
    }
}
