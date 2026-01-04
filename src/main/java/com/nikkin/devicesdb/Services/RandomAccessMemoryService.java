package com.nikkin.devicesdb.Services;

import com.nikkin.devicesdb.Dto.RandomAccessMemoryDto;
import com.nikkin.devicesdb.Entities.RandomAccessMemory;
import com.nikkin.devicesdb.Repos.RandomAccessMemoryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Validated
public class RandomAccessMemoryService {
    private final RandomAccessMemoryRepository randomAccessMemoryRepository;

    public RandomAccessMemoryService(RandomAccessMemoryRepository randomAccessMemoryRepository) {
        this.randomAccessMemoryRepository = randomAccessMemoryRepository;
    }

    public RandomAccessMemoryDto add(RandomAccessMemoryDto dto) {
        RandomAccessMemory entity = mapToEntity(dto);
        RandomAccessMemory saved = randomAccessMemoryRepository.save(entity);
        return mapToDto(saved);
    }

    public List<RandomAccessMemoryDto> getAll() {
        List<RandomAccessMemory> allEntities = (List<RandomAccessMemory>) randomAccessMemoryRepository.findAll();
        List<RandomAccessMemoryDto> allDtos = new ArrayList<>();

        for (var entity : allEntities)
            allDtos.add(mapToDto(entity));

        return allDtos;
    }

    public Optional<RandomAccessMemoryDto> getById(Long id) {
        return randomAccessMemoryRepository.findById(id)
                .map(this::mapToDto);
    }

    public RandomAccessMemoryDto delete(Long id) {
        RandomAccessMemory entity = randomAccessMemoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Запись не найдена"));

        RandomAccessMemoryDto dto = mapToDto(entity); // Маппинг в DTO
        randomAccessMemoryRepository.deleteById(id);

        return dto;
    }

    public RandomAccessMemoryDto update(Long id, RandomAccessMemoryDto new_dto) {
        RandomAccessMemory entity = randomAccessMemoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Запись не найдена"));

        entity.setName(new_dto.name());
        entity.setModel(new_dto.model());
        entity.setManufacturer(new_dto.manufacturer());
        entity.setModuleType(new_dto.moduleType());
        entity.setMemoryType(new_dto.memoryType());
        entity.setCapacity(new_dto.capacity());
        entity.setFrequencyMhz(new_dto.frequencyMhz());
        entity.setCasLatency(new_dto.casLatency());
        entity.setSupportsEcc(new_dto.supportsEcc());

        RandomAccessMemory updated = randomAccessMemoryRepository.save(entity);

        return mapToDto(updated);
    }

    // Мапперы
    private RandomAccessMemoryDto mapToDto(RandomAccessMemory entity) {
        return new RandomAccessMemoryDto(
                entity.getId(),
                entity.getName(),
                entity.getModel(),
                entity.getManufacturer(),
                entity.getMemoryType(),
                entity.getModuleType(),
                entity.getCapacity(),
                entity.getFrequencyMhz(),
                entity.getCasLatency(),
                entity.isSupportingEcc()
        );
    }

    private RandomAccessMemory mapToEntity(RandomAccessMemoryDto dto) {
        RandomAccessMemory entity = new RandomAccessMemory();

        entity.setId(dto.id());
        entity.setName(dto.name());
        entity.setModel(dto.model());
        entity.setManufacturer(dto.manufacturer());
        entity.setModuleType(dto.moduleType());
        entity.setCapacity(dto.capacity());
        entity.setMemoryType(dto.memoryType());
        entity.setFrequencyMhz(dto.frequencyMhz());
        entity.setCasLatency(dto.casLatency());
        entity.setSupportsEcc(dto.supportsEcc());

        return entity;
    }
}
