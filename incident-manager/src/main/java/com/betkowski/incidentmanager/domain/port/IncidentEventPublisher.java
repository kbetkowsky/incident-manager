package com.betkowski.incidentmanager.domain.port;

import com.betkowski.incidentmanager.domain.event.IncidentCreated;

public interface IncidentEventPublisher {
    void publish(IncidentCreated incidentCreated);
}
