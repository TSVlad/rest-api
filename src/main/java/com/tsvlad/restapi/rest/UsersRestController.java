package com.tsvlad.restapi.rest;

import com.tsvlad.restapi.entity.User;
import com.tsvlad.restapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/")
public class UsersRestController {
    private UserService userService;

    @Autowired
    public UsersRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/all-users")
    public Collection<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/save-user")
    public void saveUser(@RequestBody User user) {
        userService.saveOrUpdateUser(user);
    }

    @GetMapping("user/{id}")
    public User getUser(@PathVariable long id) {
        return userService.getUser(id);
    }

    @DeleteMapping("delete-user/{id}")
    public void deleteUser(@PathVariable long id) {
        userService.deleteUser(id);
    }

    @GetMapping("user-by-name/{name}")
    public List<User> getUsersByName(@PathVariable String name) {
        return userService.getUsersByName(name);
    }
}
