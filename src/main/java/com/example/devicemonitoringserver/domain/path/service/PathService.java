package com.example.devicemonitoringserver.domain.path.service;

import com.example.devicemonitoringserver.domain.path.entity.Path;
import com.example.devicemonitoringserver.domain.path.repository.PathRepository;
import com.example.devicemonitoringserver.domain.storage.entity.Storage;
import com.example.devicemonitoringserver.domain.storage.repository.StorageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class PathService {

    private final PathRepository pathRepository;
    private final StorageRepository storageRepository;

    public Map<Long, Integer> calculateShortestPathsFromFactory() {
        List<Path> paths = pathRepository.findAll();
        List<Storage> storages = storageRepository.findAll();

        // 그래프 구성
        Map<String, List<String>> graph = buildGraph(paths);

        // BFS 수행 (공장은 0,0)
        Map<String, Integer> dist = bfs(graph, "0,0");

        // 결과 구성: storageId -> 거리
        Map<Long, Integer> result = new HashMap<>();
        for (Storage storage : storages) {
            String key = storage.getX() + "," + storage.getY();
            // 경로가 없으면 -1
            result.put(storage.getId(), dist.getOrDefault(key, - 1));
        }

        return result;
    }

    private Map<String, List<String>> buildGraph(List<Path> paths) {
        Map<String, List<String>> graph = new HashMap<>();

        for (Path p : paths) {
            int x1 = p.getStartX(), y1 = p.getStartY();
            int x2 = p.getEndX(), y2 = p.getEndY();

            // 수직
            if (x1 == x2) {
                for (int y = Math.min(y1, y2); y < Math.max(y1, y2); y++) {
                    connect(graph, x1 + "," + y, x1 + "," + (y + 1));
                }
            }
            // 수평
            else if (y1 == y2) {
                for (int x = Math.min(x1, x2); x < Math.max(x1, x2); x++) {
                    connect(graph, x + "," + y1, (x + 1) + "," + y1);
                }
            }
        }

        return graph;
    }

    private void connect(Map<String, List<String>> graph, String a, String b) {
        graph.computeIfAbsent(a, k -> new ArrayList<>()).add(b);
        graph.computeIfAbsent(b, k -> new ArrayList<>()).add(a);
    }

    private Map<String, Integer> bfs(Map<String, List<String>> graph, String start) {
        Map<String, Integer> dist = new HashMap<>();
        Queue<String> queue = new LinkedList<>();
        queue.add(start);
        dist.put(start, 0);

        while (!queue.isEmpty()) {
            String curr = queue.poll();
            int distance = dist.get(curr);

            for (String next : graph.getOrDefault(curr, List.of())) {
                if (!dist.containsKey(next)) {
                    dist.put(next, distance + 1);
                    queue.add(next);
                }
            }
        }

        return dist;
    }

//    public Path createPath()
}
