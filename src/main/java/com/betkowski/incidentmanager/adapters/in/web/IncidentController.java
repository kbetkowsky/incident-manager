package com.betkowski.incidentmanager.adapters.in.web;

import com.betkowski.incidentmanager.application.AcknowledgeIncidentUseCase;
import com.betkowski.incidentmanager.application.ResolveIncidentUseCase;
import com.betkowski.incidentmanager.domain.model.Incident;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("incidents")
@RequiredArgsConstructor
public class IncidentController {
    private final AcknowledgeIncidentUseCase acknowledgeIncidentUseCase;
    private final ResolveIncidentUseCase resolveIncidentUseCase;

    @PostMapping("/{id}/acknowledge")
    public ResponseEntity<IncidentResponse> acknowledge(@PathVariable UUID id) {
        Incident incident = acknowledgeIncidentUseCase.execute(id);
        return ResponseEntity.ok(IncidentResponse.from(incident));
    }

    @PostMapping("/{id}/resolve")
    public ResponseEntity<IncidentResponse> resolve(@PathVariable UUID id) {
        Incident incident = resolveIncidentUseCase.execute(id);
        return ResponseEntity.ok(IncidentResponse.from(incident));
    }
}
