package com.betkowski.incidentmanager.adapters.out.persistence;

import com.betkowski.incidentmanager.domain.model.EventType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.UUID;

public interface EventJpaRepository extends JpaRepository<EventEntity, UUID> {
    long countByDeviceIdAndEventTypeAndCreatedAtAfter(UUID deviceId, EventType eventType, Instant since);
}
