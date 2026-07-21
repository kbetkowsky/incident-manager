package com.betkowski.notificationservice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.UUID;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class IncidentEventConsumerTest {
    @Mock
    private NotificationSender notificationSender;

    private IncidentEventConsumer incidentEventConsumer;

    @BeforeEach
    void setUp() {
        incidentEventConsumer = new IncidentEventConsumer(notificationSender);
    }

    @Test
    void shouldSendPush_whenEventReceived() {
        IncidentCreated incidentCreated = new IncidentCreated(
                UUID.randomUUID(),
                UUID.randomUUID(),
                "router-01",
                "DEVICE_DOWN",
                Instant.now()
        );
        incidentEventConsumer.onEvent(incidentCreated);

        verify(notificationSender).send(incidentCreated);
    }
}
