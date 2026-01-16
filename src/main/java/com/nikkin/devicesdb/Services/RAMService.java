package com.nikkin.devicesdb.Services;

import com.nikkin.devicesdb.Dto.RandomAccessMemoryDto;
import com.nikkin.devicesdb.Entities.RandomAccessMemory;
import com.nikkin.devicesdb.Repos.RAMRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class RAMService extends BaseService<RandomAccessMemory, RandomAccessMemoryDto> {
    public RAMService(RAMRepository repository) {
        super();
        setCrudRepository(repository);
    }

    @Override
    protected RandomAccessMemoryDto mapToDto(RandomAccessMemory entity) {
        return new RandomAccessMemoryDto(
            entity.getId(),
            entity.getModel(),
            entity.getManufacturer(),
            entity.getMemoryType(),
            entity.getModuleType(),
            entity.getCapacity(),
            entity.getFrequencyMhz(),
            entity.getCasLatency()
        );
    }

    @Override
    protected void updateEntity(RandomAccessMemory entity, RandomAccessMemoryDto dto) {
        entity.setId(dto.id());
        entity.setModel(dto.model());
        entity.setManufacturer(dto.manufacturer());
        entity.setModuleType(dto.moduleType());
        entity.setCapacity(dto.capacity());
        entity.setMemoryType(dto.memoryType());
        entity.setFrequencyMhz(dto.frequencyMhz());
        entity.setCasLatency(dto.casLatency());
    }

    @Override
    protected RandomAccessMemory mapToEntity(RandomAccessMemoryDto dto) {
        RandomAccessMemory entity = new RandomAccessMemory();
        updateEntity(entity, dto);
        return entity;
    }
}
