package com.kodilla.library.database.exceptions;

public class RentalDaoNotFoundException extends RuntimeException {

    public RentalDaoNotFoundException() {
        super("There is no such rental data.");
    }
}
