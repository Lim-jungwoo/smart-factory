package com.example.devicemonitoringserver.domain.status.dto;

public record DeviceStatusRequestDto(
        Long deviceId,
        double temperature,
        double humidity
) {}
