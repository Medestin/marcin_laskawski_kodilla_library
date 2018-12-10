package com.kodilla.library.database.controller;

import com.kodilla.library.database.entities.BookTitle;
import com.kodilla.library.database.services.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/titles")
public class TitleController {
    private LibraryService libraryService;

    @Autowired
    public TitleController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @GetMapping("/")
    public List<BookTitle> getTitles(){
        return libraryService.getTitles();
    }

    @PostMapping(value = "/", consumes = APPLICATION_JSON_VALUE)
    public BookTitle addBook(@RequestBody BookTitle bookTitle){
        return libraryService.addBookTitle(bookTitle);
    }

}
