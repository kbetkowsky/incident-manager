package com.betkowski.incidentmanager.application;

import com.betkowski.incidentmanager.application.exceptions.IncidentNotFoundException;
import com.betkowski.incidentmanager.domain.model.EventType;
import com.betkowski.incidentmanager.domain.model.Incident;
import com.betkowski.incidentmanager.domain.model.IncidentStatus;
import com.betkowski.incidentmanager.domain.port.IncidentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ResolveIncidentUseCaseTest {
    @Mock
    private IncidentRepository repository;

    private ResolveIncidentUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new ResolveIncidentUseCase(repository);
    }

    @Test
    void shouldResolve_whenStatusIsDifferThanResolve() {
        UUID incidentId = UUID.randomUUID();
        Incident incident = Incident.restore(
                incidentId,
                UUID.randomUUID(),
                "Router-01",
                EventType.UNRESPONSIVE,
                IncidentStatus.OPEN,
                1,
                Instant.now(),
                Instant.now()
        );
        when(repository.findById(incidentId)).thenReturn(Optional.of(incident));

        Incident result = useCase.execute(incidentId);

        assertEquals(IncidentStatus.RESOLVED, incident.getStatus());
        assertSame(incident, result);
        verify(repository).save(incident);
    }

    @Test
    void shouldNotResolve_whenStatusIsResolve() {
        UUID incidentId = UUID.randomUUID();
        Incident incident = Incident.restore(
                incidentId,
                UUID.randomUUID(),
                "Router-01",
                EventType.UNRESPONSIVE,
                IncidentStatus.RESOLVED,
                1,
                Instant.now(),
                Instant.now()
        );
        when(repository.findById(incidentId)).thenReturn(Optional.of(incident));
        assertThrows(IllegalStateException.class, () -> useCase.execute(incidentId));
        verify(repository, never()).save(any());
    }

    @Test
    void shouldNotResolve_whenIncidentNotFound() {
        UUID incidentId = UUID.randomUUID();
        when(repository.findById(incidentId)).thenReturn(Optional.empty());

        assertThrows(IncidentNotFoundException.class, () -> useCase.execute(incidentId));
        verify(repository, never()).save(any());
    }
}
