package com.betkowski.incidentmanager.application;

import com.betkowski.incidentmanager.application.exceptions.IncidentNotFoundException;
import com.betkowski.incidentmanager.domain.model.Incident;
import com.betkowski.incidentmanager.domain.port.IncidentRepository;

import java.util.Optional;
import java.util.UUID;

public class ResolveIncidentUseCase {
    private final IncidentRepository repository;

    public ResolveIncidentUseCase(IncidentRepository repository) {
        this.repository = repository;
    }

    public Incident execute(UUID incidentId) {
        Optional<Incident> incidentOptional = repository.findById(incidentId);
        Incident incident = incidentOptional.orElseThrow( () -> new IncidentNotFoundException(
                "Incident with id:" + incidentId + " not found"));
        incident.resolve();
        repository.save(incident);
        return incident;
    }
}
