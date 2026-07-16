package com.betkowski.incidentmanager.domain.port;

import com.betkowski.incidentmanager.domain.model.Event;
import com.betkowski.incidentmanager.domain.model.EventType;

import java.time.Instant;
import java.util.UUID;

public interface EventRepository {
    void save(Event event);
    long countByDeviceIdAndEventTypeAndCreatedAtAfter(UUID deviceId, EventType eventType, Instant since);
}
