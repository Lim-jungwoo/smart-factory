package com.example.devicemonitoringserver.domain.path.controller;

import com.example.devicemonitoringserver.annotation.ProtectedApi;
import com.example.devicemonitoringserver.domain.path.entity.Path;
import com.example.devicemonitoringserver.domain.path.service.PathService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/path")
@Tag(name = "Path", description = "공장 - 저장고 물류 경로 API")
public class PathController {
    private final PathService pathService;

    @ProtectedApi(role = "USER")
    @GetMapping("/shortest-paths")
    @Operation(summary = "저장고까지 최단 경로 조회", description = "공장(0,0) 기준으로 각 저장고까지의 최단 거리 정보를 반환합니다.")
    public Map<Long, Integer> getShortestPaths() {
        return pathService.calculateShortestPathsFromFactory();
    }

//    @ProtectedApi(role = "ADMIN")
//    @PostMapping("/paths")
//    @Operation(summary = "길 등록", description = "길(Path)을 등록합니다. 수평 또는 수직이어야 합니다.")
//    public Path createPath(@RequestBody Path path) {
//        return pathService.create
//    }
}
