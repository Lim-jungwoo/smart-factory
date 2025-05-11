package com.example.devicemonitoringserver.simulation.controller;

import com.example.devicemonitoringserver.annotation.ProtectedApi;
import com.example.devicemonitoringserver.domain.device.entity.Device;
import com.example.devicemonitoringserver.domain.device.repository.DeviceRepository;
import com.example.devicemonitoringserver.domain.product.entity.Product;
import com.example.devicemonitoringserver.simulation.dto.ImproveResponseDto;
import com.example.devicemonitoringserver.simulation.dto.SimulationRequestDto;
import com.example.devicemonitoringserver.simulation.service.SimulationService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;
import java.util.stream.Collectors;

@ProtectedApi(role = "ADMIN")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/simulation")
public class SimulationController {

    private final SimulationService simulationService;

    @Operation(summary = "디바이스 성능 개선 시뮬레이션", description = "개선된 온도/습도 조건으로 생산량 변화 시뮬레이션")
    @PostMapping("/improve")
    public ResponseEntity<ImproveResponseDto> simulateImprovement(
            @RequestBody SimulationRequestDto requestDto
    ) {
        ImproveResponseDto response = simulationService.improve(requestDto);
        return ResponseEntity.ok(response);
    }
}
