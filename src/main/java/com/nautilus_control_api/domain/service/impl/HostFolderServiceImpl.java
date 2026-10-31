package com.nautilus_control_api.domain.service.impl;

import com.nautilus_control_api.domain.model.dto.request.HostFolderRequestDTO;
import com.nautilus_control_api.domain.model.dto.response.HostFolderResponseDTO;
import com.nautilus_control_api.domain.model.entity.DockerHost;
import com.nautilus_control_api.domain.model.entity.HostFolder;
import com.nautilus_control_api.domain.repository.DockerHostRepository;
import com.nautilus_control_api.domain.repository.HostFolderRepository;
import com.nautilus_control_api.domain.service.HostFolderService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HostFolderServiceImpl implements HostFolderService {
    private final HostFolderRepository hostFolderRepository;
    private final DockerHostRepository dockerHostRepository;

    @Override
    public List<HostFolderResponseDTO> list() {
        return hostFolderRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public HostFolderResponseDTO create(HostFolderRequestDTO request) {
        HostFolder folder = HostFolder.builder()
                .name(request.getName())
                .parentId(request.getParentId())
                .createdAt(LocalDateTime.now())
                .build();

        return toResponse(hostFolderRepository.save(folder));
    }

    @Override
    public HostFolderResponseDTO update(UUID id, HostFolderRequestDTO request) {
        HostFolder folder = findOrThrow(id);
        folder.setName(request.getName());
        folder.setParentId(request.getParentId());
        return toResponse(hostFolderRepository.save(folder));
    }

    @Override
    public void delete(UUID id) {
        HostFolder folder = findOrThrow(id);
        UUID newParentId = folder.getParentId();

        hostFolderRepository.findByParentId(id).forEach(child -> {
            child.setParentId(newParentId);
            hostFolderRepository.save(child);
        });

        dockerHostRepository.findByFolderId(id).forEach(host -> {
            host.setFolderId(newParentId);
            dockerHostRepository.save(host);
        });

        hostFolderRepository.deleteById(id);
    }

    private HostFolder findOrThrow(UUID id) {
        return hostFolderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pasta de hosts nao encontrada: " + id));
    }

    private HostFolderResponseDTO toResponse(HostFolder folder) {
        return HostFolderResponseDTO.builder()
                .id(folder.getId())
                .name(folder.getName())
                .parentId(folder.getParentId())
                .build();
    }
}
