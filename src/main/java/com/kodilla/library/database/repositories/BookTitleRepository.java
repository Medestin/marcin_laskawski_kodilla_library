package com.kodilla.library.database.repositories;

import com.kodilla.library.database.entities.BookTitle;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookTitleRepository extends CrudRepository<BookTitle, Long> {
    BookTitle findByTitleAndAuthorAndPublicationYear(String title, String author, int publicationYear);
}
