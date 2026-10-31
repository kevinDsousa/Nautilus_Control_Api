package com.nautilus_control_api.domain.service;

import com.nautilus_control_api.domain.model.dto.request.HostFolderRequestDTO;
import com.nautilus_control_api.domain.model.dto.response.HostFolderResponseDTO;

import java.util.List;
import java.util.UUID;

public interface HostFolderService {
    List<HostFolderResponseDTO> list();
    HostFolderResponseDTO create(HostFolderRequestDTO request);
    HostFolderResponseDTO update(UUID id, HostFolderRequestDTO request);
    void delete(UUID id);
}
