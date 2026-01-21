package com.nikkin.devicesdb.Entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class FlashDrive {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private Float capacity;

    private String usbInterface;

    private Float writeSpeed;

    private Float readSpeed;

    @ManyToOne
    @JoinColumn(name = "computer_id") // Имя колонки во внешнем ключе в БД
    private Computer computer;
}

