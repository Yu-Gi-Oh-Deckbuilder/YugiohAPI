package com.revature.main.dto;

import com.revature.main.model.BanList;
import com.revature.main.model.CardAmount;
import com.revature.main.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeckDto {
    private int id;
    private List<CardAmount> cards;
    private int totalCards;
    private User owner;
    private String name;
    private BanList banList;
}
