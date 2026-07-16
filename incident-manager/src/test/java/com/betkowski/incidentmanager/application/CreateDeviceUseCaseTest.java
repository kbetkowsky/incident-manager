package com.betkowski.incidentmanager.application;

import com.betkowski.incidentmanager.domain.model.Device;
import com.betkowski.incidentmanager.domain.model.DeviceStatus;
import com.betkowski.incidentmanager.domain.port.DeviceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreateDeviceUseCaseTest {
    @Mock
    private DeviceRepository deviceRepository;

    private CreateDeviceUseCase createDeviceUseCase;

    @BeforeEach
    void setUp() {
        createDeviceUseCase = new CreateDeviceUseCase(deviceRepository);
    }

    @Test
    void shouldCreateNewDevice_whenDataIsValid() {
        Device device = createDeviceUseCase.execute("PR01", "10.10.25.1");
        assertEquals(DeviceStatus.ACTIVE, device.getStatus());
        verify(deviceRepository).save(device);
    }

    @Test
    void shouldNotSaveDevice_whenCreationFails() {
        assertThrows(IllegalArgumentException.class, () -> createDeviceUseCase.execute("", "10.10.25.1"));
        verify(deviceRepository, never()).save(any());
    }
}
