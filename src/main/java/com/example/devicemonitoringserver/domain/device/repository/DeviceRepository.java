package com.example.devicemonitoringserver.domain.device.repository;

import com.example.devicemonitoringserver.domain.device.entity.Device;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeviceRepository extends JpaRepository<Device, Long> {
    Optional<Device> findByName(String name);
}
