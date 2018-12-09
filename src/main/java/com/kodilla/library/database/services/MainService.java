package com.kodilla.library.database.services;

import com.kodilla.library.database.entities.*;
import com.kodilla.library.database.exceptions.ExemplarNotFoundException;
import com.kodilla.library.database.repositories.BookExemplarRepository;
import com.kodilla.library.database.repositories.BookTitleRepository;
import com.kodilla.library.database.repositories.LibraryUserRepository;
import com.kodilla.library.database.repositories.RentalDaoRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.IntStream;

@Service
@NoArgsConstructor
public class MainService {
    private LibraryUserRepository libraryUserRepository;
    private BookTitleRepository bookTitleRepository;
    private BookExemplarRepository bookExemplarRepository;
    private RentalDaoRepository rentalDaoRepository;

    @Autowired
    public MainService(LibraryUserRepository libraryUserRepository, BookTitleRepository bookTitleRepository,
                       BookExemplarRepository bookExemplarRepository, RentalDaoRepository rentalDaoRepository) {
        this.libraryUserRepository = libraryUserRepository;
        this.bookTitleRepository = bookTitleRepository;
        this.bookExemplarRepository = bookExemplarRepository;
        this.rentalDaoRepository = rentalDaoRepository;
    }

    public void addUser(LibraryUser user){
        libraryUserRepository.save(user);
    }

    public void addBookTitle(BookTitle bookTitle){
        Optional<BookTitle> book = Optional.ofNullable(bookTitleRepository
                .findByTitleAndAuthorAndPublicationYear(bookTitle.getTitle(), bookTitle.getAuthor(),
                        bookTitle.getPublicationYear()));

        if(book.isPresent()){
            BookExemplar exemplar = new BookExemplar(book.get());
            bookExemplarRepository.save(exemplar);
        } else {
            bookTitleRepository.save(bookTitle);
            bookExemplarRepository.save(new BookExemplar(bookTitle));
        }
    }

    public RentalDao rentBook(LibraryUser user, BookTitle title) throws ExemplarNotFoundException {
        BookExemplar exemplar = getAvailableExemplar(title);
        RentalDao rentalDao = new RentalDao(user, exemplar);
        rentalDaoRepository.save(rentalDao);
        exemplar.setStatus(ExemplarStatus.RENTED);
        bookExemplarRepository.save(exemplar);
        return rentalDao;
    }

    public BookExemplar getAvailableExemplar(BookTitle bookTitle) throws ExemplarNotFoundException {
        Long bookTitleId = getTitleId(bookTitle);
        List<BookExemplar> books = bookExemplarRepository.findAllByBookTitle_Id(bookTitleId);
        Optional<BookExemplar> exemplar = books.stream().filter(book -> book.getStatus() == ExemplarStatus.AVAILABLE)
                .findFirst();
        if(exemplar.isPresent()){
            return exemplar.get();
        } else {
            throw new ExemplarNotFoundException();
        }
    }

    public Long getAvailableExemplarCount(BookTitle bookTitle){
        Long bookTitleId = getTitleId(bookTitle);
        List<BookExemplar> books = bookExemplarRepository.findAllByBookTitle_Id(bookTitleId);

        return books.stream().filter(exemplar -> exemplar.getStatus() == ExemplarStatus.AVAILABLE).count();
    }

    public Long getTitleId(BookTitle bookTitle) throws NoSuchElementException {
        Optional<Long> titleId = Optional.ofNullable(bookTitleRepository.findByTitleAndAuthorAndPublicationYear(
                bookTitle.getTitle(), bookTitle.getAuthor(), bookTitle.getPublicationYear()
        ).getId());

        if(titleId.isPresent()){
            return titleId.get();
        } else {
            throw new NoSuchElementException("There is no title with this ID.");
        }
    }

    public void changeExemplarStatus(BookExemplar exemplar, ExemplarStatus status){
        Optional<BookExemplar> fetchedExemplar = bookExemplarRepository.findById(exemplar.getId());

        if(fetchedExemplar.isPresent()){
            fetchedExemplar.get().setStatus(status);
            bookExemplarRepository.save(fetchedExemplar.get());
        } else {
            throw new NoSuchElementException("There is no exemplar with this ID");
        }
    }

    public void returnBook(RentalDao rentalDao){
        Optional<BookExemplar> fetchedExemplar = bookExemplarRepository.findById(rentalDao.getBookExemplar().getId());

        if(fetchedExemplar.isPresent()){
            fetchedExemplar.get().setStatus(ExemplarStatus.AVAILABLE);
            bookExemplarRepository.save(fetchedExemplar.get());
            rentalDaoRepository.deleteById(rentalDao.getId());
        } else {
            throw new NoSuchElementException("There is no exemplar with this ID");
        }
    }
}
