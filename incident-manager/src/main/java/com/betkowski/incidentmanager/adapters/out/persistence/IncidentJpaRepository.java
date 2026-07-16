package com.betkowski.incidentmanager.adapters.out.persistence;

import com.betkowski.incidentmanager.domain.model.EventType;
import com.betkowski.incidentmanager.domain.model.IncidentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface IncidentJpaRepository extends JpaRepository<IncidentEntity, UUID> {
    Optional<IncidentEntity> findById(UUID id);
    Optional<IncidentEntity> findByDeviceIdAndEventTypeAndStatusNot(UUID deviceId, EventType eventType,IncidentStatus excludedStatus);
}
