package com.nautilus_control_api.domain.repository;

import com.nautilus_control_api.domain.model.entity.HostFolder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface HostFolderRepository extends JpaRepository<HostFolder, UUID> {
    List<HostFolder> findByParentId(UUID parentId);
}
