package com.betkowski.notificationservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class IncidentEventConsumer {
    private final NotificationSender notificationSender;

    public IncidentEventConsumer(NotificationSender notificationSender) {
        this.notificationSender = notificationSender;
    }

    @KafkaListener(topics = "${app.kafka.incidents-topic}", groupId = "notification-service")
    public void onEvent(IncidentCreated event) {
        notificationSender.send(event);
    }
}
