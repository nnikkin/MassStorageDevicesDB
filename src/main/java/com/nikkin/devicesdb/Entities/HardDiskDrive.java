package com.nikkin.devicesdb.Entities;

public class HardDiskDrive {
    int Id;
    String Name;
    String Interface;       // SATA...
    float CapacityKB;
    float Format;           // 3.5", 2.5"
    int Rpm;                // 5400, 7200...
    int Cache;
    float PowerConsumption;
}
