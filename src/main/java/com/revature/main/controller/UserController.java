package com.revature.main.controller;

import com.revature.main.exceptions.UserNotFoundException;
import com.revature.main.model.User;
import com.revature.main.service.UserService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.*;

import java.security.InvalidParameterException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Profile("prod")
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    @Getter
    private UserService userService;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{username}")
    public User getUserByUsername(@PathVariable("username") String username) {
        return userService.getUserByUsername(username);
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable("id") int id) {
        return userService.getUserById(id);
    }

    @DeleteMapping("/{id}")
    public boolean deleteUserById(@PathVariable("id") int id) throws UserNotFoundException {
        boolean result = userService.deleteUserById(id);
        if (result == false){
            throw new InvalidParameterException("The User attempted to be deleted doesn't exist");
        }
        return result;
    }

    @PostMapping
    public User updateUser(@PathVariable("id") int id){
        return null;
    }

}