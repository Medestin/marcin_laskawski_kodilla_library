package com.kodilla.library.database.controller;

import com.kodilla.library.database.entities.BookTitle;
import com.kodilla.library.database.services.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/titles")
public class TitleController {
    private MainService mainService;

    @Autowired
    public TitleController(MainService mainService) {
        this.mainService = mainService;
    }

    @GetMapping("/")
    public List<BookTitle> getTitles(){
        return mainService.getTitles();
    }

    @PostMapping(value = "/", consumes = APPLICATION_JSON_VALUE)
    public BookTitle addBook(@RequestBody BookTitle bookTitle){
        return mainService.addBookTitle(bookTitle);
    }

}
