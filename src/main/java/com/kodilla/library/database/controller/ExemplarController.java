package com.kodilla.library.database.controller;

import com.kodilla.library.database.entities.*;
import com.kodilla.library.database.exceptions.ExemplarNotFoundException;
import com.kodilla.library.database.services.MainService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Long getExemplarCount(@RequestParam Long bookTitleId){
        return mainService.getAvailableExemplarCount(bookTitleId);
    }

    @PutMapping("/status")
    public BookExemplar changeExemplarStatus(@RequestParam Long exemplarId, @RequestParam ExemplarStatus status){
        return mainService.changeExemplarStatus(exemplarId, status);
    }

    @PutMapping("/rent")
    public RentalDao rentABook(@RequestParam Long userId, @RequestParam Long titleId) throws ExemplarNotFoundException {
        return mainService.rentBook(userId, titleId);
    }

    @PutMapping("/return")
    public void returnBook(@RequestParam Long rentalDaoId){
        mainService.returnBook(rentalDaoId);
    }

    @GetMapping("rentals")
    public List<RentalDao> getRentals(){
        return mainService.getRentalDaos();
    }
}
