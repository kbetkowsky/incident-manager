package com.betkowski.notificationservice;

import java.time.Instant;
import java.util.UUID;

public record IncidentCreated(
        UUID incidentId,
        UUID deviceId,
        String deviceName,
        String eventType,
        Instant createdAt
) {
}
