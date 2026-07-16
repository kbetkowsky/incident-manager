package com.betkowski.incidentmanager.adapters.out.persistence;

import com.betkowski.incidentmanager.domain.model.EventType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface EscalationRuleJpaRepository extends JpaRepository<EscalationRuleEntity, UUID> {
    Optional<EscalationRuleEntity> findByEventType(EventType eventType);
}
