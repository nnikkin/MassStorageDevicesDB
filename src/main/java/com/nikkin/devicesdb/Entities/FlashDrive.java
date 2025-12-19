package com.nikkin.devicesdb.Entities;

public class FlashDrive {
    int Id;
    String Name;
    String Interface;       // Type-A, Type-C...
    String UsbType;         // 1.0, 2.0, 3.0...
    float CapacityKB;
    float WriteSpeed;       // Kb/s
    float ReadSpeed;
}
