package com.nikkin.devicesdb.Controllers;

import com.nikkin.devicesdb.Dto.RandomAccessMemoryDto;
import com.nikkin.devicesdb.Services.RamService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/ram")
public class RAMController {
    @Autowired
    private RamService ramService;

    public RAMController(RamService ramService) {
        this.ramService = ramService;
    }

    @PostMapping
    public ResponseEntity<RandomAccessMemoryDto> addRandomAccessMemory(@RequestBody @Valid RandomAccessMemoryDto ramDto) {
        RandomAccessMemoryDto createdRamDto = ramService.add(ramDto);
        return new ResponseEntity<>(createdRamDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<RandomAccessMemoryDto>> getAllRandomAccessMemorys() {
        List<RandomAccessMemoryDto> allRamDtos = ramService.getAll();
        return new ResponseEntity<>(allRamDtos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RandomAccessMemoryDto> getRandomAccessMemoryById(@PathVariable Integer id) {
        //RandomAccessMemoryDto ramDto = ramService.getById(id).orElseThrow(
        //        () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        //);
        //return new ResponseEntity<>(ramDto, HttpStatus.OK);
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RandomAccessMemoryDto> deleteRandomAccessMemory(@PathVariable Integer id) {
        RandomAccessMemoryDto deleted = ramService.delete(id);
        return ResponseEntity.ok(deleted); // 200 OK с телом
    }

    @PutMapping("/{id}")
    public ResponseEntity<RandomAccessMemoryDto> replaceRandomAccessMemory(@PathVariable Integer id, @RequestBody @Valid RandomAccessMemoryDto ramDto) {
        RandomAccessMemoryDto updatedRamDto = ramService.update(id, ramDto);
        return new ResponseEntity<>(updatedRamDto, HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public String handleException(EntityNotFoundException exception) {
        return exception.getMessage();
    }
}
