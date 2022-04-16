package com.revature.main.service;

import com.revature.main.dao.DeckRepository;
import com.revature.main.exceptions.UnAuthorizedException;
import com.revature.main.exceptions.UserNotFoundException;
import com.revature.main.exceptions.CollectionDoesNotExistException;
import com.revature.main.model.Deck;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class DeckService extends EntityService{

    @Autowired
    private DeckRepository deckRepository;


    public List<Deck> getAllDecks(){
        List<Deck> decks = deckRepository.findAll();

        return decks;
    }

    public List<Deck> getAllDecksByUserId(int id) throws UserNotFoundException, CollectionDoesNotExistException, UnAuthorizedException {
        checkIfUserExists(id);
        List<Deck> decks = deckRepository.findAllByUserId(id);
        return decks;
    }

    public Deck getDeckById(int id, int userId) throws UserNotFoundException, CollectionDoesNotExistException {
        checkIfUserExists(userId);

        if(!deckRepository.existsById(id)){
            throw new CollectionDoesNotExistException("Deck does not exist");
        }

       Deck deck = deckRepository.findById(id).get();

        return deck;
    }


    @Transactional
    public Deck editDeck(Deck target) throws UserNotFoundException, CollectionDoesNotExistException {
        if(!deckRepository.existsById(target.getId())){
            throw new CollectionDoesNotExistException("Deck with id "+target.getId()+" does not exist");
        }

        if(!userRepository.existsById(target.getOwner().getId())){
            throw new UserNotFoundException("User with id "+target.getOwner().getId()+ " does not exist");
        }

        Deck source = deckRepository.findById(target.getId()).get();
        source.setCards(target.getCards());
        source.setBanList(target.getBanList());
        return deckRepository.saveAndFlush(source);
    }

    @Transactional
    public boolean deleteDeckById(int id){
        if ( !deckRepository.existsById(id)){
            return false;
        }
        deckRepository.deleteById(id);
        return true;
    }

    @Transactional
    public Deck createDeck(Deck deck) throws UserNotFoundException {
        checkIfUserExists(deck.getOwner().getId());

        deckRepository.saveAndFlush(deck);

        return deck;
    }
}
