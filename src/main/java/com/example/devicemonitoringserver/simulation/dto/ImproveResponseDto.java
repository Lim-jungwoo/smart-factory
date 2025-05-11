package com.example.devicemonitoringserver.simulation.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record ImproveResponseDto(

        @Schema(description = "개선 전 부품 생산량")
        int beforePartCount,

        @Schema(description = "개선 후 부품 생산량")
        int afterPartCount,

        @Schema(description = "개선 전 상품 생산량")
        int beforeProductCount,

        @Schema(description = "개선 후 상품 생산량")
        int afterProductCount,

        @Schema(description = "개선 전 온도")
        double beforeTemperature,

        @Schema(description = "개선 후 온도")
        double afterTemperature,

        @Schema(description = "개선 전 습도")
        double beforeHumidity,

        @Schema(description = "개선 후 습도")
        double afterHumidity,

        @Schema(description = "상품 생산량 향상률 (%)")
        double productImprovementRate,

        @Schema(description = "디바이스 이름")
        String deviceName,

        @Schema(description = "개선 적용 성공 여부")
        boolean improved,

        @Schema(description = "API 처리 시간(ms)")
        long processingTimeMs

) {}
