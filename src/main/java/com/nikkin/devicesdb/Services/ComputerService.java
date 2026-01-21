package com.nikkin.devicesdb.Services;

import com.nikkin.devicesdb.Dto.ComputerDto;
import com.nikkin.devicesdb.Entities.Computer;
import com.nikkin.devicesdb.Mappers.ComputerMapper;
import com.nikkin.devicesdb.Repos.ComputerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Validated
public class ComputerService implements IService<ComputerDto> {
    @Autowired
    private ComputerRepository repo;

    @Autowired
    private ComputerMapper mapper;

    @Override
    public ComputerDto add(ComputerDto dto) {
        // Создаём объект из DTO
        Computer newComputer = toEntity(dto);

        // Сохраняем объект
        Computer savedComputer = repo.save(newComputer);

        // Создаём ComputerDto из полученного объекта
        ComputerDto restoredDto = toDto(savedComputer);
        return restoredDto;
    }

    @Override
    public ComputerDto getById(Integer id) {
        // получаем Computer из БД
        Computer computer = repo.getReferenceById(id);

        // получаем ComputerDto
        return toDto(computer);
    }

    @Override
    public List<ComputerDto> getAll() {
        return repo.findAll()
               .stream()
               .map(this::toDto)
               .toList();
    }

    @Override
    public ComputerDto delete(Integer id) {
        // получаем Computer из БД
        Computer computer = repo.getReferenceById(id);

        // Удаляем объект
        Computer deletedComputer = repo.save(computer);

        // получаем ComputerDto
        return toDto(deletedComputer);
    }

    @Override
    public ComputerDto update(Integer id, ComputerDto new_dto) {
        // получаем Computer из БД
        Computer computer = repo.getReferenceById(id);

        // изменяем значения полей Computer на новые
        var tmp = mapper.toEntity(new_dto);
        computer.setName(tmp.getName());
        computer.setLinkedRams(tmp.getLinkedRams());
        computer.setLinkedHdds(tmp.getLinkedHdds());
        computer.setLinkedSsds(tmp.getLinkedSsds());
        computer.setLinkedFlashDrives(tmp.getLinkedFlashDrives());
        Computer updatedComputer = repo.save(computer);

        // Создаём ComputerDto из полученного объекта
        return toDto(updatedComputer);
    }

    @Override
    public int getItemsCount() {
        return Math.toIntExact(repo.findAll().size());
    }

    private Computer toEntity(ComputerDto dto) {
        return mapper.toEntity(dto);
    }

    private ComputerDto toDto(Computer entity) {
        return mapper.toDto(entity);
    }
}
