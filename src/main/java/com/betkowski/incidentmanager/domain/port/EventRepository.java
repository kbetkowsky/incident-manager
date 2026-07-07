package com.betkowski.incidentmanager.domain.port;

import com.betkowski.incidentmanager.domain.model.Event;

public interface EventRepository {
    void save(Event event);
}
