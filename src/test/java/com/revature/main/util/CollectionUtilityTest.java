package com.revature.main.util;

import com.revature.main.model.CardAmount;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

public class CollectionUtilityTest {
    @Test
    public void calculateTotalCards(){
        List<CardAmount> cards = new ArrayList<>();

        cards.add(new CardAmount(1,1,1));
        cards.add(new CardAmount(2,2,1));
        cards.add(new CardAmount(3,3,1));
        cards.add(new CardAmount(4,4,1));


        int total = CollectionUtility.calculateTotal(cards);

        assertThat(total).isEqualTo(4);
    }
}
