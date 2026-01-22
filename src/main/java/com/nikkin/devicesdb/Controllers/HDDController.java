package com.nikkin.devicesdb.Controllers;

import com.nikkin.devicesdb.Dto.HardDiskDriveDto;
import com.nikkin.devicesdb.Services.HddService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/hdd")
public class HDDController {
    @Autowired
    private HddService hddService;

    public HDDController(HddService hddService) {
        this.hddService = hddService;
    }

    @PostMapping
    public ResponseEntity<HardDiskDriveDto> addHardDiskDrive(@RequestBody @Valid HardDiskDriveDto hddDto) {
        HardDiskDriveDto createdHddDto = hddService.add(hddDto);
        return new ResponseEntity<>(createdHddDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<HardDiskDriveDto>> getAllHardDiskDrives() {
        List<HardDiskDriveDto> allHddDtos = hddService.getAll();
        return ResponseEntity.ok(allHddDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HardDiskDriveDto> getHardDiskDriveById(@PathVariable Integer id) {
        HardDiskDriveDto hddDto = hddService.getById(id);
        return ResponseEntity.ok(hddDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HardDiskDriveDto> deleteHardDiskDrive(@PathVariable Integer id) {
        HardDiskDriveDto deleted = hddService.delete(id);
        return ResponseEntity.ok(deleted); // 200 OK с телом
    }

    @PutMapping("/{id}")
    public ResponseEntity<HardDiskDriveDto> replaceHardDiskDrive(@PathVariable Integer id, @RequestBody @Valid HardDiskDriveDto hddDto) {
        HardDiskDriveDto updatedHddDto = hddService.update(id, hddDto);
        return ResponseEntity.ok(updatedHddDto);
    }
}
