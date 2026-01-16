package com.nikkin.devicesdb.Services;

import com.nikkin.devicesdb.Dto.HardDiskDriveDto;
import com.nikkin.devicesdb.Entities.HardDiskDrive;
import com.nikkin.devicesdb.Repos.HDDRepository;
import org.springframework.stereotype.Service;

@Service
public class HDDService extends BaseService<HardDiskDrive, HardDiskDriveDto> {
    public HDDService(HDDRepository repository) {
        super();
        setCrudRepository(repository);
    }

    @Override
    protected HardDiskDriveDto mapToDto(HardDiskDrive entity) {
        return new HardDiskDriveDto(
            entity.getId(),
            entity.getManufacturer(),
            entity.getCapacity(),
            entity.getDriveInterface(),
            entity.getFormat(),
            entity.getPowerConsumption()
        );
    }

    @Override
    protected void updateEntity(HardDiskDrive entity, HardDiskDriveDto dto) {
        entity.setId(dto.id());
        entity.setManufacturer(dto.manufacturer());
        entity.setDriveInterface(dto.driveInterface());
        entity.setCapacity(dto.capacity());
        entity.setFormat(dto.format());
        entity.setPowerConsumption(dto.powerConsumption());
    }

    @Override
    protected HardDiskDrive mapToEntity(HardDiskDriveDto dto) {
        HardDiskDrive entity = new HardDiskDrive();
        updateEntity(entity, dto);
        return entity;
    }
}
