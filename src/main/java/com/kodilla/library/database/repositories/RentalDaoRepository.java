package com.kodilla.library.database.repositories;

import com.kodilla.library.database.entities.RentalDao;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RentalDaoRepository extends CrudRepository<RentalDao, Long> {
    List<RentalDao> findAll();
    List<RentalDao> findAllByLibraryUser_Id(Long userId);
}
