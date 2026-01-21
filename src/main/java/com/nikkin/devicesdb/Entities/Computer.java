package com.nikkin.devicesdb.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Collection;
import java.util.LinkedHashSet;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class Computer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @OneToMany(mappedBy = "computer", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE, orphanRemoval = true)
    Collection<HardDiskDrive> linkedHdds = new LinkedHashSet<>();

    @OneToMany(mappedBy = "computer", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE, orphanRemoval = true)
    Collection<SolidStateDrive> linkedSsds = new LinkedHashSet<>();

    @OneToMany(mappedBy = "computer", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE, orphanRemoval = true)
    Collection<RandomAccessMemory> linkedRams = new LinkedHashSet<>();

    @OneToMany(mappedBy = "computer", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE, orphanRemoval = true)
    Collection<FlashDrive> linkedFlashDrives = new LinkedHashSet<>();
}
