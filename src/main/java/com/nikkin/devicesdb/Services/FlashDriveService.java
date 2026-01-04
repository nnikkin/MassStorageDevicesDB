package com.nikkin.devicesdb.Services;

import com.nikkin.devicesdb.Dto.FlashDriveDto;
import com.nikkin.devicesdb.Entities.FlashDrive;
import com.nikkin.devicesdb.Repos.FlashDriveRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Validated
public class FlashDriveService {
    private final FlashDriveRepository flashDriveRepository;

    public FlashDriveService(FlashDriveRepository flashDriveRepository) {
        this.flashDriveRepository = flashDriveRepository;
    }

    public FlashDriveDto add(FlashDriveDto dto) {
        FlashDrive entity = mapToEntity(dto);
        FlashDrive saved = flashDriveRepository.save(entity);
        return mapToDto(saved);
    }

    public List<FlashDriveDto> getAll() {
        List<FlashDrive> allEntities = (List<FlashDrive>) flashDriveRepository.findAll();
        List<FlashDriveDto> allDtos = new ArrayList<>();

        for (var entity : allEntities)
            allDtos.add(mapToDto(entity));

        return allDtos;
    }

    public Optional<FlashDriveDto> getById(Long id) {
        return flashDriveRepository.findById(id)
                .map(this::mapToDto);
    }

    public FlashDriveDto delete(Long id) {
        FlashDrive entity = flashDriveRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Запись не найдена"));

        FlashDriveDto dto = mapToDto(entity); // Маппинг в DTO
        flashDriveRepository.deleteById(id);

        return dto;
    }

    public FlashDriveDto update(Long id, FlashDriveDto new_dto) {
        FlashDrive entity = flashDriveRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Запись не найдена"));

        entity.setName(new_dto.name());
        entity.setUsbInterface(new_dto.usbInterface());
        entity.setUsbType(new_dto.usbType());
        entity.setCapacity(new_dto.capacity());
        entity.setWriteSpeed(new_dto.writeSpeed());
        entity.setReadSpeed(new_dto.readSpeed());

        FlashDrive updated = flashDriveRepository.save(entity);
        return mapToDto(updated);
    }

    // Мапперы
    private FlashDriveDto mapToDto(FlashDrive entity) {
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

    private FlashDrive mapToEntity(FlashDriveDto dto) {
        FlashDrive entity = new FlashDrive();

        entity.setId(dto.id());
        entity.setName(dto.name());
        entity.setUsbInterface(dto.usbInterface());
        entity.setUsbType(dto.usbType());
        entity.setCapacity(dto.capacity());
        entity.setWriteSpeed(dto.writeSpeed());
        entity.setReadSpeed(dto.readSpeed());

        return entity;
    }
}
