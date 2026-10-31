package com.nautilus_control_api.domain.model.entity;

import com.nautilus_control_api.infra.persistence.converter.EncryptedStringConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "docker_host")
public class DockerHost {
    @Id
    @GeneratedValue
    private UUID id;

    private String name;

    private String description;

    @Column(name = "docker_host")
    private String dockerHost;

    @Column(name = "tls_enabled")
    private boolean tlsEnabled;

    @Lob
    @Convert(converter = EncryptedStringConverter.class)
    @Column(name = "ca_cert")
    private String caCert;

    @Lob
    @Convert(converter = EncryptedStringConverter.class)
    @Column(name = "client_cert")
    private String clientCert;

    @Lob
    @Convert(converter = EncryptedStringConverter.class)
    @Column(name = "client_key")
    private String clientKey;

    private UUID folderId;

    private boolean active;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
