package com.example.devicemonitoringserver.domain.path.repository;

import com.example.devicemonitoringserver.domain.path.entity.Path;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PathRepository extends JpaRepository<Path, Long> {
}
