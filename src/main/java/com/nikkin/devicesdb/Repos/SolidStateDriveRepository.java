package com.nikkin.devicesdb.Repos;

import com.nikkin.devicesdb.Entities.SolidStateDrive;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SolidStateDriveRepository extends CrudRepository<SolidStateDrive, Long> {
}
