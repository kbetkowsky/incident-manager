package com.betkowski.incidentmanager.domain.model;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

public class IncidentTest {

    private Incident incident;
    UUID incidentId = UUID.fromString("0da60571-10a5-491e-8857-5773d0d7c450");
    private Incident restoreIncident() {
        Instant now = Instant.now();
        return Incident.restore(
                UUID.randomUUID(),
                UUID.randomUUID(),
                "Router-01",
                EventType.UNRESPONSIVE,
                IncidentStatus.OPEN,
                1,
                now,
                now
        );
    }

    @BeforeEach
    public void setUp() {
        incident = Incident.create(incidentId, "PR01", EventType.UNRESPONSIVE);
    }

    @Test
    public void shouldCreateIncidentWithValidData() {
        assertNotNull(incident.getId());
        assertEquals("PR01", incident.getDeviceName());
        assertEquals(EventType.UNRESPONSIVE, incident.getEventType());
        assertNotNull(incident.getFirstOccurredAt());
    }

    @Test
    public void shouldRejectBlankName() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class, () -> Incident.create(incidentId, "",
                        EventType.UNRESPONSIVE)
        );

        assertEquals("Device name cannot be blank", exception.getMessage());
    }

    @Test
    public void shouldAcknowledge_whenStatusIsOpen() {
        incident.acknowledge();

        assertEquals(IncidentStatus.ACKNOWLEDGED, incident.getStatus());
    }

    @Test
    public void shouldNotBeAcknowledge_whenStatusIsAcknowledge() {
        Instant now = Instant.now();
        Incident incident1 = Incident.restore(UUID.randomUUID(),
                UUID.randomUUID(),
                "Router-01",
                EventType.UNRESPONSIVE,
                IncidentStatus.ACKNOWLEDGED,
                1,
                now,
                now);

        assertThrows(IllegalStateException.class, incident1::acknowledge);
    }

    @Test
    public void shouldNotBeAcknowledged_whenStatusIsResolve() {
        Instant now = Instant.now();
        Incident incident1 = Incident.restore(UUID.randomUUID(),
                UUID.randomUUID(),
                "Router-01",
                EventType.UNRESPONSIVE,
                IncidentStatus.RESOLVED,
                1,
                now,
                now);

        assertThrows(IllegalStateException.class, incident1::acknowledge);
    }

    @Test
    public void shouldNotGetResolveStatus_whenStatusIsResolve() {
        Instant now = Instant.now();
        Incident incident1 = Incident.restore(UUID.randomUUID(),
                UUID.randomUUID(),
                "Router-01",
                EventType.UNRESPONSIVE,
                IncidentStatus.RESOLVED,
                1,
                now,
                now);
        assertThrows(IllegalStateException.class, incident1::resolve);
    }

    @Test
    public void shouldIncreaseOccurrenceCountAndUpdateLastOccurredAt() {
        Instant before = incident.getLastOccurredAt();

        incident.recordOccurrence();

        assertEquals(2, incident.getOccurrenceCount());
        assertTrue(incident.getLastOccurredAt().isAfter(before) || incident.getLastOccurredAt().equals(before));

    }

    @Test
    void shouldBeEqual_whenIdsMatchEvenIsEvenTypeDiffers() {
        Instant now = Instant.now();
        UUID sameId = UUID.randomUUID();
        Incident first = Incident.restore(sameId,
                UUID.randomUUID(),
                "Router-01",
                EventType.UNRESPONSIVE,
                IncidentStatus.RESOLVED,
                1,
                now,
                now);
        Incident second = Incident.restore(sameId,
                UUID.randomUUID(),
                "Router-01",
                EventType.BACK_ONLINE,
                IncidentStatus.RESOLVED,
                1,
                now,
                now);

        assertEquals(first, second);
        assertEquals(first.hashCode(), second.hashCode());
    }

    @Test
    void shouldNotBeEqual_whenIdsIsDiffers() {
        Incident first = Incident.create(UUID.randomUUID(), "PR01", EventType.UNRESPONSIVE);
        Incident second = Incident.create(UUID.randomUUID(), "PR01", EventType.UNRESPONSIVE);

        assertNotEquals(first, second);
    }

}
