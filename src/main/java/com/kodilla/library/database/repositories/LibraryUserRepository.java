package com.kodilla.library.database.repositories;

import com.kodilla.library.database.entities.LibraryUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface LibraryUserRepository extends CrudRepository<LibraryUser, Long> {
}
