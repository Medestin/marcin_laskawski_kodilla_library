package com.kodilla.library.database.controller;

import com.kodilla.library.database.entities.*;
import com.kodilla.library.database.entities.exemplarStatus.ExemplarStatus;
import com.kodilla.library.database.entities.exemplarStatus.ExemplarStatusWrapper;
import com.kodilla.library.database.services.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

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
    public BookExemplar getExemplarById(@PathVariable("id") Long exemplarId){
        return libraryService.getExemplarById(exemplarId);
    }

    @GetMapping("/{id}/count")
    public Long getExemplarCount(@PathVariable("id") Long bookTitleId){
        return libraryService.getAvailableExemplarCount(bookTitleId);
    }

    @PutMapping(value = "/{id}/change", consumes = APPLICATION_JSON_VALUE)
    public BookExemplar changeExemplarStatus(@PathVariable("id") Long exemplarId,
                                             @RequestBody ExemplarStatusWrapper status){
        return libraryService.changeExemplarStatus(exemplarId, status.getExemplarStatus());
    }

    @PutMapping("/{userId}/{titleId}/rent")
    public RentalDao rentABook(@PathVariable("userId") Long userId,
                               @PathVariable("titleId") Long titleId) {
        return libraryService.rentBook(userId, titleId);
    }

    @PutMapping("/{id}/return")
    public void returnBook(@PathVariable("id") Long rentalDaoId){
        libraryService.returnBook(rentalDaoId);
    }

    @GetMapping("rentals")
    public List<RentalDao> getRentals(){
        return libraryService.getRentalDaos();
    }
}
