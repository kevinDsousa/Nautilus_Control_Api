package com.nautilus_control_api.domain.model.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class User {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    private String username;

    private String email;

    @Column(name = "firebase_uid")
    private String firebaseUID;
}
