package com.betkowski.incidentmanager.adapters.in.web;

import com.betkowski.incidentmanager.domain.model.Event;
import com.betkowski.incidentmanager.domain.model.EventType;

import java.time.Instant;
import java.util.UUID;

public record EventResponse(
        UUID id,
        UUID deviceId,
        String deviceName,
        EventType eventType,
        Instant createdAt
) {
    public static EventResponse from(Event event) {
        return new EventResponse(event.getId(), event.getDeviceId(), event.getDeviceName(), event.getEventType()
        , event.getCreatedAt());
    }
}
