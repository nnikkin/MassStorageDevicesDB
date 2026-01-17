package com.nikkin.devicesdb.Repos;

import com.nikkin.devicesdb.Entities.SolidStateDrive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SSDRepository extends JpaRepository<SolidStateDrive, Long> {
}
