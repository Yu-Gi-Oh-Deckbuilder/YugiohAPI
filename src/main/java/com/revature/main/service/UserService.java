package com.revature.main.service;

import com.revature.main.dao.UserRepository;
import com.revature.main.dto.UserDto;
import com.revature.main.exceptions.UserNotFoundException;
import com.revature.main.model.User;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@NoArgsConstructor
public class UserService extends EntityService{

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User getUserById(int id){
        return userRepository.findById(id).get();
    }

    @Transactional
    public boolean deleteUserById(int id) throws UserNotFoundException {
       checkIfUserExists(id);
       userRepository.deleteById(id);
       return true;
    }

    @Transactional
    public User updateUser(UserDto userDto) throws UserNotFoundException {
        checkIfUserExists(userDto.getId());
        validateUserData(userDto);

        User user = userRepository.getById(userDto.getId());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());

        return user;
    }

    //This is an admin endpoint
    @Transactional
    public User updateUser(User target) throws UserNotFoundException {
        checkIfUserExists(target.getId());
        validateUserData(target);


        User user = userRepository.getById(target.getId());
        user.setUsername(target.getUsername());
        user.setFirstName(target.getFirstName());
        user.setLastName(target.getLastName());
        user.setPassword(target.getPassword());
        user.setEmail(target.getEmail());

        return user;
    }

    @Transactional
    public User createUser(User user){

        validateUserData(user);

        return userRepository.save(user);
    }

    public void validateUserData(User user){
        try{
            user.setUsername(user.getUsername().trim());
            user.setFirstName(user.getFirstName().trim());
            user.setLastName(user.getLastName().trim());

            if (user.getPassword().length() > 50){
                throw new IllegalArgumentException("Your password is too long. Please make it shorter");
            }

            if (!user.getUsername().matches("[a-zA-Z0-9]+")) {
                throw new IllegalArgumentException("Username must only have alphabetical characters and/or '!_-. Username input was " + user.getUsername());
            }

            if(user.getFirstName().contains(" ") || user.getLastName().contains(" ")){
                throw new IllegalArgumentException("First name and last and cannot be more than 1 string");
            }

            if (!user.getFirstName().matches("[a-zA-Z]+")) {
                throw new IllegalArgumentException("First name must only have alphabetical characters. First name input was " + user.getFirstName());
            }

            if (!user.getLastName().matches("[a-zA-Z]+")) {
                throw new IllegalArgumentException("Last name must only have alphabetical characters. Last name input was " + user.getLastName());
            }

            if(!user.getEmail().matches("^[A-Za-z0-9+_.-]+@[A-Za-z-]+[.]+[a-z]{2,3}$")){
                throw new IllegalArgumentException("Please ensure that your email is correct,Internationalized domain names are not allowed.");
            }

        }catch(NullPointerException e){
            new NullPointerException("Parameter specified as non-null is null");
        }
    }

    public void validateUserData(UserDto userDto){
        try{

            userDto.setFirstName(userDto.getFirstName().trim());
            userDto.setLastName(userDto.getLastName().trim());

            if (!userDto.getFirstName().matches("[a-zA-Z]+")) {
                throw new IllegalArgumentException("First name must only have alphabetical characters. First name input was " + userDto.getFirstName());
            }

            if (!userDto.getLastName().matches("[a-zA-Z]+")) {
                throw new IllegalArgumentException("Last name must only have alphabetical characters. Last name input was " + userDto.getLastName());
            }

            if(!userDto.getEmail().matches("^[A-Za-z0-9+_.-]+@[A-Za-z-]+[.]+[a-z]{2,3}$")){
                throw new IllegalArgumentException("Please ensure that your email is correct,Internationalized domain names are not allowed.");
            }

        }catch(NullPointerException e){
            new NullPointerException("Parameter specified as non-null is null");
        }
    }
}