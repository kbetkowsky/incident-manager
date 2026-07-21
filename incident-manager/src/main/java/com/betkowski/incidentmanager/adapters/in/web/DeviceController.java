package com.betkowski.incidentmanager.adapters.in.web;

import com.betkowski.incidentmanager.application.*;
import com.betkowski.incidentmanager.domain.model.Device;
import com.betkowski.incidentmanager.domain.model.Event;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("devices")
@RequiredArgsConstructor
public class DeviceController {
    private final DeactivateDeviceUseCase deactivateDeviceUseCase;
    private final EnterDeviceMaintenanceUseCase enterDeviceMaintenanceUseCase;
    private final CreateDeviceUseCase createDeviceUseCase;
    private final RecordDeviceEventUseCase recordDeviceEventUseCase;
    private final GetDevicesUseCase getDevicesUseCase;

    @PostMapping("/{id}/maintenance")
    public ResponseEntity<DeviceResponse> enterMaintenance(@PathVariable UUID id) {
        Device device = enterDeviceMaintenanceUseCase.execute(id);
        return ResponseEntity.ok(DeviceResponse.from(device));
    }

    @PostMapping("/{id}/deactivate")
    public ResponseEntity<DeviceResponse> deactivateDevice(@PathVariable UUID id) {
        Device device = deactivateDeviceUseCase.execute(id);
        return ResponseEntity.ok(DeviceResponse.from(device));
    }

    @PostMapping
    public ResponseEntity<DeviceResponse> create(@RequestBody DeviceRequest request) {
        Device device = createDeviceUseCase.execute(request.name(), request.address());
        URI location = URI.create("/devices/" + device.getId());
        return ResponseEntity.created(location).body(DeviceResponse.from(device));
    }

    @PostMapping("/{id}/events")
    public ResponseEntity<EventResponse> recordEvent(@PathVariable UUID id,
                                                     @RequestBody EventRequest request) {
        Event event = recordDeviceEventUseCase.execute(id, request.eventType());
        return ResponseEntity.status(HttpStatus.CREATED).body(EventResponse.from(event));
    }

    @GetMapping
    public ResponseEntity<List<DeviceResponse>> getAllDevices() {
        List<Device> devices = getDevicesUseCase.execute();
        List<DeviceResponse> body = devices.stream()
                .map(DeviceResponse::from)
                .toList();
        return ResponseEntity.ok(body);
    }
}
