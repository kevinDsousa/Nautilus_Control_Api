package com.nautilus_control_api.domain.controller;

import com.nautilus_control_api.domain.model.dto.request.DockerHostRequestDTO;
import com.nautilus_control_api.domain.model.dto.response.DockerHostResponseDTO;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.UUID;

public interface DockerHostController {
    List<DockerHostResponseDTO> list();
    DockerHostResponseDTO getById(@PathVariable UUID id);
    DockerHostResponseDTO create(@Valid @RequestBody DockerHostRequestDTO request);
    DockerHostResponseDTO update(@PathVariable UUID id, @Valid @RequestBody DockerHostRequestDTO request);
    void delete(@PathVariable UUID id);
    boolean testConnection(@Valid @RequestBody DockerHostRequestDTO request);
    boolean testConnection(@PathVariable UUID id);
}
