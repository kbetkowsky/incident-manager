package com.betkowski.incidentmanager.config;

import com.betkowski.incidentmanager.application.*;
import com.betkowski.incidentmanager.domain.model.Event;
import com.betkowski.incidentmanager.domain.port.DeviceRepository;
import com.betkowski.incidentmanager.domain.port.EscalationRuleRepository;
import com.betkowski.incidentmanager.domain.port.EventRepository;
import com.betkowski.incidentmanager.domain.port.IncidentRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {

    @Bean
    public EnterDeviceMaintenanceUseCase enterDeviceMaintenanceUseCase(DeviceRepository deviceRepository) {
        return new EnterDeviceMaintenanceUseCase(deviceRepository);
    }

    @Bean
    public DeactivateDeviceUseCase deactivateDeviceUseCase(DeviceRepository deviceRepository) {
        return new DeactivateDeviceUseCase(deviceRepository);
    }

    @Bean
    public CreateDeviceUseCase createDeviceUseCase(DeviceRepository deviceRepository) {
        return new CreateDeviceUseCase(deviceRepository);
    }

    @Bean
    public RecordDeviceEventUseCase recordDeviceEventUseCase(
            DeviceRepository deviceRepository, EventRepository eventRepository,
            EventEscalationUseCase eventEscalationUseCase) {
        return new RecordDeviceEventUseCase(deviceRepository, eventRepository, eventEscalationUseCase);
    }

    @Bean
    public EventEscalationUseCase eventEscalationUseCase(
            EscalationRuleRepository escalationRuleRepository,
            EventRepository eventRepository, IncidentRepository incidentRepository,
            ApplicationEventPublisher applicationEventPublisher) {
        return new EventEscalationUseCase(escalationRuleRepository, eventRepository,
                incidentRepository, applicationEventPublisher);
    }

    @Bean
    public AcknowledgeIncidentUseCase acknowledgeIncidentUseCase(
            IncidentRepository incidentRepository
    ) {
        return new AcknowledgeIncidentUseCase(incidentRepository);
    }

    @Bean
    public ResolveIncidentUseCase resolveIncidentUseCase(IncidentRepository incidentRepository) {
        return new ResolveIncidentUseCase(incidentRepository);
    }

    @Bean
    public GetDevicesUseCase getDevicesUseCase(DeviceRepository deviceRepository) {
        return new GetDevicesUseCase(deviceRepository);
    }
}
