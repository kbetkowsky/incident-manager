package com.betkowski.incidentmanager.domain.port;

import com.betkowski.incidentmanager.domain.model.EscalationRule;
import com.betkowski.incidentmanager.domain.model.EventType;

import java.util.Optional;

public interface EscalationRuleRepository {
    Optional<EscalationRule> findByEventType(EventType eventType);
}
