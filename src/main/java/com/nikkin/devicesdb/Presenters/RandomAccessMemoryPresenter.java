package com.nikkin.devicesdb.Presenters;

import com.nikkin.devicesdb.Dto.RandomAccessMemoryDto;
import com.nikkin.devicesdb.Services.RandomAccessMemoryService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RandomAccessMemoryPresenter {
    private final RandomAccessMemoryService randomAccessMemoryService;

    public RandomAccessMemoryPresenter(RandomAccessMemoryService randomAccessMemoryService) {
        this.randomAccessMemoryService = randomAccessMemoryService;
    }

    public List<RandomAccessMemoryDto> loadAllRam() {
        return randomAccessMemoryService.getAll();
    }

    public void addRam(RandomAccessMemoryDto dto) {
        randomAccessMemoryService.add(dto);
    }

    public void updateRam(Long id, RandomAccessMemoryDto dto) {
        randomAccessMemoryService.update(id, dto);
    }

    public void deleteRam(List<Long> ids) {
        ids.forEach(randomAccessMemoryService::delete);
    }

    public RandomAccessMemoryDto getRamById(Long id) {
        return randomAccessMemoryService.getById(id)
                .orElseThrow(() -> new RuntimeException("RandomAccessMemory not found"));
    }
}