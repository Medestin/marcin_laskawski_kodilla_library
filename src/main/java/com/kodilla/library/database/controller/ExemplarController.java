package com.kodilla.library.database.controller;

import com.kodilla.library.database.entities.*;
import com.kodilla.library.database.exceptions.ExemplarNotFoundException;
import com.kodilla.library.database.services.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/exemplars")
public class ExemplarController {
    private LibraryService libraryService;

    @Autowired
    public ExemplarController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @GetMapping("/")
    public List<BookExemplar> getExemplars(){
        return libraryService.getExemplars();
    }

    @GetMapping("/get")
    public BookExemplar getExemplarById(@RequestParam Long exemplarId){
        return libraryService.getExemplarById(exemplarId);
    }

    @GetMapping("/count")
    public Long getExemplarCount(@RequestParam Long bookTitleId){
        return libraryService.getAvailableExemplarCount(bookTitleId);
    }

    @PutMapping("/status")
    public BookExemplar changeExemplarStatus(@RequestParam Long exemplarId, @RequestParam ExemplarStatus status){
        return libraryService.changeExemplarStatus(exemplarId, status);
    }

    @PutMapping("/rent")
    public RentalDao rentABook(@RequestParam Long userId, @RequestParam Long titleId) throws ExemplarNotFoundException {
        return libraryService.rentBook(userId, titleId);
    }

    @PutMapping("/return")
    public void returnBook(@RequestParam Long rentalDaoId){
        libraryService.returnBook(rentalDaoId);
    }

    @GetMapping("rentals")
    public List<RentalDao> getRentals(){
        return libraryService.getRentalDaos();
    }
}
