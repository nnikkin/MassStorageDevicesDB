package com.nikkin.devicesdb.Services;

import com.nikkin.devicesdb.Dto.SolidStateDriveDto;
import com.nikkin.devicesdb.Entities.SolidStateDrive;
import com.nikkin.devicesdb.Repos.ComputerRepository;
import com.nikkin.devicesdb.Repos.SSDRepository;
import org.springframework.stereotype.Service;

@Service
public class SSDService extends BaseService<SolidStateDrive, SolidStateDriveDto> {
    private final ComputerRepository computerRepository;

    public SSDService(SSDRepository repository, ComputerRepository computerRepository) {
        super();
        setRepository(repository);
        this.computerRepository = computerRepository;
    }

    @Override
    protected SolidStateDriveDto mapToDto(SolidStateDrive entity) {
        return new SolidStateDriveDto(
            entity.getId(),
            entity.getManufacturer(),
            entity.getDriveInterface(),
            entity.getCapacity(),
            entity.getNandType(),
            entity.getWriteSpeed(),
            entity.getReadSpeed(),
            entity.getPowerConsumption(),
            entity.getComputer() != null ? entity.getComputer().getId() : null
        );
    }

    @Override
    protected void updateEntity(SolidStateDrive entity, SolidStateDriveDto dto) {
        entity.setId(dto.id());
        entity.setManufacturer(dto.manufacturer());
        entity.setDriveInterface(dto.driveInterface());
        entity.setCapacity(dto.capacity());
        entity.setNandType(dto.nandType());
        entity.setWriteSpeed(dto.writeSpeed());
        entity.setReadSpeed(dto.readSpeed());
        entity.setPowerConsumption(dto.powerConsumption());

        if (dto.computerId() != null) {
            computerRepository.findById(dto.computerId()).ifPresent(entity::setComputer);
        } else {
            entity.setComputer(null);
        }
    }

    @Override
    protected SolidStateDrive mapToEntity(SolidStateDriveDto dto) {
        SolidStateDrive entity = new SolidStateDrive();
        updateEntity(entity, dto);
        return entity;
    }
}
