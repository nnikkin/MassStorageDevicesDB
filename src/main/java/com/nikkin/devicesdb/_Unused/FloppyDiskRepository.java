package com.nikkin.devicesdb._Unused;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FloppyDiskRepository extends CrudRepository<FloppyDisk, Long> {
}
