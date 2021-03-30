package com.tsvlad.restapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.*;

public class PhoneBook {
    private long currentId;
    private List<PhoneBookEntry> entries;

    public PhoneBook() {
        currentId = 1;
        entries = new LinkedList<>();
    }

    public void saveOrUpdatePhoneBookEntry(PhoneBookEntry entry) {
        if (entry.getId() == 0) {
            entry.setId(currentId);
            currentId++;
            entries.add(entry);
        } else {
            for (int i = 0; i < entries.size(); i++) {
                if (entries.get(i).getId() == entry.getId()) {
                    entries.set(i, entry);
                    break;
                }
            }
        }
    }

    public PhoneBookEntry getPhoneBookEntry(long id) {
        for (PhoneBookEntry entry : entries) {
            if (entry.getId() == id) {
                return entry;
            }
        }
        return null;
    }

    @JsonIgnore
    public Collection<PhoneBookEntry> getAllEntries() {
        return entries;
    }

    public void deletePhoneBookEntry(long id) {
        for (int i = 0; i < entries.size(); i++) {
            if (entries.get(i).getId() == id) {
                entries.remove(i);
                break;
            }
        }
    }

    public long getCurrentId() {
        return currentId;
    }

    public void setCurrentId(long currentId) {
        this.currentId = currentId;
    }

    public List<PhoneBookEntry> getEntries() {
        return entries;
    }

    public void setEntries(List<PhoneBookEntry> entries) {
        this.entries = entries;
    }
}
