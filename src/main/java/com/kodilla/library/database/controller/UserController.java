package com.kodilla.library.database.controller;

import com.kodilla.library.database.entities.LibraryUser;
import com.kodilla.library.database.services.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/users")
public class UserController {
    private LibraryService libraryService;

    @Autowired
    public UserController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @GetMapping("/")
    public List<LibraryUser> getUsers(){
        return libraryService.getUsers();
    }

    @PostMapping(value = "/", consumes = APPLICATION_JSON_VALUE)
    public LibraryUser addUser(@RequestBody LibraryUser user){
        return libraryService.addUser(user);
    }
}
