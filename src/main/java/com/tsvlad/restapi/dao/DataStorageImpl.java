package com.tsvlad.restapi.dao;

import com.tsvlad.restapi.entity.PhoneBook;
import com.tsvlad.restapi.entity.PhoneBookEntry;
import com.tsvlad.restapi.entity.User;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Repository
public class DataStorageImpl implements DataStorage {
    private Map<Long, User> users;
    private long currentId;

    public DataStorageImpl() {
        init();
    }

    private void init() {
        this.currentId = 4;
        this.users = new HashMap<>();
        User user1 = new User(1, "Vladislav");
        user1.saveOrUpdatePhoneBookEntry(new PhoneBookEntry("89151111111", "Ivan"));
        user1.saveOrUpdatePhoneBookEntry(new PhoneBookEntry("89152222222", "Vladislav"));
        user1.saveOrUpdatePhoneBookEntry(new PhoneBookEntry("89153333333", "Elena"));
        this.users.put(user1.getId(), user1);

        User user2 = new User(2, "Ivan");
        user2.saveOrUpdatePhoneBookEntry(new PhoneBookEntry("89154444444", "Denis"));
        user2.saveOrUpdatePhoneBookEntry(new PhoneBookEntry("89155555555", "Anna"));
        user2.saveOrUpdatePhoneBookEntry(new PhoneBookEntry("89156666666", "Alina"));
        this.users.put(user2.getId(), user2);

        User user3 = new User(3, "Elena");
        user3.saveOrUpdatePhoneBookEntry(new PhoneBookEntry("89157777777", "Polina"));
        user3.saveOrUpdatePhoneBookEntry(new PhoneBookEntry("89158888888", "Veronika"));
        user3.saveOrUpdatePhoneBookEntry(new PhoneBookEntry("89159999999", "Nikita"));
        this.users.put(user3.getId(), user3);
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
        users.put(user.getId(), user);
    }

    @Override
    public User getUser(long id) {
        return users.get(id);
    }

    @Override
    public void deleteUser(long id) {
        users.remove(id);
    }

    @Override
    public Collection<PhoneBookEntry> getAllEntriesForUser(long id) {
        User user = users.get(id);
        return user == null ? null : user.getAllEntries();
    }

    @Override
    public PhoneBookEntry getPhoneBookEntry(long userId, long id) {
        User user = users.get(userId);
        if (user == null) {
            return null;
        }
        return user.getPhoneBookEntry(id);
    }

    @Override
    public void saveOrUpdateEntry(long userId, PhoneBookEntry entry) {
        User user = users.get(userId);
        if (user != null) {
            user.saveOrUpdatePhoneBookEntry(entry);
        }
    }

    @Override
    public void deleteEntry(long userId, long id) {
        User user = users.get(userId);
        if (user != null) {
            user.deletePhoneBookEntry(id);
        }
    }
}
