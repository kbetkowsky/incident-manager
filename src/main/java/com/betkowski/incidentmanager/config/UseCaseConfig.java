package com.betkowski.incidentmanager.config;

import com.betkowski.incidentmanager.application.CreateDeviceUseCase;
import com.betkowski.incidentmanager.application.DeactivateDeviceUseCase;
import com.betkowski.incidentmanager.application.EnterDeviceMaintenanceUseCase;
import com.betkowski.incidentmanager.application.RecordDeviceEventUseCase;
import com.betkowski.incidentmanager.domain.model.Event;
import com.betkowski.incidentmanager.domain.port.DeviceRepository;
import com.betkowski.incidentmanager.domain.port.EventRepository;
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
            DeviceRepository deviceRepository, EventRepository eventRepository) {
        return new RecordDeviceEventUseCase(deviceRepository, eventRepository);
    }
}
