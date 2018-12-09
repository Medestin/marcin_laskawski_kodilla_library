package com.kodilla.library.database.controller;

import com.kodilla.library.database.entities.*;
import com.kodilla.library.database.exceptions.ExemplarNotFoundException;
import com.kodilla.library.database.services.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/exemplars")
public class ExemplarController {
    private MainService mainService;

    @Autowired
    public ExemplarController(MainService mainService) {
        this.mainService = mainService;
    }

    @GetMapping("/")
    public List<BookExemplar> getExemplars(){
        return mainService.getExemplars();
    }

    @GetMapping("/count")
    public Long getExemplarCount(@RequestBody BookTitle bookTitle){
        return mainService.getAvailableExemplarCount(bookTitle);
    }

    @GetMapping("rentals")
    public List<RentalDao> getRentals(){
        return mainService.getRentalDaos();
    }

    @PutMapping("/status")
    public BookExemplar changeExemplarStatus(@RequestParam Long exemplarId, @RequestParam ExemplarStatus status){
        return mainService.changeExemplarStatus(mainService.getExemplarById(exemplarId), status);
    }

    @PutMapping("/rent")
    public RentalDao rentABook(@RequestParam Long userId, @RequestParam Long titleId) throws ExemplarNotFoundException {
        LibraryUser user = mainService.getUserById(userId);
        BookTitle title = mainService.getTitleById(titleId);
        return mainService.rentBook(user, title);
    }

    @PutMapping("/return/{id}")
    public void returnBook(@PathVariable("id") Long rentalDaoId){
        RentalDao rentalDao = mainService.getRentalDaoById(rentalDaoId);
        mainService.returnBook(rentalDao);
    }
}
