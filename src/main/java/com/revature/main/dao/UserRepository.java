package com.revature.main.dao;


import com.revature.main.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);
    //User findById(int id);
    User findByUsernameAndPassword(String username,String password);
}