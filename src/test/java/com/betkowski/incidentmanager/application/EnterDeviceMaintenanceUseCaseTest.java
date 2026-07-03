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

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class EnterDeviceMaintenanceUseCaseTest {
    @Mock
    private DeviceRepository deviceRepository;

    private EnterDeviceMaintenanceUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new EnterDeviceMaintenanceUseCase(deviceRepository);
    }

    @Test
    void shouldSaveToDataBase_whenFindById() {
        // ARRANGE
        UUID deviceId = UUID.randomUUID();
        Device device = Device.restore(deviceId, "pr-01", "192.168.0.1", DeviceStatus.ACTIVE, Instant.now());

        when(deviceRepository.findById(deviceId)).thenReturn(Optional.of(device));

        // ACT
        useCase.execute(deviceId);

        // ASSERT/VERIFY
        assertEquals(DeviceStatus.MAINTENANCE, device.getStatus());
        verify(deviceRepository).save(device);
    }

    @Test
    void shouldntSaveToDatabase_whenNotFindById() {
        UUID deviceId = UUID.randomUUID();
        when(deviceRepository.findById(deviceId)).thenReturn(Optional.empty());

        assertThrows(DeviceNotFoundException.class, () -> useCase.execute(deviceId));
    }

    @Test
    void shouldThrowException_whenStateIsMaintenance() {
        // ARRANGE
        UUID deviceId = UUID.randomUUID();
        Device device = Device.restore(deviceId, "pr-01", "192.168.0.1", DeviceStatus.MAINTENANCE, Instant.now());

        when(deviceRepository.findById(deviceId)).thenReturn(Optional.of(device));

        // ASSERT/VERIFY
        assertThrows(IllegalStateException.class, () -> useCase.execute(deviceId));

        verify(deviceRepository, never()).save(any());
    }
}
