package com.nikkin.devicesdb.Presenters;

import com.nikkin.devicesdb.Dto.FloppyDiskDto;
import com.nikkin.devicesdb.Services.FloppyDiskService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FloppyDiskPresenter {
    private final FloppyDiskService floppyDiskService;

    public FloppyDiskPresenter(FloppyDiskService floppyDiskService) {
        this.floppyDiskService = floppyDiskService;
    }

    public List<FloppyDiskDto> loadAllFloppyDisks() {
        return floppyDiskService.getAll();
    }

    public void addFloppyDisk(FloppyDiskDto dto) {
        floppyDiskService.add(dto);
    }

    public void updateFloppyDisk(Long id, FloppyDiskDto dto) {
        floppyDiskService.update(id, dto);
    }

    public void deleteFloppyDisks(List<Long> ids) {
        ids.forEach(floppyDiskService::delete);
    }

    public FloppyDiskDto getFloppyDiskById(Long id) {
        return floppyDiskService.getById(id)
                .orElseThrow(() -> new RuntimeException("FloppyDisk not found"));
    }
}