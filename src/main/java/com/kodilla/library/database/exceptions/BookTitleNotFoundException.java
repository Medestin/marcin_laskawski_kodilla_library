package com.kodilla.library.database.exceptions;

public class BookTitleNotFoundException extends RuntimeException {

    public BookTitleNotFoundException() {
        super("No matching BookTitles found.");
    }
}
