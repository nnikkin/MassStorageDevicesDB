package com.nikkin.devicesdb.Repos;

import com.nikkin.devicesdb.Entities.Computer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComputerRepository extends CrudRepository<Computer, Long> {
}
