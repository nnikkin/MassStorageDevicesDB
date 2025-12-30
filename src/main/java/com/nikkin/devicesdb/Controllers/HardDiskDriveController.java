package com.nikkin.devicesdb.Controllers;

import com.nikkin.devicesdb.Dto.HardDiskDriveDto;
import com.nikkin.devicesdb.Services.HardDiskDriveService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("api/flash")
public class HardDiskDriveController {
    private final HardDiskDriveService hddService;

    public HardDiskDriveController(HardDiskDriveService hddService) {
        this.hddService = hddService;
    }

    @PostMapping
    public ResponseEntity<HardDiskDriveDto> addHardDiskDrive(@RequestBody HardDiskDriveDto hddDto) {
        HardDiskDriveDto createdHddDto = hddService.add(hddDto);
        return new ResponseEntity<HardDiskDriveDto>(createdHddDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<HardDiskDriveDto>> getAllHardDiskDrives() {
        List<HardDiskDriveDto> allHddDtos = hddService.getAll();
        return new ResponseEntity<List<HardDiskDriveDto>>(allHddDtos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HardDiskDriveDto> getHardDiskDriveById(@PathVariable Long id) {
        HardDiskDriveDto hddDto = hddService.getById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );
        return new ResponseEntity<HardDiskDriveDto>(hddDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HardDiskDriveDto> deleteHardDiskDrive(@PathVariable Long id) {
        HardDiskDriveDto deleted = hddService.delete(id);
        return ResponseEntity.ok(deleted); // 200 OK с телом
    }

    @PutMapping("/{id}")
    public ResponseEntity<HardDiskDriveDto> replaceHardDiskDrive(@PathVariable Long id, @RequestBody HardDiskDriveDto hddDto) {
        HardDiskDriveDto updatedHddDto = hddService.update(id, hddDto);
        return new ResponseEntity<HardDiskDriveDto>(updatedHddDto, HttpStatus.OK);
    }
}
