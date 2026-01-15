package com.nikkin.devicesdb.Repos;

import com.nikkin.devicesdb.Entities.HardDiskDrive;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HDDRepository extends CrudRepository<HardDiskDrive, Long> {
}
