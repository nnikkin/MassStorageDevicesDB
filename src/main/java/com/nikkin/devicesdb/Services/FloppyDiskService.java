package com.nikkin.devicesdb.Services;

import com.nikkin.devicesdb.Dto.FloppyDiskDto;
import com.nikkin.devicesdb.Entities.FloppyDisk;
import com.nikkin.devicesdb.Repos.FloppyDiskRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Validated
public class FloppyDiskService {
    private final FloppyDiskRepository floppyDiskRepository;

    public FloppyDiskService(FloppyDiskRepository floppyDiskRepository) {
        this.floppyDiskRepository = floppyDiskRepository;
    }

    public FloppyDiskDto add(FloppyDiskDto dto) {
        FloppyDisk entity = mapToEntity(dto);
        FloppyDisk saved = floppyDiskRepository.save(entity);
        return mapToDto(saved);
    }

    public List<FloppyDiskDto> getAll() {
        List<FloppyDisk> allEntities = (List<FloppyDisk>) floppyDiskRepository.findAll();
        List<FloppyDiskDto> allDtos = new ArrayList<>();

        for (var entity : allEntities)
            allDtos.add(mapToDto(entity));

        return allDtos;
    }

    public Optional<FloppyDiskDto> getById(Long id) {
        return floppyDiskRepository.findById(id)
                .map(this::mapToDto);
    }

    public FloppyDiskDto delete(Long id) {
        FloppyDisk entity = floppyDiskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Запись не найдена"));

        FloppyDiskDto dto = mapToDto(entity); // Маппинг в DTO
        floppyDiskRepository.deleteById(id);

        return dto;
    }

    public FloppyDiskDto update(Long id, FloppyDiskDto new_dto) {
        FloppyDisk entity = floppyDiskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Запись не найдена"));

        entity.setFormat(new_dto.format());
        entity.setDoubleSided(new_dto.isDoubleSided());
        entity.setDiskDensity(new_dto.diskDensity());
        entity.setName(new_dto.name());
        entity.setCapacity(new_dto.capacity());

        FloppyDisk updated = floppyDiskRepository.save(entity);

        return mapToDto(updated);
    }

    // Мапперы
    private FloppyDiskDto mapToDto(FloppyDisk entity) {
        return new FloppyDiskDto(
                entity.getId(),
                entity.getName(),
                entity.getCapacity(),
                entity.getFormat(),
                entity.getDiskDensity(),
                entity.isDoubleSided()
        );
    }

    private FloppyDisk mapToEntity(FloppyDiskDto dto) {
        FloppyDisk entity = new FloppyDisk();
        entity.setName(dto.name());
        entity.setCapacity(dto.capacity());
        entity.setFormat(dto.format());
        entity.setDiskDensity(dto.diskDensity());
        entity.setDoubleSided(dto.isDoubleSided());
        return entity;
    }
}
