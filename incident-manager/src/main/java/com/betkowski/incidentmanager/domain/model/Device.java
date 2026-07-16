package com.betkowski.incidentmanager.domain.model;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public class Device {
    private final UUID id;
    private final String name;
    private final String address;
    private DeviceStatus status;
    private final Instant createdAt;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Device device = (Device) o;
        return Objects.equals(id, device.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    private Device(UUID id, String name, String address, DeviceStatus status, Instant createdAt) {
        this.id = Objects.requireNonNull(id, "Device id cannot be null");
        this.name = validateRequiredText(name, "Device name cannot be blank");
        this.address = validateRequiredText(address, "Device address cannot be blank");
        this.status = Objects.requireNonNull(status, "Device status cannot be null");
        this.createdAt = Objects.requireNonNull(createdAt, "Created date cannot be null");
    }

    public static Device create(String name, String address) {
        return new Device(
                UUID.randomUUID(),
                name,
                address,
                DeviceStatus.ACTIVE,
                Instant.now()
        );
    }

    public static Device restore(UUID id, String name, String address, DeviceStatus status, Instant createdAt) {
        return new Device(id, name, address, status, createdAt);
    }

    public void activate() {
        if (status == DeviceStatus.MAINTENANCE || status == DeviceStatus.ACTIVE) {
            throw new IllegalStateException("Cannot be ACTIVE direct from " + status);
        }
        this.status = DeviceStatus.ACTIVE;
    }

    public void deactivate() {
        if (status == DeviceStatus.INACTIVE) {
            throw new IllegalStateException("Cannot enter deactivate when its already " + status);
        }
        this.status = DeviceStatus.INACTIVE;
    }

    public void enterMaintenance() {
        if (status == DeviceStatus.MAINTENANCE) {
            throw new IllegalStateException("Cannot enter maintenance when you are in " + status);
        }
        this.status = DeviceStatus.MAINTENANCE;
    }

    public void exitMaintenance() {
        if (status != DeviceStatus.MAINTENANCE) {
            throw new IllegalStateException("Cannot enter ACTIVE when device is not in MAINTENANCE");
        }
        this.status = DeviceStatus.ACTIVE;
    }

    public boolean isActive() {
        return status == DeviceStatus.ACTIVE;
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

    public DeviceStatus getStatus() {
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
