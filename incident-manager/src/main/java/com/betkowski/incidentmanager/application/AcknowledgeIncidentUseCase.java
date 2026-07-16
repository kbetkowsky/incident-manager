package com.betkowski.incidentmanager.application;

import com.betkowski.incidentmanager.application.exceptions.IncidentNotFoundException;
import com.betkowski.incidentmanager.domain.model.Incident;
import com.betkowski.incidentmanager.domain.port.IncidentRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

public class AcknowledgeIncidentUseCase {
    private final IncidentRepository repository;

    public AcknowledgeIncidentUseCase(IncidentRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Incident execute(UUID incidentId) {
        Optional<Incident> incidentOptional = repository.findById(incidentId);
        Incident incident = incidentOptional.orElseThrow( () ->
                new IncidentNotFoundException("Incident with id:" + incidentId + " not found") );
        incident.acknowledge();
        repository.save(incident);
        return incident;
    }
}
