package com.kodilla.library.database.repositories;

import com.kodilla.library.database.entities.RentalDao;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentalDaoRepository extends CrudRepository<RentalDao, Long> {
}
