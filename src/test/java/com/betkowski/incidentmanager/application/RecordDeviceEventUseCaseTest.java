package com.betkowski.incidentmanager.application;

import com.betkowski.incidentmanager.application.exceptions.DeviceNotFoundException;
import com.betkowski.incidentmanager.domain.model.Device;
import com.betkowski.incidentmanager.domain.model.DeviceStatus;
import com.betkowski.incidentmanager.domain.model.Event;
import com.betkowski.incidentmanager.domain.model.EventType;
import com.betkowski.incidentmanager.domain.port.DeviceRepository;
import com.betkowski.incidentmanager.domain.port.EventRepository;
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
public class RecordDeviceEventUseCaseTest {
    @Mock
    private DeviceRepository deviceRepository;

    @Mock
    private EventRepository eventRepository;

    @Mock
    EventEscalationUseCase eventEscalationUseCase;

    private RecordDeviceEventUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new RecordDeviceEventUseCase(deviceRepository, eventRepository, eventEscalationUseCase);
    }

    @Test
    void shouldSaveEvent_whenFindById() {
        UUID deviceId = UUID.randomUUID();
        Device device = Device.restore(deviceId, "PR01","10.10.25.1", DeviceStatus.ACTIVE, Instant.now());
        when(deviceRepository.findById(deviceId)).thenReturn(Optional.of(device));

        Event result = useCase.execute(deviceId, EventType.BACK_ONLINE);

        assertEquals("PR01", result.getDeviceName());
        verify(eventRepository).save(result);
    }

    @Test
    void shouldNotSaveEvent_whenDeviceDoesNotExist() {
        UUID deviceId = UUID.randomUUID();
        when(deviceRepository.findById(deviceId)).thenReturn(Optional.empty());

        assertThrows(DeviceNotFoundException.class, () -> useCase.execute(deviceId, EventType.BACK_ONLINE));
        verify(eventRepository, never()).save(any());
    }
}
