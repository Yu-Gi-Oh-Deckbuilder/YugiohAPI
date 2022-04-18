package com.revature.main.service;

import com.revature.main.dao.DeckRepository;
import com.revature.main.dto.DeckDto;
import com.revature.main.exceptions.UserNotFoundException;
import com.revature.main.exceptions.CollectionDoesNotExistException;
import com.revature.main.model.Deck;

import com.revature.main.util.CollectionUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class DeckService extends EntityService{

    @Autowired
    private DeckRepository deckRepository;


    public List<Deck> getAllDecks(){

        return deckRepository.findAll();
    }

    public List<Deck> getAllDecksByUserId(int id) throws UserNotFoundException, CollectionDoesNotExistException {
        checkIfUserExists(id);
        return deckRepository.findAllByUserId(id);
    }

    public Deck getDeckById(int id, int userId) throws UserNotFoundException, CollectionDoesNotExistException {
        checkIfUserExists(userId);

        Optional<Deck> deck = deckRepository.findById(id);
        if (deck.isPresent()) return deck.get();
        throw new CollectionDoesNotExistException("Deck does not exist");
    }


    @Transactional
    public Deck editDeck(DeckDto target) throws UserNotFoundException, CollectionDoesNotExistException {
        if(!userRepository.existsById(target.getOwner().getId())){
            throw new UserNotFoundException("User with id "+target.getOwner().getId()+ " does not exist");
        }
        Optional<Deck> deck = deckRepository.findById(target.getId());
        if (deck.isPresent()) {
            Deck source = deck.get();
            source.setCards(target.getCards());
            source.setBanList(target.getBanList());
            source.setTotalCards(CollectionUtility.calculateTotal(target.getCards()));
            return deckRepository.saveAndFlush(source);
        }
        throw new CollectionDoesNotExistException("Deck with id "+target.getId()+" does not exist");
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
    public Deck createDeck(DeckDto deck) throws UserNotFoundException {
        checkIfUserExists(deck.getOwner().getId());

        Deck newDeck = new Deck();
        newDeck.setBanList(deck.getBanList());
        newDeck.setCards(deck.getCards());
        newDeck.setName(deck.getName());
        newDeck.setOwner(deck.getOwner());
        newDeck.setTotalCards(CollectionUtility.calculateTotal(deck.getCards()));
        deckRepository.saveAndFlush(newDeck);

        return newDeck;
    }
}
