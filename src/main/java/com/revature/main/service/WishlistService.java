package com.revature.main.service;

import com.revature.main.dao.UserRepository;
import com.revature.main.dao.WishlistRepository;
import com.revature.main.exceptions.UnAuthorizedException;
import com.revature.main.exceptions.UserNotFoundException;
import com.revature.main.exceptions.WishlistDoesNotExistException;
import com.revature.main.model.User;
import com.revature.main.model.Wishlist;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@Service
@NoArgsConstructor
@Transactional
public class WishlistService extends EntityService{

    @Autowired
    private WishlistRepository wishlistRepository;

    public List<Wishlist> getAllWishlistByUserId(int id) throws UserNotFoundException {
       checkIfUserExists(id);

        List<Wishlist> wishlists = wishlistRepository.findAllByUserId(id);

        return wishlists;
    }

    public Wishlist getWishListById(int id, int userId) throws UserNotFoundException, UnAuthorizedException, WishlistDoesNotExistException {
        User user = userRepository.getById(userId);

        checkIfUserExists(userId);

        if(!wishlistRepository.existsById(id)){
            throw new WishlistDoesNotExistException("Wishlist does not exist");
        }

        Wishlist wishlist = wishlistRepository.getById(id);


        if(!wishlist.getSharedUsers().contains(user)){
            throw new UnAuthorizedException("You cannot view this wishlist");
        }

        return wishlist;
    }

    @Transactional
    public Wishlist editWishlist(Wishlist target) throws UserNotFoundException, WishlistDoesNotExistException {
        if(!wishlistRepository.existsById(target.getId())){
            throw new WishlistDoesNotExistException("Wishlist with id "+target.getId()+" does not exist");
        }

        if(!userRepository.existsById(target.getOwner().getId())){
            throw new UserNotFoundException("User with id "+target.getOwner().getId()+ " does not exist");
        }

        Wishlist source = wishlistRepository.getById(target.getId());
        source.setSharedUsers(target.getSharedUsers());
        source.setCards(target.getCards());
        return source;
    }

    @Transactional
    public boolean deleteWishlistById(int id){
        if ( !wishlistRepository.existsById(id)){
            return false;
        }
        wishlistRepository.deleteById(id);
        return true;
    }

    @Transactional
    public Wishlist createWishlist(Wishlist wishlist) throws UserNotFoundException {
        checkIfUserExists(wishlist.getOwner().getId());

        wishlistRepository.save(wishlist);

        return wishlist;
    }
}