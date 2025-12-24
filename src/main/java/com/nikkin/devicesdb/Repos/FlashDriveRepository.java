package com.nikkin.devicesdb.Repos;

import java.util.List;
import java.util.Optional;

import com.nikkin.devicesdb.Entities.FlashDrive;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlashDriveRepository extends CrudRepository<FlashDrive, Long> {
    Optional<FlashDrive> findFirstByName(String name);
    List<FlashDrive> getByUsbInterface(String usbInterface);
    List<FlashDrive> getByUsbType(String usbType);
    List<FlashDrive> getByCapacity(float capacity);
    List<FlashDrive> getByWriteSpeed(float writeSpeed);
    List<FlashDrive> getByReadSpeed(float readSpeed);
}
