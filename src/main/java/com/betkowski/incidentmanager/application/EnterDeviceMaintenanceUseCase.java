package com.betkowski.incidentmanager.application;

import com.betkowski.incidentmanager.application.exceptions.DeviceNotFoundException;
import com.betkowski.incidentmanager.domain.model.Device;
import com.betkowski.incidentmanager.domain.port.DeviceRepository;

import java.util.Optional;
import java.util.UUID;

public class EnterDeviceMaintenanceUseCase {

    private final DeviceRepository deviceRepository;

    public EnterDeviceMaintenanceUseCase(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    public void execute(UUID deviceId) {
        Optional<Device> deviceOptional = deviceRepository.findById(deviceId);
        Device device = deviceOptional.orElseThrow(() ->
                new DeviceNotFoundException("Device with id:" + deviceId + " not found"));
        device.enterMaintenance();
        deviceRepository.save(device);
    }
}
