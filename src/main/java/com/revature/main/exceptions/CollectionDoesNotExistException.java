package com.revature.main.exceptions;

public class CollectionDoesNotExistException extends Exception{
    public CollectionDoesNotExistException() {
    }

    public CollectionDoesNotExistException(String message) {
        super(message);
    }
}

