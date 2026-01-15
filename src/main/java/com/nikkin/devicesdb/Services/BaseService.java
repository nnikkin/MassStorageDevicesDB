package com.nikkin.devicesdb.Services;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class BaseService<E, D> {
    private CrudRepository<E, Long> crudRepository;

    protected abstract D mapToDto(E entity);
    protected abstract E mapToEntity(D dto);
    protected abstract void updateEntity(E entity, D dto);

    public BaseService() {
        this.crudRepository = null;
    }

    protected void setCrudRepository(CrudRepository<E, Long> crudRepository) {
        if (crudRepository == null)
            throw new RuntimeException("repository не может быть null");

        this.crudRepository = crudRepository;
    }

    public D add(D dto) {
        E entity = mapToEntity(dto);
        E saved = crudRepository.save(entity);
        return mapToDto(saved);
    }

    public List<D> getAll() {
        var allEntities = crudRepository.findAll();
        List<D> allDtos = new ArrayList<>();

        for (var entity : allEntities)
            allDtos.add(mapToDto(entity));

        return allDtos;
    }

    public Optional<D> getById(Long id) {
        return crudRepository.findById(id)
                .map(this::mapToDto);
    }

    public D delete(Long id) {
        E entity = crudRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Запись не найдена"));

        D dto = mapToDto(entity);
        crudRepository.deleteById(id);

        return dto;
    }

    public D update(Long id, D new_dto) {
        E entity = crudRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Запись не найдена"));

        updateEntity(entity, new_dto);

        E updated = crudRepository.save(entity);
        return mapToDto(updated);
    }
}
