package com.nikkin.devicesdb.Services;

import com.nikkin.devicesdb.Dto.FloppyDiskDto;
import com.nikkin.devicesdb.Entities.FloppyDisk;
import com.nikkin.devicesdb.Repos.FloppyDiskRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
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
        FloppyDisk old_entity = floppyDiskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Запись не найдена"));
        FloppyDisk new_entity = mapToEntity(new_dto);
        floppyDiskRepository.save(new_entity);

        return new_dto;
    }

    // Мапперы
    private FloppyDiskDto mapToDto(FloppyDisk entity) {
        /*
        public FloppyDiskDto(
            String name,
            @Positive Float capacity,
            @Positive Float format,
            String diskDensity
        )
        */

        return new FloppyDiskDto(
                entity.getName(),
                entity.getCapacity(),
                entity.getFormat(),
                entity.getDiskDensity()
        );
    }

    private FloppyDisk mapToEntity(FloppyDiskDto dto) {
        FloppyDisk entity = new FloppyDisk();
        entity.setName(dto.name());
        entity.setCapacity(dto.capacity());
        entity.setFormat(dto.format());
        entity.setDiskDensity(dto.diskDensity());
        return entity;
    }
}
