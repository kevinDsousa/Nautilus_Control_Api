package com.nautilus_control_api.domain.controller.impl;

import com.nautilus_control_api.domain.controller.HostFolderController;
import com.nautilus_control_api.domain.model.dto.request.HostFolderRequestDTO;
import com.nautilus_control_api.domain.model.dto.response.HostFolderResponseDTO;
import com.nautilus_control_api.domain.service.HostFolderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/host-folders")
public class HostFolderControllerImpl implements HostFolderController {
    private final HostFolderService hostFolderService;

    @Override
    @GetMapping
    public List<HostFolderResponseDTO> list() {
        return hostFolderService.list();
    }

    @Override
    @PostMapping
    public HostFolderResponseDTO create(@Valid @RequestBody HostFolderRequestDTO request) {
        return hostFolderService.create(request);
    }

    @Override
    @PutMapping("/{id}")
    public HostFolderResponseDTO update(@PathVariable UUID id, @Valid @RequestBody HostFolderRequestDTO request) {
        return hostFolderService.update(id, request);
    }

    @Override
    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        hostFolderService.delete(id);
    }
}
