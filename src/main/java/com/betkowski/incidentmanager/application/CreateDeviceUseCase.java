package com.betkowski.incidentmanager.application;

import com.betkowski.incidentmanager.domain.model.Device;
import com.betkowski.incidentmanager.domain.port.DeviceRepository;

public class CreateDeviceUseCase {
    private final DeviceRepository deviceRepository;

    public CreateDeviceUseCase(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    public Device execute(String name, String address) {
        Device device = Device.create(name, address);
        deviceRepository.save(device);
        return device;
    }

}
