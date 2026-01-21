package com.nikkin.devicesdb.Entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class SolidStateDrive {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String manufacturer;

    private String driveInterface;
    
    private Float capacity;
    
    private String nandType;

    private Float writeSpeed;
    
    private Float readSpeed;
    
    private Float powerConsumption;

    @ManyToOne
    @JoinColumn(name = "computer_id") // Имя колонки во внешнем ключе в БД
    private Computer computer;
}
