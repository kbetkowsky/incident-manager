package com.betkowski.incidentmanager.adapters.out.persistence;

import com.betkowski.incidentmanager.domain.model.EscalationRule;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EscalationRuleMapper {
    EscalationRuleEntity toEntity(EscalationRule escalationRule);
    default EscalationRule toDomain(EscalationRuleEntity entity) {
        return EscalationRule.restore(
                entity.getId(),
                entity.getEventType(),
                entity.getThresholdCount(),
                entity.getTimeWindowMinutes()
        );
    }
}
