package com.betkowski.incidentmanager.application;

import com.betkowski.incidentmanager.domain.model.Device;
import com.betkowski.incidentmanager.domain.port.DeviceRepository;

import java.util.List;

public class GetDevicesUseCase {
    private final DeviceRepository deviceRepository;

    public GetDevicesUseCase(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    public List<Device> execute() {
        List<Device> devices = deviceRepository.findAll();
        return devices;
    }
}
