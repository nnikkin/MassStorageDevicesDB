package com.nikkin.devicesdb.Entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class HardDiskDrive {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String manufacturer;

    private String driveInterface;       // SATA...

    private Float capacity;

    private String format;           // 3.5", 2.5"

    private Float powerConsumption;

    @ManyToOne
    @JoinColumn(name = "computer_id") // Имя колонки во внешнем ключе в БД
    private Computer computer;
}
