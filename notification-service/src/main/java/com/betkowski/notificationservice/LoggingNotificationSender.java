package com.betkowski.notificationservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class LoggingNotificationSender implements NotificationSender{
    private static final Logger log = LoggerFactory.getLogger(LoggingNotificationSender.class);
    @Override
    public void send(IncidentCreated event) {
        log.info("Would send notification for incident {} to {}", event.incidentId(), event.deviceName());
    }
}
