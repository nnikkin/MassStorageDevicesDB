package com.nikkin.devicesdb.Repos;

import com.nikkin.devicesdb.Entities.RandomAccessMemory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RAMRepository extends JpaRepository<RandomAccessMemory, Long> {
}
