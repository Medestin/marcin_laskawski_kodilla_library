package com.kodilla.library.database.services;

import com.kodilla.library.database.entities.LibraryUser;
import com.kodilla.library.database.repositories.LibraryUserRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
public class MainService {
    private LibraryUserRepository repository;

    @Autowired
    public MainService(LibraryUserRepository repository) {
        this.repository = repository;
    }

    public void createUser(LibraryUser user){
        repository.save(user);
    }
}
