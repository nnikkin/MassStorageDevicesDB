package com.nikkin.devicesdb.Repos;

import com.nikkin.devicesdb.Entities.OpticalDisc;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OpticalDiscRepository extends CrudRepository<OpticalDisc, Long> {
}
