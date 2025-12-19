package com.nikkin.devicesdb.Entities;

public class RandomAccessMemory {
    int Id;
    String Model;
    String Manufacturer;
    String Type;            // DDR3, DDR4, DDR5...
    String ModuleType;      // DIMM/SO-DIMM
    float CapacityKB;
    float FrequencyMhz;
    float CasLatency;
    boolean SupportsEcc;
}
