package com.nikkin.devicesdb.Controllers;

import com.nikkin.devicesdb.Dto.OpticalDiscDto;
import com.nikkin.devicesdb.Services.OpticalDiscService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("api/flash")
public class OpticalDiscController {
    private final OpticalDiscService opticalDiscService;

    public OpticalDiscController(OpticalDiscService opticalDiscService) {
        this.opticalDiscService = opticalDiscService;
    }

    @PostMapping
    public ResponseEntity<OpticalDiscDto> addOpticalDisc(@RequestBody OpticalDiscDto opticalDiscDto) {
        OpticalDiscDto createdOpticalDiscDto = opticalDiscService.add(opticalDiscDto);
        return new ResponseEntity<OpticalDiscDto>(createdOpticalDiscDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<OpticalDiscDto>> getAllOpticalDiscs() {
        List<OpticalDiscDto> allOpticalDiscDtos = opticalDiscService.getAll();
        return new ResponseEntity<List<OpticalDiscDto>>(allOpticalDiscDtos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OpticalDiscDto> getOpticalDiscById(@PathVariable Long id) {
        OpticalDiscDto opticalDiscDto = opticalDiscService.getById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );
        return new ResponseEntity<OpticalDiscDto>(opticalDiscDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<OpticalDiscDto> deleteOpticalDisc(@PathVariable Long id) {
        OpticalDiscDto deleted = opticalDiscService.delete(id);
        return ResponseEntity.ok(deleted); // 200 OK с телом
    }

    @PutMapping("/{id}")
    public ResponseEntity<OpticalDiscDto> replaceOpticalDisc(@PathVariable Long id, @RequestBody OpticalDiscDto opticalDiscDto) {
        OpticalDiscDto updatedOpticalDiscDto = opticalDiscService.update(id, opticalDiscDto);
        return new ResponseEntity<OpticalDiscDto>(updatedOpticalDiscDto, HttpStatus.OK);
    }
}
