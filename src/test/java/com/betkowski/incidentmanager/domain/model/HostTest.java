package com.betkowski.incidentmanager.domain.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class HostTest {

    private Host host;

    @BeforeEach
    void setUp() {
        host = Host.create("prod-api-01", "10.10.25.50");
    }

    @Test
    void shouldCreateHostWithDefaultValues() {
        assertNotNull(host.getId());
        assertEquals("prod-api-01", host.getName());
        assertEquals("10.10.25.50", host.getAddress());
        assertEquals(HostStatus.ACTIVE, host.getStatus());
        assertNotNull(host.getCreatedAt());
    }

    @Test
    void shouldRejectBlankName() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class, () -> Host.create("", "10.10.25.1"));

        assertEquals("Host name cannot be blank", exception.getMessage());
    }

    @Test
    void shouldRejectBlankAddress() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class, () -> Host.create("SP01", "")
        );
        assertEquals("Host address cannot be blank", exception.getMessage());
    }

    @Test
    void shouldActivateHost() {
        host.deactivate();
        host.activate();

        assertEquals(HostStatus.ACTIVE, host.getStatus());
    }

    @Test
    void shouldDeactivateHost() {
        host.deactivate();

        assertEquals(HostStatus.INACTIVE, host.getStatus());
    }

    @Test
    void shouldReturnTrueWhenHostIsActive() {
        assertTrue(host.isActive());
    }

    @Test
    void shouldReturnFalseWhenHostIsDeactivate() {
        host.deactivate();
        assertFalse(host.isActive());
    }
}
