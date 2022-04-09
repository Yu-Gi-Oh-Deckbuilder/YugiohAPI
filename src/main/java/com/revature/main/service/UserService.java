package com.revature.main.service;

import com.revature.main.dao.UserRepository;
import com.revature.main.dto.UserDto;
import com.revature.main.model.User;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.CustomScopeConfigurer;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@NoArgsConstructor
@Transactional
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

    public boolean deleteUserById(int id) {
      User user = userRepository.getById(id);
       if ( user == null){
           return false;
       }
       userRepository.deleteById(id);
       return true;
    }

    public User updateUser(int id, UserDto userDto){
        User user = userRepository.getById(id);
        if ( user == null){
            return null;
        }

        user.setUsername(userDto.getUsername());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPassword(userDto.getPassword());
        user.setEmail(userDto.getEmail());

        return user;
    }

    public User createUser(User user){
        return userRepository.save(user);
    }
}