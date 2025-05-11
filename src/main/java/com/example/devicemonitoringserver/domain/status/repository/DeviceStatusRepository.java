package com.example.devicemonitoringserver.domain.status.repository;

import com.example.devicemonitoringserver.domain.device.entity.Device;
import com.example.devicemonitoringserver.domain.status.entity.DeviceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface DeviceStatusRepository extends JpaRepository<DeviceStatus, Long> {
    @Query("SELECT ds FROM DeviceStatus ds WHERE ds.device = :device ORDER BY ds.timestamp DESC LIMIT 1")
    Optional<DeviceStatus> findLatestByDevice(@Param("device") Device device);
    @Query("""
    SELECT ds FROM DeviceStatus ds
    WHERE ds.device.id = :deviceId
    ORDER BY ds.timestamp DESC LIMIT 1
""")
    Optional<DeviceStatus> findLatestByDeviceId(@Param("deviceId") Long deviceId);

}
