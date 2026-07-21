package com.betkowski.incidentmanager.domain.port;

import com.betkowski.incidentmanager.domain.model.Device;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DeviceRepository {
    Optional<Device> findById(UUID id);
    void save(Device device);
    List<Device> findAll();
}
