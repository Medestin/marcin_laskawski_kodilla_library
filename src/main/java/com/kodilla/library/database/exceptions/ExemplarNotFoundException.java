package com.kodilla.library.database.exceptions;

public class ExemplarNotFoundException extends Exception {

    public ExemplarNotFoundException(){
        super("No available exemplars of this book.");
    }
}
