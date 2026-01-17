package com.nikkin.devicesdb.Services;

import com.nikkin.devicesdb.Dto.FlashDriveDto;
import com.nikkin.devicesdb.Entities.FlashDrive;
import com.nikkin.devicesdb.Repos.ComputerRepository;
import com.nikkin.devicesdb.Repos.FlashDriveRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class FlashDriveService extends BaseService<FlashDrive, FlashDriveDto> {
    private final ComputerRepository computerRepository;

    public FlashDriveService(FlashDriveRepository repository, ComputerRepository computerRepository) {
        super();
        setRepository(repository);
        this.computerRepository = computerRepository;
    }

    @Override
    protected FlashDriveDto mapToDto(FlashDrive entity) {
        return new FlashDriveDto(
            entity.getId(),
            entity.getName(),
            entity.getUsbInterface(),
            entity.getCapacity(),
            entity.getWriteSpeed(),
            entity.getReadSpeed(),
            entity.getComputer() != null ? entity.getComputer().getId() : null
        );
    }

    @Override
    protected void updateEntity(FlashDrive entity, FlashDriveDto dto) {
        entity.setId(dto.id());
        entity.setName(dto.name());
        entity.setUsbInterface(dto.usbInterface());
        entity.setCapacity(dto.capacity());
        entity.setWriteSpeed(dto.writeSpeed());
        entity.setReadSpeed(dto.readSpeed());

        if (dto.computerId() != null) {
            computerRepository.findById(dto.computerId()).ifPresent(entity::setComputer);
        } else {
            entity.setComputer(null);
        }
    }

    @Override
    protected FlashDrive mapToEntity(FlashDriveDto dto) {
        FlashDrive entity = new FlashDrive();
        updateEntity(entity, dto);
        return entity;
    }
}
