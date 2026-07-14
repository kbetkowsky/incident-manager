package com.betkowski.incidentmanager.adapters.out.persistence;

import com.betkowski.incidentmanager.domain.model.Event;
import com.betkowski.incidentmanager.domain.model.EventType;
import com.betkowski.incidentmanager.domain.port.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class EventRepositoryAdapter implements EventRepository {

    private final EventJpaRepository repository;
    private final EventMapper mapper;

    @Override
    public void save(Event event) {
        EventEntity entity = mapper.toEntity(event);
        repository.save(entity);
    }

    @Override
    public long countByDeviceIdAndEventTypeAndCreatedAtAfter(UUID deviceId, EventType eventType, Instant since) {
        return repository.countByDeviceIdAndEventTypeAndCreatedAtAfter(deviceId, eventType, since);
    }
}
