package com.tsvlad.restapi.rest;

import com.tsvlad.restapi.entity.User;
import com.tsvlad.restapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UsersRestController {
    private UserService userService;

    @Autowired
    public UsersRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public Collection<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("")
    public void saveUser(@RequestBody User user) {
        userService.saveOrUpdateUser(user);
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable long id) {
        return userService.getUser(id);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable long id) {
        userService.deleteUser(id);
    }

    @GetMapping("/by-name/{name}")
    public List<User> getUsersByName(@PathVariable String name) {
        return userService.getUsersByName(name);
    }
}
