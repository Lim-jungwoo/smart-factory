package com.example.devicemonitoringserver;

import com.example.devicemonitoringserver.domain.path.entity.Path;
import com.example.devicemonitoringserver.domain.path.repository.PathRepository;
import com.example.devicemonitoringserver.domain.path.service.PathService;
import com.example.devicemonitoringserver.domain.storage.entity.Storage;
import com.example.devicemonitoringserver.domain.storage.repository.StorageRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;

@SpringBootTest
@Transactional
public class PathServiceTests {

    @Autowired
    private PathService pathService;

    @Autowired
    private PathRepository pathRepository;

    @Autowired
    private StorageRepository storageRepository;

    @Test
    @DisplayName("공장에서 저장고까지 최단 경로 계산")
    void testShortestPath() {
        // given
//        pathRepository.save(Path.builder().startX(0).startY(0).endX(3).endY(0).build()); // (0,0)-(3,0)
//        pathRepository.save(Path.builder().startX(3).startY(0).endX(3).endY(2).build()); // (3,0)-(3,2)
        pathRepository.save(Path.builder().startX(0).startY(0).endX(5).endY(0).build()); // (0,0)-(5,0)
        pathRepository.save(Path.builder().startX(1).startY(0).endX(1).endY(2).build()); // (1,0)-(1,2)
        pathRepository.save(Path.builder().startX(4).startY(0).endX(4).endY(2).build()); // (4,0)-(4,2)
        pathRepository.save(Path.builder().startX(1).startY(2).endX(2).endY(2).build()); // (1,2)-(2,2)
        pathRepository.save(Path.builder().startX(3).startY(2).endX(4).endY(2).build()); // (3,2)-(4,2)
        Storage storage = storageRepository.save(Storage.builder().x(3).y(2).name("S1").build());

        // when
        Map<Long, Integer> result = pathService.calculateShortestPathsFromFactory();

        // then
        assertThat(result).containsKey(storage.getId());
        assertThat(result.get(storage.getId())).isEqualTo(7);
    }
}
