package com.betkowski.incidentmanager.domain.model;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public class Incident {
    private final UUID id;
    private final UUID deviceId;
    private final String deviceName;
    private final EventType eventType;
    private IncidentStatus status;
    private int occurrenceCount;
    private final Instant firstOccurredAt;
    private Instant lastOccurredAt;


    private Incident(UUID id, UUID deviceId, String deviceName, EventType eventType, IncidentStatus status
            , int occurrenceCount, Instant firstOccurredAt, Instant lastOccurredAt) {
        this.id = Objects.requireNonNull(id, "Incident id cannot be null");
        this.deviceId = Objects.requireNonNull(deviceId, "Device id cannot be null");
        this.deviceName = validateRequiredText(deviceName, "Device name cannot be blank");
        this.eventType = Objects.requireNonNull(eventType, "Event type cannot be null");
        this.status = Objects.requireNonNull(status, "Incident status cannot be null");
        this.occurrenceCount = occurrenceCount;
        this.firstOccurredAt = Objects.requireNonNull(firstOccurredAt, "First occurred date cannot be null");
        this.lastOccurredAt = Objects.requireNonNull(lastOccurredAt, "Last occurred date cannot be null");
    }

    public static Incident create(UUID deviceId, String deviceName, EventType eventType) {
        return new Incident(
                UUID.randomUUID(),
                deviceId,
                deviceName,
                eventType,
                IncidentStatus.OPEN,
                1,
                Instant.now(),
                Instant.now()
        );
    }

    public static Incident restore(UUID id, UUID deviceId, String deviceName, EventType eventType,
                                   IncidentStatus status, int occurrenceCount, Instant firstOccurredAt,
                                   Instant lastOccurredAt) {
        return new Incident(id, deviceId, deviceName, eventType, status, occurrenceCount, firstOccurredAt, lastOccurredAt);
    }

    public void acknowledge() {
        if (status == IncidentStatus.RESOLVED || status == IncidentStatus.ACKNOWLEDGED) {
            throw new IllegalStateException("Cannot enter ACKNOWLEDGED when device is " + status);
        }
        this.status = IncidentStatus.ACKNOWLEDGED;
    }

    public void resolve() {
        if (status == IncidentStatus.RESOLVED) {
            throw new IllegalStateException("Cannot be resolved when status is " + status);
        }
        this.status = IncidentStatus.RESOLVED;
    }

    public void recordOccurrence() {
        this.occurrenceCount++;
        this.lastOccurredAt = Instant.now();
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

    public IncidentStatus getStatus() {
        return status;
    }

    public int getOccurrenceCount() {
        return occurrenceCount;
    }

    public Instant getFirstOccurredAt() {
        return firstOccurredAt;
    }

    public Instant getLastOccurredAt() {
        return lastOccurredAt;
    }

    private static String validateRequiredText(String value, String message) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(message);
        }

        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Incident incident = (Incident) o;
        return Objects.equals(id, incident.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
