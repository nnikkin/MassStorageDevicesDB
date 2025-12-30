package com.nikkin.devicesdb.Services;

import com.nikkin.devicesdb.Dto.RandomAccessMemoryDto;
import com.nikkin.devicesdb.Entities.RandomAccessMemory;
import com.nikkin.devicesdb.Repos.RandomAccessMemoryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
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
        RandomAccessMemory old_entity = randomAccessMemoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Запись не найдена"));
        RandomAccessMemory new_entity = mapToEntity(new_dto);
        randomAccessMemoryRepository.save(new_entity);

        return new_dto;
    }

    // Мапперы
    private RandomAccessMemoryDto mapToDto(RandomAccessMemory entity) {
        return new RandomAccessMemoryDto(
                entity.getModel(),
                entity.getManufacturer(),
                entity.getType(),
                entity.getModuleType(),
                entity.getCapacity(),
                entity.getFrequencyMhz(),
                entity.getCasLatency(),
                entity.isSupportingEcc()
        );
    }

    private RandomAccessMemory mapToEntity(RandomAccessMemoryDto dto) {
        RandomAccessMemory entity = new RandomAccessMemory();
        entity.setModel(dto.model());
        entity.setManufacturer(dto.manufacturer());
        entity.setModuleType(dto.moduleType());
        entity.setCapacity(dto.capacity());
        entity.setType(dto.type());
        entity.setFrequencyMhz(dto.frequencyMhz());
        entity.setCasLatency(dto.casLatency());
        entity.setSupportsEcc(dto.supportsEcc());

        return entity;
    }
}
