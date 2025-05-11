package com.example.devicemonitoringserver.simulation.service;

import com.example.devicemonitoringserver.domain.device.entity.Device;
import com.example.devicemonitoringserver.domain.device.repository.DeviceRepository;
import com.example.devicemonitoringserver.domain.part.entity.Part;
import com.example.devicemonitoringserver.domain.part.repository.PartRepository;
import com.example.devicemonitoringserver.domain.product.entity.Product;
import com.example.devicemonitoringserver.domain.product.entity.ProductPart;
import com.example.devicemonitoringserver.domain.product.repository.ProductPartRepository;
import com.example.devicemonitoringserver.domain.product.repository.ProductRepository;
import com.example.devicemonitoringserver.domain.status.entity.DeviceStatus;
import com.example.devicemonitoringserver.domain.status.repository.DeviceStatusRepository;
import com.example.devicemonitoringserver.simulation.dto.ImproveResponseDto;
import com.example.devicemonitoringserver.simulation.dto.SimulationRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SimulationService {

    private final DeviceRepository deviceRepository;
    private final DeviceStatusRepository deviceStatusRepository;
    private final PartRepository partRepository;
    private final ProductRepository productRepository;
    private final ProductPartRepository productPartRepository;

    public ImproveResponseDto improve(SimulationRequestDto request) {
        long startTime = System.currentTimeMillis();

        Long targetDeviceId = request.deviceId();
        double improvedTemp = request.improvedTemperature();
        double improvedHumidity = request.improvedHumidity();

        Device device = deviceRepository.findById(targetDeviceId)
                .orElseThrow(() -> new RuntimeException("Device not found"));
        DeviceStatus beforeStatus = deviceStatusRepository.findLatestByDevice(device)
                .orElseThrow(() -> new RuntimeException("No status for device"));

        // 개선 전 부품 생산량 (해당 device 기준)
        int beforePartCount = calculateProduction(device, beforeStatus);

        // 개선 후 부품 생산량 (해당 device 기준)
        DeviceStatus improvedStatus = DeviceStatus.create(device, improvedTemp, improvedHumidity);
        int afterPartCount = calculateProduction(device, improvedStatus);

        // 개선 전 전체 상품 생산량 (원래 시뮬레이션 상태 기준)
        SimulationRequestDto beforeRequest = new SimulationRequestDto(
                targetDeviceId, beforeStatus.getTemperature(), beforeStatus.getHumidity()
        );
        Map<Product, Integer> beforeProductCounts = simulateProductCountsWithImprovedDevice(beforeRequest);

        // 개선 후 전체 상품 생산량 (개선된 온습도 반영)
        Map<Product, Integer> afterProductCounts = simulateProductCountsWithImprovedDevice(request);

        int beforeProductTotal = beforeProductCounts.values().stream().mapToInt(Integer::intValue).sum();
        int afterProductTotal = afterProductCounts.values().stream().mapToInt(Integer::intValue).sum();

        double improvementRate = 0.0;
        if (beforeProductTotal > 0) {
            improvementRate = ((afterProductTotal - beforeProductTotal) / (double) beforeProductTotal) * 100.0;
        }

        boolean improved = afterProductTotal > beforeProductTotal;
        long processingTimeMs = System.currentTimeMillis() - startTime;

        return new ImproveResponseDto(
                beforePartCount,
                afterPartCount,
                beforeProductTotal,
                afterProductTotal,
                beforeStatus.getTemperature(),
                improvedTemp,
                beforeStatus.getHumidity(),
                improvedHumidity,
                improvementRate,
                device.getName(),
                improved,
                processingTimeMs
        );
    }


    public Map<Product, Integer> simulateProductCountsWithImprovedDevice(SimulationRequestDto request) {
        Long improvedDeviceId = request.deviceId();
        double improvedTemp = request.improvedTemperature();
        double improvedHumidity = request.improvedHumidity();

        List<Device> devices = deviceRepository.findAll();
        Map<Device, DeviceStatus> statusMap = new HashMap<>();

        for (Device device : devices) {
            if (device.getId().equals(improvedDeviceId)) {
                statusMap.put(device, DeviceStatus.create(device, improvedTemp, improvedHumidity));
            } else {
                DeviceStatus originalStatus = deviceStatusRepository.findLatestByDevice(device)
                        .orElseThrow(() -> new RuntimeException("No status for device: " + device.getId()));
                statusMap.put(device, originalStatus);
            }
        }

        Map<Long, Integer> partCounts = simulatePartCounts(statusMap);
        return calculateProductCounts(partCounts);
    }


    private Map<Long, Integer> simulatePartCounts(Map<Device, DeviceStatus> statusMap) {
        Map<Long, Integer> partCounts = new HashMap<>();

        for (Map.Entry<Device, DeviceStatus> entry : statusMap.entrySet()) {
            Device device = entry.getKey();
            DeviceStatus status = entry.getValue();

            int productionCount = calculateProduction(device, status);

            Part part = partRepository.findAllByDevice(device).stream().findFirst()
                    .orElseThrow(() -> new RuntimeException("No part for device: " + device.getId()));

            partCounts.merge(part.getId(), productionCount, Integer::sum);
        }

        return partCounts;
    }

    private Map<Product, Integer> calculateProductCounts(Map<Long, Integer> partCounts) {
        List<Product> products = productRepository.findAll();
        Map<Product, Integer> result = new HashMap<>();

        for (Product product : products) {
            List<ProductPart> requiredParts = productPartRepository.findAllByProduct(product);
            int maxCount = requiredParts.stream()
                    .mapToInt(pp -> partCounts.getOrDefault(pp.getPart().getId(), 0) / pp.getQuantity())
                    .min()
                    .orElse(0);
            result.put(product, maxCount);
        }

        return result;
    }

    private int calculateProduction(Device device, DeviceStatus deviceStatus) {
        double temperatureDiff = Math.abs(deviceStatus.getTemperature() - device.getTargetTemperature());
        double humidityDiff = Math.abs(deviceStatus.getHumidity()) - device.getTargetHumidity();

        if (temperatureDiff >= 10.0 || humidityDiff >= 20.0) {
            return 0;
        }

        double temperaturePenalty = temperatureDiff / 10.0;
        double humidityPenalty = humidityDiff / 20.0;

        double totalPenalty = temperaturePenalty + humidityPenalty;
        totalPenalty = Math.min(totalPenalty, 1.0);

        return (int) (device.getBaseProductionCount() * (1 - totalPenalty));
    }
}
