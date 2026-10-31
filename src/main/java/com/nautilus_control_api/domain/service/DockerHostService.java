package com.nautilus_control_api.domain.service;

import com.nautilus_control_api.domain.model.dto.request.DockerHostRequestDTO;
import com.nautilus_control_api.domain.model.dto.response.DockerHostResponseDTO;

import java.util.List;
import java.util.UUID;

public interface DockerHostService {
    List<DockerHostResponseDTO> list();
    DockerHostResponseDTO getById(UUID id);
    DockerHostResponseDTO create(DockerHostRequestDTO request);
    DockerHostResponseDTO update(UUID id, DockerHostRequestDTO request);
    void delete(UUID id);
    boolean testConnection(DockerHostRequestDTO request);
    boolean testConnection(UUID id);
}
