package com.revature.main.exceptions;

public class WishlistDoesNotExistException extends Exception{
    public WishlistDoesNotExistException() {
    }

    public WishlistDoesNotExistException(String message) {
        super(message);
    }
}

