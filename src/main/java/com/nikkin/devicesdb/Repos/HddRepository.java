package com.nikkin.devicesdb.Repos;

import com.nikkin.devicesdb.Entities.HardDiskDrive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HddRepository extends JpaRepository<HardDiskDrive, Integer> {
}
