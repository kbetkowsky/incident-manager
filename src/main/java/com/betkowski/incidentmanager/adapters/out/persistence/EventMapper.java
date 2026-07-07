package com.betkowski.incidentmanager.adapters.out.persistence;

import com.betkowski.incidentmanager.domain.model.Event;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EventMapper {
    EventEntity toEntity(Event event);

    default Event toDomain(EventEntity entity) {
        return Event.restore(
                entity.getId(),
                entity.getDeviceId(),
                entity.getDeviceName(),
                entity.getEventType(),
                entity.getCreatedAt()
        );
    }
}
