package com.revature.main.service;

import com.revature.main.dao.CardAmountRepository;
import com.revature.main.exceptions.CardAmountDoesNotExistException;
import com.revature.main.model.CardAmount;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import javax.smartcardio.Card;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class CardAmountServiceTest {

    private static CardAmount cardAmount;
    private static CardAmount cardAmount2;


    @Mock
    private CardAmountRepository cardAmountRepository;

    @InjectMocks
    private CardAmountService cardAmountService;

    @BeforeAll
    public static void init(){
        cardAmount = new CardAmount(1,1,2);
        cardAmount2 = new CardAmount(2,2,5);
    }

    @Test
    public void createCardAmount_positive(){
        List<CardAmount> newCards = new ArrayList<>();
        newCards.add(cardAmount);
        when(cardAmountRepository.save(cardAmount)).thenReturn(cardAmount);
        List<CardAmount> target = cardAmountService.createCardAmount(newCards);

        assertThat(target).isEqualTo(newCards);
    }

    @Test
    public void editCardAmount_positive() throws CardAmountDoesNotExistException {
        List<CardAmount> editedCards = new ArrayList<>();
        editedCards.add(cardAmount);
        editedCards.add(cardAmount2);

        when(cardAmountRepository.save(cardAmount)).thenReturn(cardAmount);
        when(cardAmountRepository.save(cardAmount2)).thenReturn(cardAmount2);
        when(cardAmountRepository.existsById(cardAmount.getId())).thenReturn(true);
        when(cardAmountRepository.existsById(cardAmount2.getId())).thenReturn(true);
        when(cardAmountRepository.getById(cardAmount.getId())).thenReturn(cardAmount);
        when(cardAmountRepository.getById(cardAmount2.getId())).thenReturn(cardAmount2);

        List<CardAmount> target = cardAmountService.editCardAmount(editedCards);

        assertThat(target).isEqualTo(editedCards);
    }

    @Test
    public void editCardAmount_CardAmountDoesNotExistException(){
        List<CardAmount> editedCards = new ArrayList<>();
        editedCards.add(cardAmount);
        Assertions.assertThrows(CardAmountDoesNotExistException.class,()->{
            cardAmountService.editCardAmount(editedCards);
        });
    }
}
