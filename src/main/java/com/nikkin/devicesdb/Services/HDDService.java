package com.nikkin.devicesdb.Services;

import com.nikkin.devicesdb.Dto.HardDiskDriveDto;
import com.nikkin.devicesdb.Entities.HardDiskDrive;
import com.nikkin.devicesdb.Repos.ComputerRepository;
import com.nikkin.devicesdb.Repos.HDDRepository;
import org.springframework.stereotype.Service;

@Service
public class HDDService extends BaseService<HardDiskDrive, HardDiskDriveDto> {
    private final ComputerRepository computerRepository;
    public HDDService(HDDRepository repository, ComputerRepository computerRepository) {
        super();
        setRepository(repository);
        this.computerRepository = computerRepository;
    }

    @Override
    protected HardDiskDriveDto mapToDto(HardDiskDrive entity) {
        return new HardDiskDriveDto(
            entity.getId(),
            entity.getManufacturer(),
            entity.getCapacity(),
            entity.getDriveInterface(),
            entity.getFormat(),
            entity.getPowerConsumption(),
            entity.getComputer() != null ? entity.getComputer().getId() : null
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

        if (dto.computerId() != null) {
            computerRepository.findById(dto.computerId()).ifPresent(entity::setComputer);
        } else {
            entity.setComputer(null);
        }
    }

    @Override
    protected HardDiskDrive mapToEntity(HardDiskDriveDto dto) {
        HardDiskDrive entity = new HardDiskDrive();
        updateEntity(entity, dto);
        return entity;
    }
}
