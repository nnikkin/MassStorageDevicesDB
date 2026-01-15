package com.nikkin.devicesdb.Controllers;

import com.nikkin.devicesdb.Dto.HardDiskDriveDto;
import com.nikkin.devicesdb.Services.HDDService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("api/hdd")
public class HDDController {
    private final HDDService hddService;

    public HDDController(HDDService hddService) {
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
        return new ResponseEntity<>(allHddDtos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HardDiskDriveDto> getHardDiskDriveById(@PathVariable Long id) {
        HardDiskDriveDto hddDto = hddService.getById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );
        return new ResponseEntity<>(hddDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HardDiskDriveDto> deleteHardDiskDrive(@PathVariable Long id) {
        HardDiskDriveDto deleted = hddService.delete(id);
        return ResponseEntity.ok(deleted); // 200 OK с телом
    }

    @PutMapping("/{id}")
    public ResponseEntity<HardDiskDriveDto> replaceHardDiskDrive(@PathVariable Long id, @RequestBody @Valid HardDiskDriveDto hddDto) {
        HardDiskDriveDto updatedHddDto = hddService.update(id, hddDto);
        return new ResponseEntity<>(updatedHddDto, HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public String handleException(EntityNotFoundException exception) {
        return exception.getMessage();
    }
}
