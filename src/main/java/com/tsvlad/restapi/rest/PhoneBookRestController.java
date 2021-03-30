package com.tsvlad.restapi.rest;

import com.tsvlad.restapi.entity.PhoneBookEntry;
import com.tsvlad.restapi.service.PhoneBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/")
public class PhoneBookRestController {

    private PhoneBookService phoneBookService;

    @Autowired
    public PhoneBookRestController(PhoneBookService phoneBookService) {
        this.phoneBookService = phoneBookService;
    }

    @GetMapping("all-entries/{id}")
    public Collection<PhoneBookEntry> getAllEntriesForUser(@PathVariable long id) {
        return phoneBookService.getAllEntriesForUser(id);
    }

    @GetMapping("entry/{userId}/{id}")
    public PhoneBookEntry getPhoneBookEntry(@PathVariable long userId, @PathVariable long id) {
        return phoneBookService.getPhoneBookEntry(userId, id);
    }

    @PostMapping("save-entry/{userId}")
    public void saveEntry(@PathVariable long userId, @RequestBody PhoneBookEntry entry) {
        phoneBookService.saveOrUpdateEntry(userId, entry);
    }

    @DeleteMapping("delete-entry/{userId}/{id}")
    public void deletePhoneBookEntry(@PathVariable long userId, @PathVariable long id) {
        phoneBookService.deleteEntry(userId, id);
    }

    @GetMapping("entry-by-number/{userId}/{number}")
    public PhoneBookEntry getEntryByNumber(@PathVariable long userId, @PathVariable String number) {
        return phoneBookService.getEntryByNumber(userId, number);
    }
}
