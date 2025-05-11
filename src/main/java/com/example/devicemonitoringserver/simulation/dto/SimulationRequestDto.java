package com.example.devicemonitoringserver.simulation.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "설비 개선 시뮬레이션 요청")
public record SimulationRequestDto (
        @Schema(description = "개선 대상 설비 ID")
        Long deviceId,

        @Schema(description = "개선된 온도")
        double improvedTemperature,

        @Schema(description = "개선된 습도")
        double improvedHumidity
) {}
