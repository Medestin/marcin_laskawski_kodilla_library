package com.kodilla.library.database.controller;

import com.kodilla.library.database.entities.LibraryUser;
import com.kodilla.library.database.services.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/users")
public class UserController {
    private MainService mainService;

    @Autowired
    public UserController(MainService mainService) {
        this.mainService = mainService;
    }

    @GetMapping("/")
    public List<LibraryUser> getUsers(){
        return mainService.getUsers();
    }

    @PostMapping(value = "/", consumes = APPLICATION_JSON_VALUE)
    public LibraryUser addUser(@RequestBody LibraryUser user){
        return mainService.addUser(user);
    }
}
