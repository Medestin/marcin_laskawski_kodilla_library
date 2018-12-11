package com.kodilla.library.database.controller;

import com.kodilla.library.database.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionAdvice {

    @ResponseBody
    @ExceptionHandler(ExemplarNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String exemplarNotFoundHandler(ExemplarNotFoundException e){
        return e.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String userNotFoundHandler(UserNotFoundException e){
        return e.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(BookTitleNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String bookTitleNotFoundHandler(BookTitleNotFoundException e){
        return e.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(RentalDaoNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String rentalDaoNotFoundHandler(RentalDaoNotFoundException e){
        return e.getMessage();
    }
}
