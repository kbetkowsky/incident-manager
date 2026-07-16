package com.betkowski.incidentmanager.application;

import com.betkowski.incidentmanager.domain.event.IncidentCreated;
import com.betkowski.incidentmanager.domain.model.EscalationRule;
import com.betkowski.incidentmanager.domain.model.Event;
import com.betkowski.incidentmanager.domain.model.Incident;
import com.betkowski.incidentmanager.domain.port.EscalationRuleRepository;
import com.betkowski.incidentmanager.domain.port.EventRepository;
import com.betkowski.incidentmanager.domain.port.IncidentRepository;
import org.springframework.context.ApplicationEventPublisher;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

public class EventEscalationUseCase {
    private final EscalationRuleRepository escalationRuleRepository;
    private final EventRepository eventRepository;
    private final IncidentRepository incidentRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    public EventEscalationUseCase(EscalationRuleRepository escalationRuleRepository,
                                  EventRepository eventRepository,
                                  IncidentRepository incidentRepository,
                                  ApplicationEventPublisher applicationEventPublisher) {
        this.escalationRuleRepository = escalationRuleRepository;
        this.eventRepository = eventRepository;
        this.incidentRepository = incidentRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void execute(Event event) {
        Optional<EscalationRule> optionalEscalationRule =
                escalationRuleRepository.findByEventType(event.getEventType());

        if (optionalEscalationRule.isEmpty()) {
            return;
        }

        EscalationRule rule = optionalEscalationRule.get();

        Instant since = Instant.now().minus(rule.getTimeWindowMinutes(), ChronoUnit.MINUTES);

        long recentEventCount = eventRepository.countByDeviceIdAndEventTypeAndCreatedAtAfter(
                event.getDeviceId(), event.getEventType(), since);

        if (recentEventCount < rule.getThresholdCount()) {
            return;
        }

        Optional<Incident> optionalIncident =
                incidentRepository.findActiveByDeviceIdAndEventType(
                        event.getDeviceId(), event.getEventType());
        if (optionalIncident.isPresent()) {
            Incident incident = optionalIncident.get();
            incident.recordOccurrence();
            incidentRepository.save(incident);
        } else {
            Incident newIncident = Incident.create(event.getDeviceId(), event.getDeviceName(),
                    event.getEventType());
            incidentRepository.save(newIncident);
            applicationEventPublisher.publishEvent(new IncidentCreated(newIncident.getId(),
                    newIncident.getDeviceId(), newIncident.getDeviceName(), newIncident.getEventType(),
                    newIncident.getFirstOccurredAt()));
        }
    }
}
