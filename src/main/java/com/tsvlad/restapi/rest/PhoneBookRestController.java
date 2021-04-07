package com.tsvlad.restapi.rest;

import com.tsvlad.restapi.entity.PhoneBookEntry;
import com.tsvlad.restapi.service.PhoneBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/users")
public class PhoneBookRestController {

    private PhoneBookService phoneBookService;

    @Autowired
    public PhoneBookRestController(PhoneBookService phoneBookService) {
        this.phoneBookService = phoneBookService;
    }

    @GetMapping("/{userId}/contacts")
    public Collection<PhoneBookEntry> getAllEntriesForUser(@PathVariable long userId) {
        return phoneBookService.getAllEntriesForUser(userId);
    }

    @GetMapping("/{userId}/contacts/{id}")
    public PhoneBookEntry getPhoneBookEntry(@PathVariable long userId, @PathVariable long id) {
        return phoneBookService.getPhoneBookEntry(userId, id);
    }

    @PostMapping("/{userId}/contacts")
    public void saveEntry(@PathVariable long userId, @RequestBody PhoneBookEntry entry) {
        phoneBookService.saveOrUpdateEntry(userId, entry);
    }

    @DeleteMapping("/{userId}/contacts/{id}")
    public void deletePhoneBookEntry(@PathVariable long userId, @PathVariable long id) {
        phoneBookService.deleteEntry(userId, id);
    }

    @GetMapping("/{userId}/contacts/by-number/{number}")
    public PhoneBookEntry getEntryByNumber(@PathVariable long userId, @PathVariable String number) {
        return phoneBookService.getEntryByNumber(userId, number);
    }
}
