package com.betkowski.incidentmanager.application;

import com.betkowski.incidentmanager.domain.model.Device;
import com.betkowski.incidentmanager.domain.port.DeviceRepository;
import org.springframework.transaction.annotation.Transactional;

public class CreateDeviceUseCase {
    private final DeviceRepository deviceRepository;

    public CreateDeviceUseCase(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    @Transactional
    public Device execute(String name, String address) {
        Device device = Device.create(name, address);
        deviceRepository.save(device);
        return device;
    }

}
