package com.betkowski.incidentmanager.adapters.in.web;

import com.betkowski.incidentmanager.domain.model.EventType;

public record EventRequest(
        EventType eventType
) {

}
