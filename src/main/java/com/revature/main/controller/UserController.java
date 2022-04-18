package com.revature.main.controller;

import com.revature.main.exceptions.UserNotFoundException;
import com.revature.main.model.User;
import com.revature.main.service.UserService;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.InvalidParameterException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.security.InvalidParameterException;
import java.util.List;
import java.util.NoSuchElementException;

@Profile("prod")
@RestController
@RequestMapping("/users")
@CrossOrigin
public class UserController {
    @Autowired
    @Getter
    private UserService userService;

    @GetMapping
    public ResponseEntity<?> getAllUsers() {
            List<User> users = userService.getAllUsers();
            if (users.isEmpty()){
                return ResponseEntity.status(400).body(new UserNotFoundException("There are no users available").getMessage());
            }
            return ResponseEntity.ok().body(users);
    }

    @GetMapping("/username")
    public ResponseEntity<?> getUserByUsername(@RequestParam("username") String username) {
        User user = userService.getUserByUsername(username);
        if (user == null){
            return ResponseEntity.status(400).body(new UserNotFoundException("There are no user with username "+ username).getMessage());
        }
        return ResponseEntity.ok().body(user);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable("id") int id) {
        try{
            User user = userService.getUserById(id);
            return ResponseEntity.ok().body(user);
        }catch(NoSuchElementException e){
            return ResponseEntity.status(400).body(new UserNotFoundException("There are no user with id "+ id).getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public boolean deleteUserById(@PathVariable("id") int id) throws UserNotFoundException {
        boolean result = userService.deleteUserById(id);
        if (!result){
            throw new InvalidParameterException("The User attempted to be deleted doesn't exist");
        }
        return true;
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user) {
        try{
            User createdUser = userService.createUser(user);
            return ResponseEntity.status(201).body(createdUser);
        }catch(IllegalArgumentException e){
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }

    @PutMapping
    public User updateUser(@PathVariable("id") int id){
        // TODO 1 check token to see the users role

        // TODO 2 if user is an admin pass in User else pass in UserDto
        //if()
        //userService.updateUser()
        return null;
    }


}