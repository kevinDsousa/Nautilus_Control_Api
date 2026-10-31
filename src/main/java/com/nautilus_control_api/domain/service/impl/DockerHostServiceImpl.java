package com.nautilus_control_api.domain.service.impl;

import com.nautilus_control_api.domain.model.dto.request.DockerHostRequestDTO;
import com.nautilus_control_api.domain.model.dto.response.DockerHostResponseDTO;
import com.nautilus_control_api.domain.model.entity.DockerHost;
import com.nautilus_control_api.domain.repository.DockerHostRepository;
import com.nautilus_control_api.domain.service.DockerHostService;
import com.nautilus_control_api.infra.config.DockerClientRegistry;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DockerHostServiceImpl implements DockerHostService {
    private final DockerHostRepository dockerHostRepository;
    private final DockerClientRegistry dockerClientRegistry;

    @Override
    public List<DockerHostResponseDTO> list() {
        return dockerHostRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public DockerHostResponseDTO getById(UUID id) {
        return toResponse(findOrThrow(id));
    }

    @Override
    public DockerHostResponseDTO create(DockerHostRequestDTO request) {
        LocalDateTime now = LocalDateTime.now();
        DockerHost host = DockerHost.builder()
                .name(request.getName())
                .description(request.getDescription())
                .dockerHost(request.getDockerHost())
                .tlsEnabled(request.isTlsEnabled())
                .caCert(request.getCaCert())
                .clientCert(request.getClientCert())
                .clientKey(request.getClientKey())
                .folderId(request.getFolderId())
                .active(true)
                .createdAt(now)
                .updatedAt(now)
                .build();

        return toResponse(dockerHostRepository.save(host));
    }

    @Override
    public DockerHostResponseDTO update(UUID id, DockerHostRequestDTO request) {
        DockerHost host = findOrThrow(id);

        host.setName(request.getName());
        host.setDescription(request.getDescription());
        host.setDockerHost(request.getDockerHost());
        host.setTlsEnabled(request.isTlsEnabled());
        host.setCaCert(request.getCaCert());
        host.setClientCert(request.getClientCert());
        host.setClientKey(request.getClientKey());
        host.setFolderId(request.getFolderId());
        host.setUpdatedAt(LocalDateTime.now());

        DockerHost saved = dockerHostRepository.save(host);
        dockerClientRegistry.invalidate(id);
        return toResponse(saved);
    }

    @Override
    public void delete(UUID id) {
        findOrThrow(id);
        dockerHostRepository.deleteById(id);
        dockerClientRegistry.invalidate(id);
    }

    @Override
    public boolean testConnection(DockerHostRequestDTO request) {
        return dockerClientRegistry.testConnection(
                request.getDockerHost(),
                request.isTlsEnabled(),
                request.getCaCert(),
                request.getClientCert(),
                request.getClientKey());
    }

    @Override
    public boolean testConnection(UUID id) {
        DockerHost host = findOrThrow(id);
        return dockerClientRegistry.testConnection(
                host.getDockerHost(),
                host.isTlsEnabled(),
                host.getCaCert(),
                host.getClientCert(),
                host.getClientKey());
    }

    private DockerHost findOrThrow(UUID id) {
        return dockerHostRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Host Docker nao encontrado: " + id));
    }

    private DockerHostResponseDTO toResponse(DockerHost host) {
        return DockerHostResponseDTO.builder()
                .id(host.getId())
                .name(host.getName())
                .description(host.getDescription())
                .dockerHost(host.getDockerHost())
                .tlsEnabled(host.isTlsEnabled())
                .active(host.isActive())
                .folderId(host.getFolderId())
                .build();
    }
}
