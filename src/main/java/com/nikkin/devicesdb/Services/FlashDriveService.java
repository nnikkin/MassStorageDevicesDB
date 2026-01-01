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
        FlashDrive old_entity = flashDriveRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Запись не найдена"));

        old_entity.setName(new_dto.name());
        old_entity.setUsbInterface(new_dto.usbInterface());
        old_entity.setUsbType(new_dto.usbType());
        old_entity.setCapacity(new_dto.capacity());
        old_entity.setWriteSpeed(new_dto.writeSpeed());
        old_entity.setReadSpeed(new_dto.readSpeed());

        FlashDrive updated = flashDriveRepository.save(old_entity);
        return mapToDto(updated);
    }

    public Optional<FlashDriveDto> getFlashDriveByName(String name) {
        return flashDriveRepository.findFirstByName(name)
                .map(this::mapToDto);
    }

    public List<FlashDriveDto> getByUsbInterface(String usbInterface) {
        return flashDriveRepository.getByUsbInterface(usbInterface).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<FlashDriveDto> getByUsbType(String usbType) {
        return flashDriveRepository.getByUsbType(usbType).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<FlashDriveDto> getByCapacity(float capacity) {
        return flashDriveRepository.getByCapacity(capacity).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<FlashDriveDto> getByWriteSpeed(float writeSpeed) {
        return flashDriveRepository.getByWriteSpeed(writeSpeed).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<FlashDriveDto> getByReadSpeed(float readSpeed) {
        return flashDriveRepository.getByReadSpeed(readSpeed).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    // Мапперы
    private FlashDriveDto mapToDto(FlashDrive entity) {
        /*
            public FlashDriveDto(
                String name,
                String usbInterface,
                String usbType,
                @Positive Float capacity,
                @Positive Float writeSpeed,
                @Positive Float readSpeed
            )
         */
        return new FlashDriveDto(
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
        entity.setName(dto.name());
        entity.setUsbInterface(dto.usbInterface());
        entity.setUsbType(dto.usbType());
        entity.setCapacity(dto.capacity());
        entity.setWriteSpeed(dto.writeSpeed());
        entity.setReadSpeed(dto.readSpeed());
        return entity;
    }
}
