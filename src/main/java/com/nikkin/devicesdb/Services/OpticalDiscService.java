package com.nikkin.devicesdb.Services;

import com.nikkin.devicesdb.Dto.OpticalDiscDto;
import com.nikkin.devicesdb.Entities.OpticalDisc;
import com.nikkin.devicesdb.Repos.OpticalDiscRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OpticalDiscService {
    private final OpticalDiscRepository opticalDiscRepository;

    public OpticalDiscService(OpticalDiscRepository opticalDiscRepository) {
        this.opticalDiscRepository = opticalDiscRepository;
    }

    public OpticalDiscDto add(OpticalDiscDto dto) {
        OpticalDisc entity = mapToEntity(dto);
        OpticalDisc saved = opticalDiscRepository.save(entity);
        return mapToDto(saved);
    }

    public List<OpticalDiscDto> getAll() {
        List<OpticalDisc> allEntities = (List<OpticalDisc>) opticalDiscRepository.findAll();
        List<OpticalDiscDto> allDtos = new ArrayList<>();

        for (var entity : allEntities)
            allDtos.add(mapToDto(entity));

        return allDtos;
    }

    public Optional<OpticalDiscDto> getById(Long id) {
        return opticalDiscRepository.findById(id)
                .map(this::mapToDto);
    }

    public OpticalDiscDto delete(Long id) {
        OpticalDisc entity = opticalDiscRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Запись не найдена"));

        OpticalDiscDto dto = mapToDto(entity); // Маппинг в DTO
        opticalDiscRepository.deleteById(id);

        return dto;
    }

    public OpticalDiscDto update(Long id, OpticalDiscDto new_dto) {
        OpticalDisc old_entity = opticalDiscRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Запись не найдена"));
        OpticalDisc new_entity = mapToEntity(new_dto);
        opticalDiscRepository.save(new_entity);

        return new_dto;
    }

    // Мапперы
    private OpticalDiscDto mapToDto(OpticalDisc entity) {
        /*
            public OpticalDiscDto(
                @NotBlank String name,
                @NotBlank String type,
                @Positive Float capacity,
                @Positive Integer speedMultiplier,
                String rewriteType,
                @Positive Integer layers
            )
        */

        return new OpticalDiscDto(
                entity.getName(),
                entity.getType(),
                entity.getCapacity(),
                entity.getSpeedMultiplier(),
                entity.getRewriteType(),
                entity.getLayers()
        );
    }

    private OpticalDisc mapToEntity(OpticalDiscDto dto) {
        OpticalDisc entity = new OpticalDisc();
        entity.setName(dto.name());
        entity.setCapacity(dto.capacity());
        entity.setType(dto.type());
        entity.setRewriteType(dto.rewriteType());
        entity.setSpeedMultiplier(dto.speedMultiplier());
        entity.setLayers(dto.layers());
        return entity;
    }
}
