package com.example.devicemonitoringserver.domain.status.service;

import com.example.devicemonitoringserver.domain.device.dto.DeviceStatusResponseDto;
import com.example.devicemonitoringserver.domain.device.entity.Device;
import com.example.devicemonitoringserver.domain.device.repository.DeviceRepository;
import com.example.devicemonitoringserver.domain.status.dto.DeviceStatusRequestDto;
import com.example.devicemonitoringserver.domain.status.entity.DeviceStatus;
import com.example.devicemonitoringserver.domain.status.repository.DeviceStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeviceStatusService {

    private final DeviceRepository deviceRepository;
    private final DeviceStatusRepository deviceStatusRepository;

    public DeviceStatusResponseDto saveStatus(DeviceStatusRequestDto dto) {
        Device device = deviceRepository.findById(dto.deviceId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid device ID: " + dto.deviceId()));

        DeviceStatus status = DeviceStatus.create(
                device,
                dto.temperature(),
                dto.humidity()
        );

        deviceStatusRepository.save(status);

        return new DeviceStatusResponseDto(
                device.getId(),
                device.getName(),
                status.getTemperature(),
                status.getHumidity()
        );
    }

    public DeviceStatusResponseDto getLatestStatus(Long deviceId) {
        DeviceStatus status = deviceStatusRepository.findLatestByDeviceId(deviceId)
                .orElseThrow(() -> new RuntimeException("해당 디바이스의 상태 정보가 없습니다."));

        Device device = status.getDevice();
        return new DeviceStatusResponseDto(
                device.getId(),
                device.getName(),
                status.getTemperature(),
                status.getHumidity()
        );
    }

    public DeviceStatusResponseDto getStatusById(Long statusId) {
        DeviceStatus status = deviceStatusRepository.findById(statusId)
                .orElseThrow(() -> new RuntimeException("해당 상태 ID가 존재하지 않습니다."));

        Device device = status.getDevice();
        return new DeviceStatusResponseDto(
                device.getId(),
                device.getName(),
                status.getTemperature(),
                status.getHumidity()
        );
    }
}
