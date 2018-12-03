package com.kodilla.library.database.services;

import com.kodilla.library.database.entities.BookExemplar;
import com.kodilla.library.database.entities.BookTitle;
import com.kodilla.library.database.entities.LibraryUser;
import com.kodilla.library.database.repositories.BookExemplarRepository;
import com.kodilla.library.database.repositories.BookTitleRepository;
import com.kodilla.library.database.repositories.LibraryUserRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@NoArgsConstructor
public class MainService {
    private LibraryUserRepository libraryUserRepository;
    private BookTitleRepository bookTitleRepository;
    private BookExemplarRepository bookExemplarRepository;

    @Autowired
    public MainService(LibraryUserRepository libraryUserRepository, BookTitleRepository bookTitleRepository,
                       BookExemplarRepository bookExemplarRepository) {
        this.libraryUserRepository = libraryUserRepository;
        this.bookTitleRepository = bookTitleRepository;
        this.bookExemplarRepository = bookExemplarRepository;
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

}
