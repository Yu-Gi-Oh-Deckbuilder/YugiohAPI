package com.revature.main.dao;

import com.revature.main.model.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Integer> {

//    @Query("SELECT r FROM Wishlist r JOIN r.user a WHERE a.id = ?1")
//    public List<Wishlist> findAllByUserId(int userId);
}
