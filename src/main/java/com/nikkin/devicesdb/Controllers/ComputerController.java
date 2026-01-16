package com.nikkin.devicesdb.Controllers;

import com.nikkin.devicesdb.Dto.ComputerDto;
import com.nikkin.devicesdb.Services.ComputerService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/computers")
@Validated
public class ComputerController {
    private final ComputerService computerService;

    public ComputerController(ComputerService computerService) {
        this.computerService = computerService;
    }

    @PostMapping
    public ResponseEntity<ComputerDto> addComputer(@RequestBody @Valid ComputerDto computerDto) {
        ComputerDto createdComputerDto = computerService.add(computerDto);
        return new ResponseEntity<>(createdComputerDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ComputerDto>> getAllComputers() {
        List<ComputerDto> allComputerDtos = computerService.getAll();
        return ResponseEntity.ok(allComputerDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ComputerDto> getComputerById(@PathVariable Long id) {
        ComputerDto computerDto = computerService.getById(id)
                .orElseThrow(() -> new EntityNotFoundException("Запись не найдена"));

        return ResponseEntity.ok(computerDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ComputerDto> deleteComputer(@PathVariable Long id) {
        ComputerDto deleted = computerService.delete(id);
        return ResponseEntity.ok(deleted); // 200 OK с телом
    }

    @PutMapping("/{id}")
    public ResponseEntity<ComputerDto> replaceComputer(@PathVariable Long id, @RequestBody @Valid ComputerDto computerDto) {
        ComputerDto updatedComputerDto = computerService.update(id, computerDto);
        return ResponseEntity.ok(updatedComputerDto);
    }
}
