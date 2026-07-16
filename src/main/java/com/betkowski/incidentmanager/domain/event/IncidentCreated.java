package com.betkowski.incidentmanager.domain.event;

import com.betkowski.incidentmanager.domain.model.EventType;

import java.time.Instant;
import java.util.UUID;

public record IncidentCreated(
        UUID incidentId,
        UUID deviceId,
        String deviceName,
        EventType eventType,
        Instant createdAt
) {
}
