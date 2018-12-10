package com.kodilla.library.database.services;

import com.kodilla.library.database.entities.*;
import com.kodilla.library.database.exceptions.ExemplarNotFoundException;
import com.kodilla.library.database.repositories.BookExemplarRepository;
import com.kodilla.library.database.repositories.BookTitleRepository;
import com.kodilla.library.database.repositories.LibraryUserRepository;
import com.kodilla.library.database.repositories.RentalDaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class LibraryService {
    private LibraryUserRepository libraryUserRepository;
    private BookTitleRepository bookTitleRepository;
    private BookExemplarRepository bookExemplarRepository;
    private RentalDaoRepository rentalDaoRepository;

    @Autowired
    public LibraryService(LibraryUserRepository libraryUserRepository, BookTitleRepository bookTitleRepository,
                          BookExemplarRepository bookExemplarRepository, RentalDaoRepository rentalDaoRepository) {
        this.libraryUserRepository = libraryUserRepository;
        this.bookTitleRepository = bookTitleRepository;
        this.bookExemplarRepository = bookExemplarRepository;
        this.rentalDaoRepository = rentalDaoRepository;
    }

    public LibraryUser addUser(LibraryUser user){
        return libraryUserRepository.save(user);
    }

    public List<LibraryUser> getUsers(){
        return libraryUserRepository.findAll();
    }

    public LibraryUser getUserById(Long id){
        Optional<LibraryUser> libraryUser = libraryUserRepository.findById(id);

        if(libraryUser.isPresent()){
            return libraryUser.get();
        } else {
            throw new NoSuchElementException("There is no user with this ID");
        }
    }

    public BookTitle getTitleById(Long id){
        Optional<BookTitle> bookTitle = bookTitleRepository.findById(id);

        if(bookTitle.isPresent()){
            return bookTitle.get();
        } else {
            throw new NoSuchElementException("There is no user with this ID");
        }
    }

    public List<BookTitle> getTitles(){
        return bookTitleRepository.findAll();
    }

    public BookTitle addBookTitle(BookTitle bookTitle){
        Optional<BookTitle> book = Optional.ofNullable(bookTitleRepository
                .findByTitleAndAuthorAndPublicationYear(bookTitle.getTitle(), bookTitle.getAuthor(),
                        bookTitle.getPublicationYear()));

        if(book.isPresent()){
            BookExemplar exemplar = new BookExemplar(book.get());
            bookExemplarRepository.save(exemplar);
            return book.get();
        } else {
            bookTitleRepository.save(bookTitle);
            bookExemplarRepository.save(new BookExemplar(bookTitle));
            return bookTitle;
        }
    }

    public List<BookExemplar> getExemplars(){
        return bookExemplarRepository.findAll();
    }

    public BookExemplar getExemplarById(Long id){
        Optional<BookExemplar> bookExemplar = bookExemplarRepository.findById(id);

        if(bookExemplar.isPresent()){
            return bookExemplar.get();
        } else {
            throw new NoSuchElementException("There is no bookExemplar with this ID");
        }
    }

    public BookExemplar getAvailableExemplar(Long bookTitleId) throws ExemplarNotFoundException {
        List<BookExemplar> books = bookExemplarRepository.findAllByBookTitle_Id(bookTitleId);
        Optional<BookExemplar> exemplar = books.stream().filter(book -> book.getStatus() == ExemplarStatus.AVAILABLE)
                .findFirst();

        if(exemplar.isPresent()){
            return exemplar.get();
        } else {
            throw new ExemplarNotFoundException();
        }
    }

    public Long getAvailableExemplarCount(Long bookTitleId){
        List<BookExemplar> books = bookExemplarRepository.findAllByBookTitle_Id(bookTitleId);

        return books.stream().filter(exemplar -> exemplar.getStatus() == ExemplarStatus.AVAILABLE).count();
    }

    public BookExemplar changeExemplarStatus(Long exemplarId, ExemplarStatus status){
        Optional<BookExemplar> fetchedExemplar = bookExemplarRepository.findById(exemplarId);

        if(fetchedExemplar.isPresent()){
            fetchedExemplar.get().setStatus(status);
            bookExemplarRepository.save(fetchedExemplar.get());
            return fetchedExemplar.get();
        } else {
            throw new NoSuchElementException("There is no exemplar with this ID");
        }
    }

    public List<RentalDao> getRentalDaos(){
        return rentalDaoRepository.findAll();
    }

    public RentalDao getRentalDaoById(Long id){
        Optional<RentalDao> rentalDao = rentalDaoRepository.findById(id);

        if(rentalDao.isPresent()){
            return rentalDao.get();
        } else {
            throw new NoSuchElementException("There is no rentalDao with this ID");
        }
    }

    public RentalDao rentBook(Long userId, Long titleId) throws ExemplarNotFoundException {
        BookExemplar exemplar = getAvailableExemplar(titleId);
        RentalDao rentalDao = new RentalDao(getUserById(userId), exemplar);

        exemplar.setStatus(ExemplarStatus.RENTED);
        rentalDaoRepository.save(rentalDao);
        bookExemplarRepository.save(exemplar);
        return rentalDao;
    }

    public void returnBook(Long rentalDaoId){
        RentalDao rentalDao = getRentalDaoById(rentalDaoId);
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
