package com.nikkin.devicesdb.Services;

import com.nikkin.devicesdb.Dto.RandomAccessMemoryDto;
import com.nikkin.devicesdb.Entities.Computer;
import com.nikkin.devicesdb.Entities.RandomAccessMemory;
import com.nikkin.devicesdb.Repos.ComputerRepository;
import com.nikkin.devicesdb.Repos.RAMRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class RAMService extends BaseService<RandomAccessMemory, RandomAccessMemoryDto> {
    private final ComputerRepository computerRepository;
    public RAMService(RAMRepository repository, ComputerRepository computerRepository) {
        super();
        setRepository(repository);
        this.computerRepository = computerRepository;
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
            entity.getComputer() != null ? entity.getComputer().getId() : null
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

        if (dto.computerId() != null) {
            computerRepository.findById(dto.computerId()).ifPresent(entity::setComputer);
        } else {
            entity.setComputer(null);
        }
    }

    @Override
    protected RandomAccessMemory mapToEntity(RandomAccessMemoryDto dto) {
        RandomAccessMemory entity = new RandomAccessMemory();
        updateEntity(entity, dto);
        return entity;
    }
}
