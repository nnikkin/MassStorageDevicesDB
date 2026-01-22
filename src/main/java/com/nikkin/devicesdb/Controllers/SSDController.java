package com.nikkin.devicesdb.Controllers;

import com.nikkin.devicesdb.Dto.SolidStateDriveDto;
import com.nikkin.devicesdb.Services.SsdService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/ssd")
public class SSDController {
    @Autowired
    private SsdService ssdService;

    public SSDController(SsdService ssdService) {
        this.ssdService = ssdService;
    }

    @PostMapping
    public ResponseEntity<SolidStateDriveDto> addSolidStateDrive(@RequestBody @Valid SolidStateDriveDto ssdDto) {
        SolidStateDriveDto createdSsdDto = ssdService.add(ssdDto);
        return ResponseEntity.ok(createdSsdDto);
    }

    @GetMapping
    public ResponseEntity<List<SolidStateDriveDto>> getAllSolidStateDrives() {
        List<SolidStateDriveDto> allSsdDtos = ssdService.getAll();
        return new ResponseEntity<>(allSsdDtos, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SolidStateDriveDto> getSolidStateDriveById(@PathVariable Integer id) {
        SolidStateDriveDto ssdDto = ssdService.getById(id);
        return ResponseEntity.ok(ssdDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SolidStateDriveDto> deleteSolidStateDrive(@PathVariable Integer id) {
        SolidStateDriveDto deleted = ssdService.delete(id);
        return ResponseEntity.ok(deleted); // 200 OK с телом
    }

    @PutMapping("/{id}")
    public ResponseEntity<SolidStateDriveDto> replaceSolidStateDrive(@PathVariable Integer id, @RequestBody @Valid SolidStateDriveDto ssdDto) {
        SolidStateDriveDto updatedSsdDto = ssdService.update(id, ssdDto);
        return ResponseEntity.ok(updatedSsdDto);
    }
}
