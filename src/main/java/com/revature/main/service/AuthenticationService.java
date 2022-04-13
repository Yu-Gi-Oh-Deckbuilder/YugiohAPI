package com.revature.main.service;

import com.revature.main.dao.UserRepository;
import com.revature.main.exceptions.UnAuthorizedException;
import com.revature.main.exceptions.UserNotFoundException;
import com.revature.main.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    private UserRepository userRepo;

    public User login(String username, String password) throws UserNotFoundException {

        // If they didn't input a username or password at all
        if (username.trim().equals("") || password.trim().equals("")) {
            throw new IllegalArgumentException("You must provide a username and password to log in");
        }

        // trim() will trim all leading and trailing whitespace
        User user = userRepo.findByUsernameAndPassword(username.trim(), password.trim());

        // No User in the database matched a particular username and password
        if (user == null) {
            throw new UserNotFoundException("Invalid username and/or password");
        }

        return user;
    }

}
