package com.revature.main.service;

import com.revature.main.dao.UserRepository;
import com.revature.main.dao.WishlistRepository;
import com.revature.main.model.User;
import com.revature.main.model.Wishlist;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@NoArgsConstructor
@Transactional
public class WishlistService {

    @Autowired
    private WishlistRepository wishlistRepository;

//    public List<Wishlist> getAllWishlistByUserId(int id) {
//        return wishlistRepository.findAllByUserId(id);
//    }
}
