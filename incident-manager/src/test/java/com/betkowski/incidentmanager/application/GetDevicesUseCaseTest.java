package com.betkowski.incidentmanager.application;

import com.betkowski.incidentmanager.domain.model.Device;
import com.betkowski.incidentmanager.domain.port.DeviceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class GetDevicesUseCaseTest {
    @Mock
    private DeviceRepository deviceRepository;

    private GetDevicesUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new GetDevicesUseCase(deviceRepository);
    }

    @Test
    void shouldReturnListOfDeviceWithProperPagination() {
        // ARRANGE
        Device device = Device.create("router", "192.168.0.1");
        Device device2 = Device.create("router2", "192.168.0.2");
        Device device3 = Device.create("router3", "192.168.0.3");
        List<Device> deviceList = List.of(device, device2, device3);
        when(deviceRepository.findAll(2, 20)).thenReturn(deviceList);

        // ACT
        List<Device> result = useCase.execute(2, 20);

        // ASSERT
        assertEquals(deviceList, result);
    }
}
