package com.nautilus_control_api.domain.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class HostFolderRequestDTO {
    @NotBlank
    private String name;

    private UUID parentId;
}
