package com.nikkin.devicesdb.Repos;

import com.nikkin.devicesdb.Entities.RandomAccessMemory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RAMRepository extends CrudRepository<RandomAccessMemory, Long> {
}
