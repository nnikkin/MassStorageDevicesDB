package com.nikkin.devicesdb.Services;

import com.nikkin.devicesdb.Dto.FlashDriveDto;
import com.nikkin.devicesdb.Entities.FlashDrive;
import com.nikkin.devicesdb.Repos.FlashDriveRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class FlashDriveService extends BaseService<FlashDrive, FlashDriveDto> {
    public FlashDriveService(FlashDriveRepository repository) {
        super();
        setCrudRepository(repository);
    }

    @Override
    protected FlashDriveDto mapToDto(FlashDrive entity) {
        return new FlashDriveDto(
            entity.getId(),
            entity.getName(),
            entity.getUsbInterface(),
            entity.getUsbType(),
            entity.getCapacity(),
            entity.getWriteSpeed(),
            entity.getReadSpeed()
        );
    }

    @Override
    protected void updateEntity(FlashDrive entity, FlashDriveDto dto) {
        entity.setId(dto.id());
        entity.setName(dto.name());
        entity.setUsbInterface(dto.usbInterface());
        entity.setUsbType(dto.usbType());
        entity.setCapacity(dto.capacity());
        entity.setWriteSpeed(dto.writeSpeed());
        entity.setReadSpeed(dto.readSpeed());
    }

    @Override
    protected FlashDrive mapToEntity(FlashDriveDto dto) {
        FlashDrive entity = new FlashDrive();
        updateEntity(entity, dto);
        return entity;
    }
}
