package com.nikkin.devicesdb.Entities;

import jakarta.persistence.*;

import java.util.Collection;
import java.util.LinkedHashSet;

@Entity
public class Computer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String name;

    @OneToMany(mappedBy = "computer", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE, orphanRemoval = true)
    Collection<HardDiskDrive> linkedHdds = new LinkedHashSet<>();

    @OneToMany(mappedBy = "computer", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE, orphanRemoval = true)
    Collection<SolidStateDrive> linkedSsds = new LinkedHashSet<>();

    @OneToMany(mappedBy = "computer", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE, orphanRemoval = true)
    Collection<RandomAccessMemory> linkedRams = new LinkedHashSet<>();

    @OneToMany(mappedBy = "computer", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE, orphanRemoval = true)
    Collection<FlashDrive> linkedFlashDrives = new LinkedHashSet<>();

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

    public Collection<HardDiskDrive> getLinkedHdds() {
        return linkedHdds;
    }

    public void setLinkedHdds(Collection<HardDiskDrive> linkedHdds) {
        this.linkedHdds = linkedHdds;
    }

    public Collection<SolidStateDrive> getLinkedSsds() {
        return linkedSsds;
    }

    public void setLinkedSsds(Collection<SolidStateDrive> linkedSsds) {
        this.linkedSsds = linkedSsds;
    }

    public Collection<RandomAccessMemory> getLinkedRams() {
        return linkedRams;
    }

    public void setLinkedRams(Collection<RandomAccessMemory> linkedRams) {
        this.linkedRams = linkedRams;
    }

    public Collection<FlashDrive> getLinkedFlashDrives() {
        return linkedFlashDrives;
    }

    public void setLinkedFlashDrives(Collection<FlashDrive> linkedFlashDrives) {
        this.linkedFlashDrives = linkedFlashDrives;
    }
}
