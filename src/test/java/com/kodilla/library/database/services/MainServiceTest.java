package com.kodilla.library.database.services;

import com.kodilla.library.database.entities.LibraryUser;
import com.kodilla.library.database.repositories.LibraryUserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MainServiceTest {
    @Autowired
    private MainService service;
    @Autowired
    private LibraryUserRepository repository;

    @Test
    public void createUser() {
        LibraryUser user = new LibraryUser("Marcin", "Laskawski");
        service.createUser(user);
        Long createdId = user.getId();

        LibraryUser fetchedUser =
                repository.findById(createdId).isPresent() ? repository.findById(createdId).get() : null;

        if (fetchedUser != null) {
            assertEquals(user.getFirstName(), fetchedUser.getFirstName());
        } else {
            fail();
        }
        repository.deleteById(createdId);
    }
}