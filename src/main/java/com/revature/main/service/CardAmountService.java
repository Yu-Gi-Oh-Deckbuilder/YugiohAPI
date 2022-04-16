package com.revature.main.service;

import com.revature.main.dao.CardAmountRepository;
import com.revature.main.exceptions.CardAmountDoesNotExistException;
import com.revature.main.exceptions.CollectionDoesNotExistException;
import com.revature.main.exceptions.UserNotFoundException;
import com.revature.main.model.CardAmount;
import com.revature.main.model.Deck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class CardAmountService {

    @Autowired
    private CardAmountRepository cardAmountRepository;

    @Transactional
    public List<CardAmount> createCardAmount(List<CardAmount> cards){
        List<CardAmount> addedCardAmounts = new ArrayList<>();
        for(CardAmount cardAmount: cards){
            addedCardAmounts.add(cardAmountRepository.save(cardAmount));
        }
        cardAmountRepository.flush();
        return addedCardAmounts;
    }

    @Transactional
    public List<CardAmount> editCardAmount(List<CardAmount> target) throws CardAmountDoesNotExistException {
        List<CardAmount> editedCards = new ArrayList<>();

        for(CardAmount cardAmount: target){
            if(!cardAmountRepository.existsById(cardAmount.getId())){
                throw new CardAmountDoesNotExistException("Cards with id "+cardAmount.getId()+" does not exist");
            }

            CardAmount source = cardAmountRepository.getById(cardAmount.getId());
            source.setCardAmount(cardAmount.getCardAmount());
            source.setCardId(cardAmount.getCardId());
            editedCards.add(cardAmountRepository.save(cardAmount));
        }
        cardAmountRepository.flush();


        return editedCards;
    }
}
