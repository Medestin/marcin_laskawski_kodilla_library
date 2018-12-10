package com.kodilla.library.database.repositories;

import com.kodilla.library.database.entities.BookExemplar;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookExemplarRepository extends CrudRepository<BookExemplar, Long> {
    List<BookExemplar> findAll();

    List<BookExemplar> findAllByBookTitle_Id(Long bookTitleId);
}
