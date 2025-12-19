package com.nikkin.devicesdb.Entities;

public class SolidStateDrive {
    int Id;
    String Name;
    String Interface;       // SATA...
    String NandType;        // SLC, MLC, TLC...
    int TbwTB;
    float WriteSpeed;       // Kb/s
    float ReadSpeed;
    float CapacityKB;
    float PowerConsumption;
}
