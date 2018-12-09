package com.kodilla.library.database.repositories;

import com.kodilla.library.database.entities.RentalDao;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface RentalDaoRepository extends CrudRepository<RentalDao, Long> {
    List<RentalDao> findAllByLibraryUser_Id(Long userId);
}
