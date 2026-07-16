package com.betkowski.notificationservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class IncidentEventConsumer {
    private static final Logger log = LoggerFactory.getLogger(IncidentEventConsumer.class);

    @KafkaListener(topics = "${app.kafka.incidents-topic}", groupId = "notification-service")
    public void onEvent(IncidentCreated event) {
        log.info("Would send notification for incident {} to {}", event.incidentId(), event.deviceName());
    }
}
