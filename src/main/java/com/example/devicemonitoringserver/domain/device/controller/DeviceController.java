package com.example.devicemonitoringserver.domain.device.controller;

import com.example.devicemonitoringserver.annotation.ProtectedApi;
import com.example.devicemonitoringserver.domain.device.dto.AbnormalDeviceDto;
import com.example.devicemonitoringserver.domain.device.entity.Device;
import com.example.devicemonitoringserver.domain.device.service.DeviceService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@ProtectedApi(role = "USER")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/devices")
public class DeviceController {

    private final DeviceService deviceService;

    @Operation(summary = "이상 디바이스 확인")
    @GetMapping("/abnormal")
    public ResponseEntity<List<AbnormalDeviceDto>> getAbnormalDevices() {
        return ResponseEntity.ok(deviceService.findAbnormalDevices());
    }
}
