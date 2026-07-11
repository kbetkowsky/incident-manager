package com.betkowski.incidentmanager.domain.model;

import java.util.Objects;
import java.util.UUID;

public class EscalationRule {
    private final UUID id;
    private final EventType eventType;
    private final int thresholdCount;
    private final int timeWindowMinutes;

    private EscalationRule(UUID id, EventType eventType, int thresholdCount, int timeWindowMinutes) {
        this.id = Objects.requireNonNull(id, "Id cannot be null");
        this.eventType = Objects.requireNonNull(eventType, "Event Type cannot be null");
        this.thresholdCount = validatePositive(thresholdCount, "Threshold count must be positive");
        this.timeWindowMinutes = validatePositive(timeWindowMinutes, "Minutes count must be positive");
    }

    public static EscalationRule create(
            EventType eventType, int thresholdCount, int timeWindowMinutes
    ) {
        return new EscalationRule(UUID.randomUUID(), eventType, thresholdCount, timeWindowMinutes);
    }

    public static EscalationRule restore(
            UUID id, EventType eventType, int thresholdCount, int timeWindowMinutes
    ) {
        return new EscalationRule(id, eventType, thresholdCount, timeWindowMinutes);
    }

    public UUID getId() {
        return id;
    }

    public EventType getEventType() {
        return eventType;
    }

    public int getThresholdCount() {
        return thresholdCount;
    }

    public int getTimeWindowMinutes() {
        return timeWindowMinutes;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        EscalationRule that = (EscalationRule) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    private static int validatePositive(int value, String message) {
        if (value <= 0) {
            throw new IllegalArgumentException(message);
        }
        return value;
    }
}
