package com.betkowski.incidentmanager.application;

import com.betkowski.incidentmanager.domain.model.*;
import com.betkowski.incidentmanager.domain.port.EscalationRuleRepository;
import com.betkowski.incidentmanager.domain.port.EventRepository;
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
public class EventEscalationUseCaseTest {
    @Mock
    private EscalationRuleRepository escalationRuleRepository;
    @Mock
    private EventRepository eventRepository;
    @Mock
    private IncidentRepository incidentRepository;

    private EventEscalationUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new EventEscalationUseCase(escalationRuleRepository, eventRepository, incidentRepository);
    }

    @Test
    void shouldDoNothing_whenNoRule() {
        Event event = Event.restore(UUID.randomUUID(), UUID.randomUUID(), "Router-01", EventType.HIGH_CPU, Instant.now());
        when(escalationRuleRepository.findByEventType(EventType.HIGH_CPU)).thenReturn(Optional.empty());
        useCase.execute(event);
        verify(incidentRepository, never()).save(any());
    }

    @Test
    void shouldDoNothing_whenLimitIsLess() {
        Event event = Event.restore(UUID.randomUUID(), UUID.randomUUID(), "Router-01", EventType.HIGH_CPU, Instant.now());
        EscalationRule rule = EscalationRule.create(EventType.HIGH_CPU, 10, 5);
        when(escalationRuleRepository.findByEventType(EventType.HIGH_CPU)).thenReturn(Optional.of(rule));
        when(eventRepository.countByDeviceIdAndEventTypeAndCreatedAtAfter(any(), any(), any())).thenReturn(5L);

        useCase.execute(event);

        verify(incidentRepository, never()).save(any());
    }

    @Test
    void shouldSentAlert_whenLimitIsExceed() {
        Event event = Event.restore(UUID.randomUUID(), UUID.randomUUID(), "Router-01", EventType.HIGH_CPU, Instant.now());
        EscalationRule rule = EscalationRule.create(EventType.HIGH_CPU, 10, 5);

        when(escalationRuleRepository.findByEventType(EventType.HIGH_CPU)).thenReturn(Optional.of(rule));
        when(eventRepository.countByDeviceIdAndEventTypeAndCreatedAtAfter(any(), any(), any()))
                .thenReturn(15L);
        when(incidentRepository.findActiveByDeviceIdAndEventType(event.getDeviceId(), event.getEventType()))
                .thenReturn(Optional.empty());

        useCase.execute(event);

        verify(incidentRepository).save(any(Incident.class));
    }

    @Test
    void shouldUpdateExistingIncident_whenExceededAndActiveIncidentExists() {
        Event event = Event.restore(UUID.randomUUID(), UUID.randomUUID(), "Router-01", EventType.HIGH_CPU, Instant.now());
        EscalationRule rule = EscalationRule.create(EventType.HIGH_CPU, 10, 5);
        Incident existingIncident = Incident.restore(UUID.randomUUID(), event.getDeviceId(), "Router-01",
                EventType.HIGH_CPU, IncidentStatus.OPEN, 1, Instant.now(), Instant.now());

        when(escalationRuleRepository.findByEventType(EventType.HIGH_CPU)).thenReturn(Optional.of(rule));
        when(eventRepository.countByDeviceIdAndEventTypeAndCreatedAtAfter(any(), any(), any())).thenReturn(15L);
        when(incidentRepository.findActiveByDeviceIdAndEventType(event.getDeviceId(), event.getEventType()))
                .thenReturn(Optional.of(existingIncident));

        useCase.execute(event);

        assertEquals(2, existingIncident.getOccurrenceCount());
        verify(incidentRepository).save(existingIncident);
    }
}
