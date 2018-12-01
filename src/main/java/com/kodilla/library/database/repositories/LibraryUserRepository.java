package com.kodilla.library.database.repositories;

import com.kodilla.library.database.entities.LibraryUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibraryUserRepository extends CrudRepository<LibraryUser, Long> {
}
