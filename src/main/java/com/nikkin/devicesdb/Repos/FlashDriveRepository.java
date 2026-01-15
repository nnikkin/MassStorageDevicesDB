package com.nikkin.devicesdb.Repos;

import com.nikkin.devicesdb.Entities.FlashDrive;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlashDriveRepository extends CrudRepository<FlashDrive, Long> {
}
