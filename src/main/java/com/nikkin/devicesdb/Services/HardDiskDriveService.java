package com.nikkin.devicesdb.Services;

import com.nikkin.devicesdb.Dto.HardDiskDriveDto;
import com.nikkin.devicesdb.Entities.HardDiskDrive;
import com.nikkin.devicesdb.Repos.HardDiskDriveRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class HardDiskDriveService {
    private final HardDiskDriveRepository hardDiskDriveRepository;

    public HardDiskDriveService(HardDiskDriveRepository hardDiskDriveRepository) {
        this.hardDiskDriveRepository = hardDiskDriveRepository;
    }

    public HardDiskDriveDto add(HardDiskDriveDto dto) {
        HardDiskDrive entity = mapToEntity(dto);
        HardDiskDrive saved = hardDiskDriveRepository.save(entity);
        return mapToDto(saved);
    }

    public List<HardDiskDriveDto> getAll() {
        List<HardDiskDrive> allEntities = (List<HardDiskDrive>) hardDiskDriveRepository.findAll();
        List<HardDiskDriveDto> allDtos = new ArrayList<>();

        for (var entity : allEntities)
            allDtos.add(mapToDto(entity));

        return allDtos;
    }

    public Optional<HardDiskDriveDto> getById(Long id) {
        return hardDiskDriveRepository.findById(id)
                .map(this::mapToDto);
    }

    public HardDiskDriveDto delete(Long id) {
        HardDiskDrive entity = hardDiskDriveRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Запись не найдена"));

        HardDiskDriveDto dto = mapToDto(entity); // Маппинг в DTO
        hardDiskDriveRepository.deleteById(id);

        return dto;
    }

    public HardDiskDriveDto update(Long id, HardDiskDriveDto dto) {
        HardDiskDrive entity = hardDiskDriveRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Запись не найдена"));

        entity.setId(dto.id());
        entity.setName(dto.name());
        entity.setDriveInterface(dto.driveInterface());
        entity.setCapacity(dto.capacity());
        entity.setFormat(dto.format());
        entity.setRpm(dto.rpm());
        entity.setCache(dto.cache());
        entity.setPowerConsumption(dto.powerConsumption());

        HardDiskDrive updated = hardDiskDriveRepository.save(entity);
        return mapToDto(updated);
    }

    // Мапперы
    private HardDiskDriveDto mapToDto(HardDiskDrive entity) {
        return new HardDiskDriveDto(
                entity.getId(),
                entity.getName(),
                entity.getCapacity(),
                entity.getDriveInterface(),
                entity.getFormat(),
                entity.getRpm(),
                entity.getCache(),
                entity.getPowerConsumption()
        );
    }

    private HardDiskDrive mapToEntity(HardDiskDriveDto dto) {
        HardDiskDrive entity = new HardDiskDrive();

        entity.setId(dto.id());
        entity.setName(dto.name());
        entity.setDriveInterface(dto.driveInterface());
        entity.setCapacity(dto.capacity());
        entity.setFormat(dto.format());
        entity.setRpm(dto.rpm());
        entity.setCache(dto.cache());
        entity.setPowerConsumption(dto.powerConsumption());

        return entity;
    }
}
