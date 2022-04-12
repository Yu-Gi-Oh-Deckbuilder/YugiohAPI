package com.revature.main.service;


import com.revature.main.dao.UserRepository;
import com.revature.main.exceptions.UserNotFoundException;
import com.revature.main.model.User;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class EntityService{
    @Autowired
    protected UserRepository userRepository;

    public void checkIfUserExists(int id) throws UserNotFoundException {
        if(!userRepository.existsById(id)){
            throw new UserNotFoundException("User with id "+ id + " does not exist");
        }
    }
}
