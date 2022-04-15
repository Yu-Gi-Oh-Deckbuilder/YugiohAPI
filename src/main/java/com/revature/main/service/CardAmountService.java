package com.revature.main.service;

import com.revature.main.dao.CardAmountRepository;
import com.revature.main.model.CardAmount;
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
}
