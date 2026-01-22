package com.nikkin.devicesdb.Views;

import com.nikkin.devicesdb.Bytes;

public class BytesConverter {
    public static float convert(float value, Bytes from, Bytes to) {
        if (value < 0) throw new IllegalArgumentException("Значение < 0");
        if (from == to) return value;

        double bits = value * from.getBits();
        return (float) (bits / to.getBits());
    }
}
