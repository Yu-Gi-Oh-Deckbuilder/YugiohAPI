package com.revature.main.service;

import com.revature.main.dao.WishlistRepository;
import com.revature.main.dto.WishlistDto;
import com.revature.main.exceptions.UnAuthorizedException;
import com.revature.main.exceptions.UserNotFoundException;
import com.revature.main.exceptions.CollectionDoesNotExistException;
import com.revature.main.model.User;
import com.revature.main.model.Wishlist;
import com.revature.main.util.CollectionUtility;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@NoArgsConstructor
@Transactional
public class WishlistService extends EntityService{


    @Autowired
    private WishlistRepository wishlistRepository;

    public List<Wishlist> getAllWishlists(){
        return wishlistRepository.findAll();
    }
    public List<Wishlist> getAllWishlistByUserId(int id) throws UserNotFoundException {
       checkIfUserExists(id);

        return wishlistRepository.findAllByUserId(id);
    }

    public Wishlist getWishListById(int id, int userId) throws UserNotFoundException, UnAuthorizedException, CollectionDoesNotExistException {
        User user = userRepository.findById(userId).get();

        checkIfUserExists(userId);

        if(!wishlistRepository.existsById(id)){
            throw new CollectionDoesNotExistException("Wishlist does not exist");
        }

        Wishlist wishlist = wishlistRepository.findById(id).get();


        if(! wishlist.getSharedUsers().contains(user)){
            throw new UnAuthorizedException("You cannot view this wishlist");
        }

        return wishlist;
    }

    @Transactional
    public Wishlist editWishlist(WishlistDto target) throws UserNotFoundException, CollectionDoesNotExistException {
        if(!wishlistRepository.existsById(target.getId())){
            throw new CollectionDoesNotExistException("Wishlist with id "+target.getId()+" does not exist");
        }

        if(!userRepository.existsById(target.getOwner().getId())){
            throw new UserNotFoundException("User with id "+target.getOwner().getId()+ " does not exist");
        }

        Wishlist source = wishlistRepository.getById(target.getId());
        source.setName(target.getName());
        source.setSharedUsers(target.getSharedUsers());
        source.setCards(target.getCards());
        source.setTotalCards(CollectionUtility.calculateTotal(target.getCards()));

        //flushing will save the object to the database
        return wishlistRepository.saveAndFlush(source);
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
    public Wishlist createWishlist(WishlistDto wishlist) throws UserNotFoundException {
        checkIfUserExists(wishlist.getOwner().getId());

        Wishlist newWishlist = new Wishlist();
        newWishlist.setName(wishlist.getName());
        newWishlist.setCards(wishlist.getCards());
        newWishlist.setOwner(wishlist.getOwner());
        newWishlist.setSharedUsers(wishlist.getSharedUsers());
        newWishlist.setTotalCards(CollectionUtility.calculateTotal(wishlist.getCards()));
        wishlistRepository.save(newWishlist);

        return newWishlist;
    }
}
