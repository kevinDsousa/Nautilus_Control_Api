package com.nautilus_control_api.domain.model.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class DockerHostResponseDTO {
    private UUID id;
    private String name;
    private String description;
    private String dockerHost;
    private boolean tlsEnabled;
    private boolean active;
    private UUID folderId;
    private Boolean reachable;
}
