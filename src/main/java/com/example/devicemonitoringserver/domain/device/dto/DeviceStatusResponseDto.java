package com.example.devicemonitoringserver.domain.device.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "디바이스 상태 저장 응답 DTO")
public record DeviceStatusResponseDto(

        @Schema(description = "디바이스 ID", example = "1")
        Long deviceId,

        @Schema(description = "디바이스 이름", example = "Engine Factory")
        String deviceName,

        @Schema(description = "저장된 온도 (°C)", example = "25.0")
        double temperature,

        @Schema(description = "저장된 습도 (%)", example = "50.0")
        double humidity

) {}
