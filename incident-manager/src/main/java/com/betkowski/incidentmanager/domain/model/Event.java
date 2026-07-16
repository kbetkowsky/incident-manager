package com.betkowski.incidentmanager.domain.model;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public class Event {
    private final UUID id;
    private final UUID deviceId;
    private final String deviceName;
    private final EventType eventType;
    private final Instant createdAt;

    private Event(UUID id, UUID deviceId, String deviceName, EventType eventType, Instant createdAt) {
        this.id = id;
        this.deviceId = deviceId;
        this.deviceName = deviceName;
        this.eventType = eventType;
        this.createdAt = createdAt;
    }

    public static Event create(UUID deviceId, String deviceName, EventType eventType) {
        return new Event(
                UUID.randomUUID(),
                deviceId,
                deviceName,
                eventType,
                Instant.now()
        );
    }

    public static Event restore(UUID id, UUID deviceId, String deviceName, EventType eventType, Instant createdAt) {
        return new Event(id, deviceId, deviceName, eventType, createdAt);
    }

    public UUID getId() {
        return id;
    }

    public UUID getDeviceId() {
        return deviceId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public EventType getEventType() {
        return eventType;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return Objects.equals(id, event.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
