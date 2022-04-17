package com.revature.main.util;

import com.revature.main.model.CardAmount;

import java.util.List;

public class CollectionUtility {
    public static int calculateTotal(List<CardAmount> cards){
        int count = 0;

        for(CardAmount amount : cards){
            count+= amount.getCardAmount();
        }
        return count;
    }
}
