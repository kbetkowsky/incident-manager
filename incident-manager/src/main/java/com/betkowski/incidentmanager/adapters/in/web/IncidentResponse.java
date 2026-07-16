package com.betkowski.incidentmanager.adapters.in.web;

import com.betkowski.incidentmanager.domain.model.EventType;
import com.betkowski.incidentmanager.domain.model.Incident;
import com.betkowski.incidentmanager.domain.model.IncidentStatus;

import java.time.Instant;
import java.util.UUID;

public record IncidentResponse(
        UUID id,
        UUID deviceId,
        String deviceName,
        EventType eventType,
        IncidentStatus status,
        int occurrenceCount,
        Instant firstOccurredAt,
        Instant lastOccurredAt
) {
    public static IncidentResponse from(Incident incident) {
        return new IncidentResponse(incident.getId(), incident.getDeviceId(), incident.getDeviceName(),
                incident.getEventType(), incident.getStatus(), incident.getOccurrenceCount(),
                incident.getFirstOccurredAt(), incident.getLastOccurredAt());
    }
}
