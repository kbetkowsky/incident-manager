package com.betkowski.notificationservice;

public interface NotificationSender {
    void send(IncidentCreated event);
}
