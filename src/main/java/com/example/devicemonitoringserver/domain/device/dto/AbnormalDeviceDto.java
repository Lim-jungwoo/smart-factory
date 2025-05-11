package com.example.devicemonitoringserver.domain.device.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "이상 설비 및 상태 정보")
public record AbnormalDeviceDto (

    @Schema(description = "설비 ID")
    Long deviceId,

    @Schema(description = "설비 이름")
    String deviceName,

    @Schema(description = "목표 온도")
    double targetTemperature,

    @Schema(description = "목표 습도")
    double targetHumidity,

    @Schema(description = "기준 생산량")
    int baseProductionCount,

    @Schema(description = "현재 온도")
    double currentTemperature,

    @Schema(description = "현재 습도")
    double currentHumidity,

    @Schema(description = "상태 측정 시각")
    LocalDateTime timestamp
) {}
