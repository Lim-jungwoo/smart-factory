package com.example.devicemonitoringserver.domain.status.entity;

import com.example.devicemonitoringserver.domain.device.entity.Device;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class DeviceStatus {
    @Id
    @GeneratedValue
//    @SequenceGenerator(name = "device_status_seq_gen", sequenceName = "device_status_id_seq", allocationSize = 1)
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "device_status_seq_gen")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Device device;

    private double temperature;
    private double humidity;
    private LocalDateTime timestamp;

    public static DeviceStatus create(Device device, double temperature, double humidity) {
        DeviceStatus status = new DeviceStatus();
        status.device = device;
        status.temperature = temperature;
        status.humidity = humidity;
        status.timestamp = LocalDateTime.now();
        return status;
    }
}
