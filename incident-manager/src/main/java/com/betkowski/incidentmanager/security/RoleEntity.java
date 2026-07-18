package com.betkowski.incidentmanager.security;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "roles")
@NoArgsConstructor
@Getter
public class RoleEntity {
    @Id
    private UUID id;
    @Column(nullable = false)
    private String name;
}
