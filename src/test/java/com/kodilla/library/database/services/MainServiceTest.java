package com.kodilla.library.database.services;

import com.kodilla.library.database.entities.BookTitle;
import com.kodilla.library.database.entities.LibraryUser;
import com.kodilla.library.database.exceptions.ExemplarNotFoundException;
import com.kodilla.library.database.repositories.BookExemplarRepository;
import com.kodilla.library.database.repositories.BookTitleRepository;
import com.kodilla.library.database.repositories.LibraryUserRepository;
import com.kodilla.library.database.repositories.RentalDaoRepository;
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
    private LibraryUserRepository libraryUserRepository;
    @Autowired
    private BookTitleRepository bookTitleRepository;
    @Autowired
    private BookExemplarRepository bookExemplarRepository;
    @Autowired
    private RentalDaoRepository rentalDaoRepository;

    @Test
    public void addUser() {
        LibraryUser user = new LibraryUser("Marcin", "Laskawski");
        service.addUser(user);
        Long createdId = user.getId();

        LibraryUser fetchedUser =
                libraryUserRepository.findById(createdId).isPresent() ? libraryUserRepository.findById(createdId).get() : null;

        if (fetchedUser != null) {
            assertEquals(user.getFirstName(), fetchedUser.getFirstName());
        } else {
            fail();
        }
        libraryUserRepository.deleteById(createdId);
    }

    @Test
    public void addBook(){
        BookTitle bookTitle = new BookTitle("Winnetou", "Karol May", 1893);
        service.addBookTitle(bookTitle);
    }

    @Test
    public void rentABook() throws ExemplarNotFoundException {
        BookTitle bookTitle = new BookTitle("Last Wish", "Andrzej Sapkowski", 1993);
        service.addBookTitle(bookTitle);

        LibraryUser user = new LibraryUser("Michal", "Jakistam");
        service.addUser(user);

        service.rentBook(user, bookTitle);
    }
}