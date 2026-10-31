package com.nautilus_control_api.domain.controller.impl;

import com.nautilus_control_api.domain.controller.DockerHostController;
import com.nautilus_control_api.domain.model.dto.request.DockerHostRequestDTO;
import com.nautilus_control_api.domain.model.dto.response.DockerHostResponseDTO;
import com.nautilus_control_api.domain.service.DockerHostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/docker/hosts")
public class DockerHostControllerImpl implements DockerHostController {
    private final DockerHostService dockerHostService;

    @Override
    @GetMapping
    public List<DockerHostResponseDTO> list() {
        return dockerHostService.list();
    }

    @Override
    @GetMapping("/{id}")
    public DockerHostResponseDTO getById(@PathVariable UUID id) {
        return dockerHostService.getById(id);
    }

    @Override
    @PostMapping
    public DockerHostResponseDTO create(@Valid @RequestBody DockerHostRequestDTO request) {
        return dockerHostService.create(request);
    }

    @Override
    @PutMapping("/{id}")
    public DockerHostResponseDTO update(@PathVariable UUID id, @Valid @RequestBody DockerHostRequestDTO request) {
        return dockerHostService.update(id, request);
    }

    @Override
    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        dockerHostService.delete(id);
    }

    @Override
    @PostMapping("/test")
    public boolean testConnection(@Valid @RequestBody DockerHostRequestDTO request) {
        return dockerHostService.testConnection(request);
    }

    @Override
    @PostMapping("/{id}/test")
    public boolean testConnection(@PathVariable UUID id) {
        return dockerHostService.testConnection(id);
    }
}
