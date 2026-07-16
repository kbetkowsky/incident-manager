package com.betkowski.incidentmanager.domain.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;


public class EscalationRuleTest {

    private EscalationRule escalationRule;

    @BeforeEach
    void setUp() {
        escalationRule = EscalationRule.create(
                EventType.UNRESPONSIVE, 5, 10
        );
    }

    @Test
    void shouldCreate_whenDataIsValid() {
        assertNotNull(escalationRule.getId());
        assertEquals(EventType.UNRESPONSIVE, escalationRule.getEventType());
        assertEquals(5, escalationRule.getThresholdCount());
        assertEquals(10, escalationRule.getTimeWindowMinutes());
    }

    @Test
    void shouldThrowException_whenEventTypeIsNull() {
        NullPointerException exception =
                assertThrows(NullPointerException.class, () -> EscalationRule.create(
                null, 5, 10));

        assertEquals("Event Type cannot be null" , exception.getMessage());
    }

    @Test
    void shouldThrowException_whenThresholdCountIsLessThanZero() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> EscalationRule.create(EventType.UNRESPONSIVE, -1, 10));

        assertEquals("Threshold count must be positive", exception.getMessage());
    }

    @Test
    void shouldThrowException_whenTimeWindowMinutesIsLessThan0() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> EscalationRule.create(EventType.UNRESPONSIVE, 5, -1));

        assertEquals("Minutes count must be positive", exception.getMessage());
    }

    @Test
    void shouldBeEquals_whenSameId() {
        UUID sameId = UUID.randomUUID();
        EscalationRule first = EscalationRule.restore(sameId, EventType.UNRESPONSIVE, 5, 10);
        EscalationRule second = EscalationRule.restore(sameId, EventType.HIGH_CPU, 1, 2);

        assertEquals(first, second);
    }

    @Test
    void shouldNotBeEqual_whenIdIsNotTheSame() {
        EscalationRule first = EscalationRule.restore(UUID.randomUUID(), EventType.UNRESPONSIVE, 5, 10);
        EscalationRule second = EscalationRule.restore(UUID.randomUUID(), EventType.UNRESPONSIVE, 5, 10);

        assertNotEquals(first, second);
    }
}
