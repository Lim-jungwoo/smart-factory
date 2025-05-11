package com.example.devicemonitoringserver.domain.device.service;

import com.example.devicemonitoringserver.domain.device.dto.AbnormalDeviceDto;
import com.example.devicemonitoringserver.domain.device.entity.Device;
import com.example.devicemonitoringserver.domain.device.repository.DeviceRepository;
import com.example.devicemonitoringserver.domain.status.entity.DeviceStatus;
import com.example.devicemonitoringserver.domain.status.repository.DeviceStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DeviceService {

    private final DeviceRepository deviceRepository;
    private final DeviceStatusRepository deviceStatusRepository;

    public List<AbnormalDeviceDto> findAbnormalDevices() {
        List<Device> allDevices = deviceRepository.findAll();
        List<AbnormalDeviceDto> abnormalDevices = new ArrayList<>();

        for (Device device : allDevices) {
            Optional<DeviceStatus> latestStatusOpt = deviceStatusRepository.findLatestByDevice(device);
            if (latestStatusOpt.isEmpty()) continue;

            DeviceStatus status = latestStatusOpt.get();

            double temperatureDiff = Math.abs(status.getTemperature() - device.getTargetTemperature());
            double humidityDiff = Math.abs(status.getHumidity() - device.getTargetHumidity());

            if (temperatureDiff >= 10.0 || humidityDiff >= 20.0) {
                abnormalDevices.add(new AbnormalDeviceDto(
                        device.getId(),
                        device.getName(),
                        device.getTargetTemperature(),
                        device.getTargetHumidity(),
                        device.getBaseProductionCount(),
                        status.getTemperature(),
                        status.getHumidity(),
                        status.getTimestamp()
                ));
            }
        }

        return abnormalDevices;
    }

//    public void addDeviceStatus(Long deviceId, DeviceStatusD)
}
