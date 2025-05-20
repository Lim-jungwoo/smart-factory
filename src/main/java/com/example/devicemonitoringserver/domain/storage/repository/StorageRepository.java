package com.example.devicemonitoringserver.domain.storage.repository;

import com.example.devicemonitoringserver.domain.storage.entity.Storage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StorageRepository extends JpaRepository<Storage, Long> {
}
