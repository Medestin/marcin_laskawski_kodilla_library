package com.kodilla.library.database.exceptions;

public class ExemplarNotFoundException extends RuntimeException {

    public ExemplarNotFoundException(){
        super("No available exemplars of this book.");
    }
}
