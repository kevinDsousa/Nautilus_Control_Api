package com.nautilus_control_api.domain.model.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class HostFolderResponseDTO {
    private UUID id;
    private String name;
    private UUID parentId;
}
