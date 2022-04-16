package com.revature.main.dao;

import com.revature.main.model.CardAmount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardAmountRepository extends JpaRepository<CardAmount, Integer> {
}
