package com.betkowski.incidentmanager.adapters.out.persistence;

import com.betkowski.incidentmanager.domain.model.EventType;
import com.betkowski.incidentmanager.domain.model.Incident;
import com.betkowski.incidentmanager.domain.model.IncidentStatus;
import com.betkowski.incidentmanager.domain.port.IncidentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class IncidentRepositoryAdapter implements IncidentRepository {

    private final IncidentJpaRepository repository;
    private final IncidentMapper mapper;

    @Override
    public void save(Incident incident) {
        IncidentEntity entity = mapper.toEntity(incident);
        repository.save(entity);
    }

    @Override
    public Optional<Incident> findById(UUID id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Optional<Incident> findActiveByDeviceIdAndEventType(UUID deviceId, EventType eventType) {
        return repository.findByDeviceIdAndEventTypeAndStatusNot(deviceId, eventType, IncidentStatus.RESOLVED)
                .map(mapper::toDomain);
    }

    @Override
    public List<Incident> findAll() {
        return repository.findAll().stream().map(mapper::toDomain).toList();
    }
}
