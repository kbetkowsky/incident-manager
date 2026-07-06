package com.betkowski.incidentmanager.adapters.in.web;

import com.betkowski.incidentmanager.application.CreateDeviceUseCase;
import com.betkowski.incidentmanager.application.DeactivateDeviceUseCase;
import com.betkowski.incidentmanager.application.EnterDeviceMaintenanceUseCase;
import com.betkowski.incidentmanager.domain.model.Device;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("devices")
@RequiredArgsConstructor
public class DeviceController {
    private final DeactivateDeviceUseCase deactivateDeviceUseCase;
    private final EnterDeviceMaintenanceUseCase enterDeviceMaintenanceUseCase;
    private final CreateDeviceUseCase createDeviceUseCase;

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
}
