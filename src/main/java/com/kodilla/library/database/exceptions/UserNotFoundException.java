package com.kodilla.library.database.exceptions;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException() {
        super("There is no such user.");
    }
}
