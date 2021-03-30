package com.tsvlad.restapi.entity;

import java.util.Locale;

public class PhoneBookEntry {
    private long id;
    private String phoneNumber;
    private String name;

    public PhoneBookEntry(String phoneNumber, String name) {
        id = 0;
        this.phoneNumber = phoneNumber;
        this.name = name;
    }

    public PhoneBookEntry() {
    }

    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
