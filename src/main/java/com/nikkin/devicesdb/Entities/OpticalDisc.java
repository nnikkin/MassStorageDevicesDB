package com.nikkin.devicesdb.Entities;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Entity
public class OpticalDisc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max=30)
    private String name;

    @Nullable
    @Size(max=10)
    private String type;            // CD, DVD...

    @Positive(message = "Объём не может быть отрицательным либо равным нулю")
    private Float capacity;

    @Nullable
    @Positive(message = "Множитель скорости не может быть отрицательным либо равным нулю")
    private Integer speedMultiplier;    // 2x, 4x...

    @Size(max=4)
    private String rewriteType;     // R, RW...

    @Nullable
    @Positive(message = "Количество слоёв не может быть отрицательным либо равным нулю")
    private Integer layers;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Float getCapacity() {
        return capacity;
    }

    public void setCapacity(Float capacity) {
        this.capacity = capacity;
    }

    public Integer getSpeedMultiplier() {
        return speedMultiplier;
    }

    public void setSpeedMultiplier(Integer speedMultiplier) {
        this.speedMultiplier = speedMultiplier;
    }

    public String getRewriteType() {
        return rewriteType;
    }

    public void setRewriteType(String rewriteType) {
        this.rewriteType = rewriteType;
    }

    public Integer getLayers() {
        return layers;
    }

    public void setLayers(Integer layers) {
        this.layers = layers;
    }
}
