package com.nikkin.devicesdb.Repos;

import com.nikkin.devicesdb.Entities.FloppyDisk;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FloppyDiskRepository extends CrudRepository<FloppyDisk, Long> {
}
