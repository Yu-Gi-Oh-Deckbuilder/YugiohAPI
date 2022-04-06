package com.revature.YuGiOhDeckBuilder.service;

import com.revature.YuGiOhDeckBuilder.model.User;
import com.revature.YuGiOhDeckBuilder.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User getUserById(int id) {
        return userRepository.getById(id);

    }
}
