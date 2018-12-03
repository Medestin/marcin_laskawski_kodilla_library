package com.kodilla.library.database.repositories;

import com.kodilla.library.database.entities.BookExemplar;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookExemplarRepository extends CrudRepository<BookExemplar, Long> {
}
