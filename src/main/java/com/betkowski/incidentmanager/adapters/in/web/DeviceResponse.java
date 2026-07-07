package com.betkowski.incidentmanager.adapters.in.web;

import com.betkowski.incidentmanager.domain.model.Device;

import java.util.UUID;

public record DeviceResponse(
        UUID id,
        String name,
        String status
) {
    public static DeviceResponse from(Device device) {
        return new DeviceResponse(device.getId(), device.getName(), device.getStatus().name());
    }
}
