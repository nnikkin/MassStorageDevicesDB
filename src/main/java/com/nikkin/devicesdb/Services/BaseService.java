package com.nikkin.devicesdb.Services;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class BaseService<E, D> {
    protected JpaRepository<E, Long> repository;

    protected abstract D mapToDto(E entity);
    protected abstract E mapToEntity(D dto);
    protected abstract void updateEntity(E entity, D dto);

    public BaseService() {
        this.repository = null;
    }

    protected void setRepository(JpaRepository<E, Long> repository) {
        if (repository == null)
            throw new RuntimeException("repository не может быть null");

        this.repository = repository;
    }

    public D add(D dto) {
        E entity = mapToEntity(dto);
        E saved = repository.save(entity);
        return mapToDto(saved);
    }

    public List<D> getAll() {
        var allEntities = repository.findAll();
        List<D> allDtos = new ArrayList<>();

        for (var entity : allEntities)
            allDtos.add(mapToDto(entity));

        return allDtos;
    }

    public Optional<D> getById(Long id) {
        return repository.findById(id)
                .map(this::mapToDto);
    }

    public D delete(Long id) {
        E entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Запись не найдена"));

        D dto = mapToDto(entity);
        repository.deleteById(id);

        return dto;
    }

    public D update(Long id, D new_dto) {
        E entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Запись не найдена"));

        updateEntity(entity, new_dto);

        E updated = repository.save(entity);
        return mapToDto(updated);
    }
}
