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
    public HostFolderResponseDTO move(UUID id, UUID parentId) {
        HostFolder folder = findOrThrow(id);

        if (parentId != null) {
            if (parentId.equals(id)) {
                throw new IllegalArgumentException("Uma pasta nao pode ser movida para dentro dela mesma");
            }
            if (isDescendant(parentId, id)) {
                throw new IllegalArgumentException("Nao e possivel mover uma pasta para dentro de uma de suas subpastas");
            }
        }

        folder.setParentId(parentId);
        return toResponse(hostFolderRepository.save(folder));
    }

    private boolean isDescendant(UUID candidateId, UUID ancestorId) {
        UUID current = candidateId;
        while (current != null) {
            if (current.equals(ancestorId)) {
                return true;
            }
            current = hostFolderRepository.findById(current).map(HostFolder::getParentId).orElse(null);
        }
        return false;
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
