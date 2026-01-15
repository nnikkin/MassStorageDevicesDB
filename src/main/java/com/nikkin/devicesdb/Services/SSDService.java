package com.nikkin.devicesdb.Services;

import com.nikkin.devicesdb.Dto.SolidStateDriveDto;
import com.nikkin.devicesdb.Entities.SolidStateDrive;
import com.nikkin.devicesdb.Repos.SSDRepository;
import org.springframework.stereotype.Service;

@Service
public class SSDService extends BaseService<SolidStateDrive, SolidStateDriveDto> {
    public SSDService(SSDRepository repository) {
        super();
        setCrudRepository(repository);
    }

    @Override
    protected SolidStateDriveDto mapToDto(SolidStateDrive entity) {
        return new SolidStateDriveDto(
            entity.getId(),
            entity.getName(),
            entity.getDriveInterface(),
            entity.getCapacity(),
            entity.getNandType(),
            entity.getTbw(),
            entity.getWriteSpeed(),
            entity.getReadSpeed(),
            entity.getPowerConsumption()
        );
    }

    @Override
    protected void updateEntity(SolidStateDrive entity, SolidStateDriveDto dto) {
        entity.setId(dto.id());
        entity.setName(dto.name());
        entity.setDriveInterface(dto.driveInterface());
        entity.setCapacity(dto.capacity());
        entity.setNandType(dto.nandType());
        entity.setTbw(dto.tbw());
        entity.setWriteSpeed(dto.writeSpeed());
        entity.setReadSpeed(dto.readSpeed());
        entity.setPowerConsumption(dto.powerConsumption());
    }

    @Override
    protected SolidStateDrive mapToEntity(SolidStateDriveDto dto) {
        SolidStateDrive entity = new SolidStateDrive();
        updateEntity(entity, dto);
        return entity;
    }
}
