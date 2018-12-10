package com.kodilla.library.database.services;

import com.kodilla.library.database.entities.*;
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

import java.util.List;

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
                libraryUserRepository.findById(createdId)
                        .isPresent() ? libraryUserRepository.findById(createdId).get() : null;

        if (fetchedUser != null) {
            assertEquals(user.getFirstName(), fetchedUser.getFirstName());
        } else {
            fail();
        }
        libraryUserRepository.deleteById(createdId);
    }

    @Test
    public void addBook(){
        BookTitle bookTitle = new BookTitle("Test title", "Test author", 2000);
        service.addBookTitle(bookTitle);

        Long titleId = bookTitle.getId();
        Long exemplarCount = service.getAvailableExemplarCount(titleId);

        assertEquals(Long.valueOf(1), exemplarCount);
        assertEquals("Test title", bookTitleRepository.findById(titleId).get().getTitle());

        List<BookExemplar> exemplars = bookExemplarRepository.findAllByBookTitle_Id(titleId);
        exemplars.forEach(n-> bookExemplarRepository.deleteById(n.getId()));
        bookTitleRepository.deleteById(titleId);
    }

    @Test
    public void rentABook() throws ExemplarNotFoundException {
        BookTitle bookTitle = new BookTitle("Test title", "Test author", 2000);
        LibraryUser user = new LibraryUser("Test", "User");
        service.addBookTitle(bookTitle);
        service.addUser(user);

        Long titleId = bookTitle.getId();
        Long userId = user.getId();
        RentalDao rentalDao = service.rentBook(userId, titleId);

        List<RentalDao> rentalDaos = rentalDaoRepository.findAllByLibraryUser_Id(userId);
        assertEquals(1, rentalDaos.size());

        service.returnBook(rentalDao.getId());
        rentalDaos = rentalDaoRepository.findAllByLibraryUser_Id(userId);
        assertTrue(rentalDaos.isEmpty());


        List<BookExemplar> exemplars = bookExemplarRepository.findAllByBookTitle_Id(titleId);
        exemplars.forEach(n-> bookExemplarRepository.deleteById(n.getId()));
        bookTitleRepository.deleteById(titleId);
        libraryUserRepository.deleteById(userId);
    }

    @Test
    public void changeExemplarStatus() throws ExemplarNotFoundException {
        BookTitle bookTitle = new BookTitle("Test title", "Test author", 2000);
        LibraryUser user = new LibraryUser("Test", "User");
        service.addBookTitle(bookTitle);
        service.addUser(user);

        BookExemplar fetchedExemplar = service.getAvailableExemplar(bookTitle.getId());

        Long titleId = bookTitle.getId();
        Long userId = user.getId();
        Long fetchedExemplarId = fetchedExemplar.getId();

        service.changeExemplarStatus(fetchedExemplarId, ExemplarStatus.LOST);
        assertEquals(ExemplarStatus.LOST, bookExemplarRepository.findById(fetchedExemplarId).get().getStatus());

        List<BookExemplar> exemplars = bookExemplarRepository.findAllByBookTitle_Id(titleId);
        exemplars.forEach(n-> bookExemplarRepository.deleteById(n.getId()));
        bookTitleRepository.deleteById(titleId);
        libraryUserRepository.deleteById(userId);
    }
}