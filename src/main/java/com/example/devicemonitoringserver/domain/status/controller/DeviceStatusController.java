package com.example.devicemonitoringserver.domain.status.controller;

import com.example.devicemonitoringserver.annotation.ProtectedApi;
import com.example.devicemonitoringserver.domain.device.dto.DeviceStatusResponseDto;
import com.example.devicemonitoringserver.domain.status.dto.DeviceStatusRequestDto;
import com.example.devicemonitoringserver.domain.status.service.DeviceStatusService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/device-status")
public class DeviceStatusController {

    private final DeviceStatusService deviceStatusService;

    @ProtectedApi(role = "ADMIN")
    @Operation(summary = "디바이스 상태 추가")
    @PostMapping
    public ResponseEntity<DeviceStatusResponseDto> addStatus(@RequestBody DeviceStatusRequestDto request) {
        DeviceStatusResponseDto response = deviceStatusService.saveStatus(request);

        URI location = URI.create("/api/device-status/" + response.deviceId());
        return ResponseEntity
                .created(location) // HTTP 201 + Location 헤더
                .body(response);
    }

    @ProtectedApi(role = "USER")
    @GetMapping("/latest/{deviceId}")
    @Operation(summary = "디바이스의 최신 상태 조회")
    public ResponseEntity<DeviceStatusResponseDto> getLatestStatus(@PathVariable("deviceId") Long deviceId) {
        return ResponseEntity.ok(deviceStatusService.getLatestStatus(deviceId));
    }

    @ProtectedApi(role = "USER")
    @GetMapping("/{statusId}")
    @Operation(summary = "상태 ID로 DeviceStatus 조회")
    public ResponseEntity<DeviceStatusResponseDto> getStatusById(@PathVariable("statusId") Long statusId) {
        return ResponseEntity.ok(deviceStatusService.getStatusById(statusId));
    }
}
