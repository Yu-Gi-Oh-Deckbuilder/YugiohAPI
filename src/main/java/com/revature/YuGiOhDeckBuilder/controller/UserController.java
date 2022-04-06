package com.revature.YuGiOhDeckBuilder.controller;

import com.revature.YuGiOhDeckBuilder.model.User;
import com.revature.YuGiOhDeckBuilder.service.UserService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Profile("prod")
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    @Getter private UserService userService;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{username}")
    public User getUserByUsername(String username) {
        return userService.getUserByUsername(username);
    }

    public User getUserById(int id) {
        return userService.getUserById(id);
    }
}
