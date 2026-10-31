package com.nautilus_control_api.domain.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
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
@Table(name = "host_folder")
public class HostFolder {
    @Id
    @GeneratedValue
    private UUID id;

    private String name;

    private UUID parentId;

    private LocalDateTime createdAt;
}
