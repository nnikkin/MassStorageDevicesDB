package com.nikkin.devicesdb.Repos;

import com.nikkin.devicesdb.Entities.FlashDrive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlashDriveRepository extends JpaRepository<FlashDrive, Long> {
}
