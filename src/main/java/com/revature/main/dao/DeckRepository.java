package com.revature.main.dao;

import com.revature.main.model.Deck;
import com.revature.main.model.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeckRepository extends JpaRepository<Deck, Integer> {
//    @Query("SELECT r FROM Deck r JOIN r.user a WHERE a.id = ?1")
//    public List<Deck> findAllByUserId(int userId);
}
