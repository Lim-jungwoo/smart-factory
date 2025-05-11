package com.example.devicemonitoringserver.domain.device.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Device {
    @Id @GeneratedValue
    private Long id;

    private String name;
    private double targetTemperature;
    private double targetHumidity;
    private int baseProductionCount;
}
