package com.nikkin.devicesdb.Entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Computer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String name;

    @OneToMany(mappedBy = "computer", cascade = CascadeType.ALL, orphanRemoval = true)
    List<HardDiskDrive> linkedHdds;

    @OneToMany(mappedBy = "computer", cascade = CascadeType.ALL, orphanRemoval = true)
    List<SolidStateDrive> linkedSsds;

    @OneToMany(mappedBy = "computer", cascade = CascadeType.ALL, orphanRemoval = true)
    List<RandomAccessMemory> linkedRams;

    @OneToMany(mappedBy = "computer", cascade = CascadeType.ALL, orphanRemoval = true)
    List<FlashDrive> linkedFlashDrives;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<HardDiskDrive> getLinkedHdds() {
        return linkedHdds;
    }

    public void setLinkedHdds(List<HardDiskDrive> linkedHdds) {
        this.linkedHdds = linkedHdds;
    }

    public List<SolidStateDrive> getLinkedSsds() {
        return linkedSsds;
    }

    public void setLinkedSsds(List<SolidStateDrive> linkedSsds) {
        this.linkedSsds = linkedSsds;
    }

    public List<RandomAccessMemory> getLinkedRams() {
        return linkedRams;
    }

    public void setLinkedRams(List<RandomAccessMemory> linkedRams) {
        this.linkedRams = linkedRams;
    }

    public List<FlashDrive> getLinkedFlashDrives() {
        return linkedFlashDrives;
    }

    public void setLinkedFlashDrives(List<FlashDrive> linkedFlashDrives) {
        this.linkedFlashDrives = linkedFlashDrives;
    }
}
