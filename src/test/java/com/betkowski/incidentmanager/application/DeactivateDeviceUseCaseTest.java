package com.betkowski.incidentmanager.application;

import com.betkowski.incidentmanager.application.exceptions.DeviceNotFoundException;
import com.betkowski.incidentmanager.domain.model.Device;
import com.betkowski.incidentmanager.domain.model.DeviceStatus;
import com.betkowski.incidentmanager.domain.port.DeviceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeactivateDeviceUseCaseTest {
    @Mock
    private DeviceRepository deviceRepository;

    private DeactivateDeviceUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new DeactivateDeviceUseCase(deviceRepository);
    }

    @Test
    void shouldDeactivateDevice_whenDeviceIsActive() {
        UUID deviceId = UUID.randomUUID();
        Device device = Device.restore(deviceId, "pr-01", "192.168.0.1", DeviceStatus.ACTIVE, Instant.now());

        when(deviceRepository.findById(deviceId)).thenReturn(Optional.of(device));

        Device result = useCase.execute(deviceId);

        assertEquals(DeviceStatus.INACTIVE, device.getStatus());
        assertSame(device, result);
        verify(deviceRepository).save(device);
    }

    @Test
    void shouldDeactivateDevice_whenDeviceIsMaintenanceState() {
        UUID deviceId = UUID.randomUUID();
        Device device = Device.restore(deviceId, "pr-01", "192.168.0.1", DeviceStatus.MAINTENANCE, Instant.now());

        when(deviceRepository.findById(deviceId)).thenReturn(Optional.of(device));

        Device result = useCase.execute(deviceId);

        assertEquals(DeviceStatus.INACTIVE, device.getStatus());
        assertSame(device, result);
        verify(deviceRepository).save(device);
    }

    @Test
    void shouldThrowException_whenDeviceStateIsInactive() {
        UUID deviceId = UUID.randomUUID();
        Device device = Device.restore(deviceId, "pr-01", "192.168.0.1", DeviceStatus.INACTIVE, Instant.now());

        when(deviceRepository.findById(deviceId)).thenReturn(Optional.of(device));

        assertThrows(IllegalStateException.class, () -> useCase.execute(deviceId));

        verify(deviceRepository, never()).save(any());
    }

    @Test
    void shouldThrowException_whenDeviceNotFound() {
        UUID deviceId = UUID.randomUUID();
        when(deviceRepository.findById(deviceId)).thenReturn(Optional.empty());

        assertThrows(DeviceNotFoundException.class, () -> useCase.execute(deviceId));

        verify(deviceRepository, never()).save(any());
    }
}
