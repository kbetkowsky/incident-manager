package com.betkowski.incidentmanager.domain.port;

import com.betkowski.incidentmanager.domain.model.EventType;
import com.betkowski.incidentmanager.domain.model.Incident;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IncidentRepository {
    void save(Incident incident);
    Optional<Incident> findById(UUID id);
    Optional<Incident> findActiveByDeviceIdAndEventType(UUID deviceId, EventType eventType);
    List<Incident> findAll();
}
