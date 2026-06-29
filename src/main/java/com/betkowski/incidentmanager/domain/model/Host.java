package com.betkowski.incidentmanager.domain.model;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public class Host {
    private final UUID id;
    private final String name;
    private final String address;
    private HostStatus status;
    private final Instant createdAt;

    private Host(UUID id, String name, String address, HostStatus status, Instant createdAt) {
        this.id = Objects.requireNonNull(id, "Host id cannot be null");
        this.name = validateRequiredText(name, "Host name cannot be blank");
        this.address = validateRequiredText(address, "Host address cannot be blank");
        this.status = Objects.requireNonNull(status, "Host status cannot be null");
        this.createdAt = Objects.requireNonNull(createdAt, "Created date cannot be null");
    }

    public static Host create(String name, String address) {
        return new Host(
                UUID.randomUUID(),
                name,
                address,
                HostStatus.ACTIVE,
                Instant.now()
        );
    }

    public static Host restore(UUID id, String name, String address, HostStatus status, Instant createdAt) {
        return new Host(id, name, address, status, createdAt);
    }

    public void activate() {
        this.status = HostStatus.ACTIVE;
    }

    public void deactivate() {
        this.status = HostStatus.INACTIVE;
    }

    public boolean isActive() {
        return status == HostStatus.ACTIVE;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public HostStatus getStatus() {
        return status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    private static String validateRequiredText(String value, String message) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(message);
        }

        return value;
    }
}
