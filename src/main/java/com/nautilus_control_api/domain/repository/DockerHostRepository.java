package com.nautilus_control_api.domain.repository;

import com.nautilus_control_api.domain.model.entity.DockerHost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DockerHostRepository extends JpaRepository<DockerHost, UUID> {
    List<DockerHost> findByFolderId(UUID folderId);
}
