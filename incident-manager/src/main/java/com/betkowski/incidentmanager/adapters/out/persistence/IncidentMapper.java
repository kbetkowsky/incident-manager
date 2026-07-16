package com.betkowski.incidentmanager.adapters.out.persistence;

import com.betkowski.incidentmanager.domain.model.Incident;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IncidentMapper {
    IncidentEntity toEntity(Incident incident);

    default Incident toDomain(IncidentEntity entity) {
        return Incident.restore(
                entity.getId(),
                entity.getDeviceId(),
                entity.getDeviceName(),
                entity.getEventType(),
                entity.getStatus(),
                entity.getOccurrenceCount(),
                entity.getFirstOccurredAt(),
                entity.getLastOccurredAt()
        );
    }

}
