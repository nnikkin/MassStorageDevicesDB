package com.nikkin.devicesdb.Presenters;

import com.nikkin.devicesdb.Dto.OpticalDiscDto;
import com.nikkin.devicesdb.Services.OpticalDiscService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OpticalDiscPresenter {
    private final OpticalDiscService opticalDiscService;

    public OpticalDiscPresenter(OpticalDiscService opticalDiscService) {
        this.opticalDiscService = opticalDiscService;
    }

    public List<OpticalDiscDto> loadAllOpticalDiscs() {
        return opticalDiscService.getAll();
    }

    public void addOpticalDisc(OpticalDiscDto dto) {
        opticalDiscService.add(dto);
    }

    public void updateOpticalDisc(Long id, OpticalDiscDto dto) {
        opticalDiscService.update(id, dto);
    }

    public void deleteOpticalDiscs(List<Long> ids) {
        ids.forEach(opticalDiscService::delete);
    }

    public OpticalDiscDto getOpticalDiscById(Long id) {
        return opticalDiscService.getById(id)
                .orElseThrow(() -> new RuntimeException("OpticalDisc not found"));
    }
}