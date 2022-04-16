package com.revature.main.exceptions;

public class CardAmountDoesNotExistException extends Exception{
    public CardAmountDoesNotExistException() {
    }

    public CardAmountDoesNotExistException(String message) {
        super(message);
    }
}
