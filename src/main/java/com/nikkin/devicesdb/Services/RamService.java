package com.nikkin.devicesdb.Services;

import com.nikkin.devicesdb.Dto.RandomAccessMemoryDto;
import com.nikkin.devicesdb.Entities.RandomAccessMemory;
import com.nikkin.devicesdb.Mappers.RamMapper;
import com.nikkin.devicesdb.Repos.RamRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Validated
public class RamService implements IService<RandomAccessMemoryDto> {
    @Autowired
    private RamRepository repo;

    @Autowired
    private RamMapper mapper;

    @Override
    public RandomAccessMemoryDto add(RandomAccessMemoryDto dto) {
        // Создаём объект из DTO
        RandomAccessMemory newRandomAccessMemory = toEntity(dto);

        // Сохраняем объект
        RandomAccessMemory savedRandomAccessMemory = repo.save(newRandomAccessMemory);

        // Создаём RandomAccessMemoryDto из полученного объекта
        RandomAccessMemoryDto restoredDto = toDto(savedRandomAccessMemory);
        return restoredDto;
    }

    @Override
    public RandomAccessMemoryDto getById(Integer id) {
        // получаем RandomAccessMemory из БД
        RandomAccessMemory computer = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Запись не найдена"));

        // получаем RandomAccessMemoryDto
        return toDto(computer);
    }

    @Override
    public List<RandomAccessMemoryDto> getAll() {
        return repo.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public RandomAccessMemoryDto delete(Integer id) {
        // получаем RandomAccessMemory из БД
        RandomAccessMemory ram = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Запись не найдена"));

        // Удаляем объект
        repo.delete(ram);

        // получаем RandomAccessMemoryDto
        return toDto(ram);
    }

    @Override
    public RandomAccessMemoryDto update(Integer id, RandomAccessMemoryDto new_dto) {
        // получаем RandomAccessMemory из БД
        RandomAccessMemory ram = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Запись не найдена"));

        // изменяем значения полей RandomAccessMemory на новые
        var tmp = mapper.toEntity(new_dto);
        ram.setManufacturer(tmp.getManufacturer());
        ram.setCapacity(tmp.getCapacity());
        ram.setMemoryType(tmp.getMemoryType());
        ram.setFrequencyMhz(tmp.getFrequencyMhz());
        ram.setModuleType(tmp.getModuleType());
        ram.setModel(tmp.getModel());
        ram.setComputer(tmp.getComputer());
        RandomAccessMemory updatedRandomAccessMemory = repo.save(ram);

        // Создаём RandomAccessMemoryDto из полученного объекта
        return toDto(updatedRandomAccessMemory);
    }

    @Override
    public int getItemsCount() {
        return Math.toIntExact(repo.count());
    }

    private RandomAccessMemory toEntity(RandomAccessMemoryDto dto) {
        return mapper.toEntity(dto);
    }

    private RandomAccessMemoryDto toDto(RandomAccessMemory entity) {
        return mapper.toDto(entity);
    }
}
