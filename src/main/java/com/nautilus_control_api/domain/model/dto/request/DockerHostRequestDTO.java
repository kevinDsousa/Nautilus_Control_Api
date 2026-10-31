package com.nautilus_control_api.domain.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class DockerHostRequestDTO {
    @NotBlank
    private String name;

    private String description;

    @NotBlank
    private String dockerHost;

    private boolean tlsEnabled;

    private String caCert;

    private String clientCert;

    private String clientKey;

    private UUID folderId;
}
