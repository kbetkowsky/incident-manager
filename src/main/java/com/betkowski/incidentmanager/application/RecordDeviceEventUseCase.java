package com.betkowski.incidentmanager.application;

import com.betkowski.incidentmanager.application.exceptions.DeviceNotFoundException;
import com.betkowski.incidentmanager.domain.model.Device;
import com.betkowski.incidentmanager.domain.model.Event;
import com.betkowski.incidentmanager.domain.model.EventType;
import com.betkowski.incidentmanager.domain.port.DeviceRepository;
import com.betkowski.incidentmanager.domain.port.EventRepository;

import java.util.Optional;
import java.util.UUID;

public class RecordDeviceEventUseCase {
    private final DeviceRepository deviceRepository;
    private final EventRepository eventRepository;

    public RecordDeviceEventUseCase(DeviceRepository deviceRepository, EventRepository eventRepository) {
        this.deviceRepository = deviceRepository;
        this.eventRepository = eventRepository;
    }

    public Event execute(UUID deviceId, EventType eventType) {
        Optional<Device> deviceOptional = deviceRepository.findById(deviceId);
        Device device = deviceOptional.orElseThrow(() -> new DeviceNotFoundException(
               "Device with id:" + deviceId + " not found"
       ));
        Event event = Event.create(deviceId, device.getName(), eventType);
        eventRepository.save(event);
        return event;
    }
}
