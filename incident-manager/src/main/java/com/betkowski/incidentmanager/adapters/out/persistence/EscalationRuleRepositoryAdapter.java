package com.betkowski.incidentmanager.adapters.out.persistence;

import com.betkowski.incidentmanager.domain.model.EscalationRule;
import com.betkowski.incidentmanager.domain.model.EventType;
import com.betkowski.incidentmanager.domain.port.EscalationRuleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class EscalationRuleRepositoryAdapter implements EscalationRuleRepository {

    private final EscalationRuleMapper mapper;
    private final EscalationRuleJpaRepository repository;

    @Override
    public Optional<EscalationRule> findByEventType(EventType eventType) {
         return repository.findByEventType(eventType)
                 .map(mapper::toDomain);
    }
}
