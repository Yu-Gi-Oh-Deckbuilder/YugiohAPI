package com.revature.main.dao;

import com.revature.main.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Integer> {

   @Query("SELECT r FROM Inventory r JOIN r.owner a WHERE a.id = ?1")
    Inventory findAllCardsOwnedById(int userId);

}
