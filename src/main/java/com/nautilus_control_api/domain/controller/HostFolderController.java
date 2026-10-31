package com.nautilus_control_api.domain.controller;

import com.nautilus_control_api.domain.model.dto.request.HostFolderMoveRequestDTO;
import com.nautilus_control_api.domain.model.dto.request.HostFolderRequestDTO;
import com.nautilus_control_api.domain.model.dto.response.HostFolderResponseDTO;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.UUID;

public interface HostFolderController {
    List<HostFolderResponseDTO> list();
    HostFolderResponseDTO create(@Valid @RequestBody HostFolderRequestDTO request);
    HostFolderResponseDTO update(@PathVariable UUID id, @Valid @RequestBody HostFolderRequestDTO request);
    HostFolderResponseDTO move(@PathVariable UUID id, @RequestBody HostFolderMoveRequestDTO request);
    void delete(@PathVariable UUID id);
}
