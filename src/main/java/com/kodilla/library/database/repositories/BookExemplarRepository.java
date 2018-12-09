package com.kodilla.library.database.repositories;

import com.kodilla.library.database.entities.BookExemplar;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface BookExemplarRepository extends CrudRepository<BookExemplar, Long> {
    List<BookExemplar> findAllByBookTitle_Id(Long bookTitleId);
}
