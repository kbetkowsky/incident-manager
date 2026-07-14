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
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class AcknowledgeIncidentUseCaseTest {
    @Mock
    private IncidentRepository repository;

    private AcknowledgeIncidentUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new AcknowledgeIncidentUseCase(repository);
    }

    @Test
    void shouldAcknowledge_whenStatusIsOpen() {
        UUID incidentId = UUID.randomUUID();
        Incident incident = Incident.restore(
                incidentId,
                UUID.randomUUID(),
                "Router-01",
                EventType.HIGH_CPU,
                IncidentStatus.OPEN,
                1,
                Instant.now(),
                Instant.now()
        );

        when(repository.findById(incidentId)).thenReturn(Optional.of(incident));

        Incident result = useCase.execute(incidentId);

        assertEquals(IncidentStatus.ACKNOWLEDGED, incident.getStatus());
        assertSame(result, incident);
        verify(repository).save(incident);
    }

    @Test
    void shouldNotAcknowledge_whenStatusIsAcknowledged() {
        UUID incidentId = UUID.randomUUID();
        Incident incident = Incident.restore(
                incidentId,
                UUID.randomUUID(),
                "Router-01",
                EventType.UNRESPONSIVE,
                IncidentStatus.ACKNOWLEDGED,
                1,
                Instant.now(),
                Instant.now()
        );

        when(repository.findById(incidentId)).thenReturn(Optional.of(incident));

        assertThrows(IllegalStateException.class, () -> useCase.execute(incidentId));
        verify(repository, never()).save(any());
    }

    @Test
    void shouldNotAcknowledge_whenStatusIsResolved() {
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
    void shouldNotAcknowledge_whenIncidentNotFound() {
        UUID incidentId = UUID.randomUUID();
        when(repository.findById(incidentId)).thenReturn(Optional.empty());

        assertThrows(IncidentNotFoundException.class, () -> useCase.execute(incidentId));
        verify(repository, never()).save(any());
    }
}
