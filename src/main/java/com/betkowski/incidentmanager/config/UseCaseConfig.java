package com.betkowski.incidentmanager.config;

import com.betkowski.incidentmanager.application.DeactivateDeviceUseCase;
import com.betkowski.incidentmanager.application.EnterDeviceMaintenanceUseCase;
import com.betkowski.incidentmanager.domain.port.DeviceRepository;
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
}
