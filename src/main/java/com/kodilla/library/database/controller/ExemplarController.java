package com.kodilla.library.database.controller;

import com.kodilla.library.database.entities.*;
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

    @GetMapping("/{id}/get")
    public BookExemplar getExemplarById(@PathVariable(name = "id") Long exemplarId){
        return libraryService.getExemplarById(exemplarId);
    }

    @GetMapping("/{id}/count")
    public Long getExemplarCount(@PathVariable(name = "id") Long bookTitleId){
        return libraryService.getAvailableExemplarCount(bookTitleId);
    }

    @PutMapping("/{id}/{status}/change")
    public BookExemplar changeExemplarStatus(@PathVariable(name = "id") Long exemplarId,
                                             @PathVariable(name = "status") ExemplarStatus status){
        return libraryService.changeExemplarStatus(exemplarId, status);
    }

    @PutMapping("/{userId}/{titleId}/rent")
    public RentalDao rentABook(@PathVariable(name = "userId") Long userId,
                               @PathVariable(name = "titleId") Long titleId) {
        return libraryService.rentBook(userId, titleId);
    }

    @PutMapping("/{id}/return")
    public void returnBook(@PathVariable(name = "id") Long rentalDaoId){
        libraryService.returnBook(rentalDaoId);
    }

    @GetMapping("rentals")
    public List<RentalDao> getRentals(){
        return libraryService.getRentalDaos();
    }
}
