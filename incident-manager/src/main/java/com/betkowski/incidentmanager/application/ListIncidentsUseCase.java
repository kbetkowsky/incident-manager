package com.betkowski.incidentmanager.application;

import com.betkowski.incidentmanager.domain.model.Incident;
import com.betkowski.incidentmanager.domain.port.IncidentRepository;

import java.util.List;

public class ListIncidentsUseCase {
    private final IncidentRepository incidentRepository;

    public ListIncidentsUseCase(IncidentRepository incidentRepository) {
        this.incidentRepository = incidentRepository;
    }

    public List<Incident> execute() {
        return incidentRepository.findAll();
    }
}
