package com.betkowski.incidentmanager.application;

import com.betkowski.incidentmanager.domain.model.Device;
import com.betkowski.incidentmanager.domain.port.DeviceRepository;

import java.util.List;

public class GetDevicesUseCase {
    private final DeviceRepository deviceRepository;

    public GetDevicesUseCase(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    public List<Device> execute(int page, int size) {
        List<Device> devices = deviceRepository.findAll(page, size);
        return devices;
    }
}
