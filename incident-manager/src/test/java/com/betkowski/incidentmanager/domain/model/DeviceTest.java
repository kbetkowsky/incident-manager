package com.betkowski.incidentmanager.domain.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;


public class DeviceTest {

    private Device device;

    @BeforeEach
    void setUp() {
        device = Device.create("prod-api-01", "10.10.25.50");
    }

    @Test
    void shouldCreateDeviceWithDefaultValues() {
        assertNotNull(device.getId());
        assertEquals("prod-api-01", device.getName());
        assertEquals("10.10.25.50", device.getAddress());
        assertEquals(DeviceStatus.ACTIVE, device.getStatus());
        assertNotNull(device.getCreatedAt());
    }

    @Test
    void shouldRejectBlankName() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class, () -> Device.create("", "10.10.25.1"));

        assertEquals("Device name cannot be blank", exception.getMessage());
    }

    @Test
    void shouldRejectBlankAddress() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class, () -> Device.create("SP01", "")
        );
        assertEquals("Device address cannot be blank", exception.getMessage());
    }

    @Test
    void shouldActivateDevice() {
        Device device1 = Device.restore(UUID.randomUUID(), "pr-01", "10.10.25.1", DeviceStatus.INACTIVE
                , Instant.now());
        device1.activate();

        assertEquals(DeviceStatus.ACTIVE, device1.getStatus());
    }

    @Test
    void shouldDeactivateDevice() {
        device.deactivate();

        assertEquals(DeviceStatus.INACTIVE, device.getStatus());
    }

    @Test
    void shouldReturnTrueWhenDeviceIsActive() {
        assertTrue(device.isActive());
    }

    @Test
    void shouldReturnFalseWhenDeviceIsDeactivate() {
        device.deactivate();
        assertFalse(device.isActive());
    }

    @Test
    void shouldEnterMaintenance_whenDeviceIsInActiveState() {
        device.enterMaintenance();

        assertEquals(DeviceStatus.MAINTENANCE, device.getStatus());
    }

    @Test
    void shouldBeActive_whenDeviceIsExitMaintenance() {
        Device device1 = Device.restore(UUID.randomUUID(), "pr-01", "10.10.25.1", DeviceStatus.MAINTENANCE
                , Instant.now());

        device1.exitMaintenance();

        assertEquals(DeviceStatus.ACTIVE, device1.getStatus());
    }

    @Test
    void shouldBeDeactivate_whenDeviceIsInMaintenance() {
        Device device1 = Device.restore(UUID.randomUUID(), "pr-01", "10.10.25.1", DeviceStatus.MAINTENANCE
                , Instant.now());

        device1.deactivate();

        assertEquals(DeviceStatus.INACTIVE, device1.getStatus());
    }

    @Test
    void shouldThrowExceptionFromMaintenanceState_whenActiveState() {
        Device device1 = Device.restore(UUID.randomUUID(), "pr-01", "10.10.25.1", DeviceStatus.MAINTENANCE
        , Instant.now());

        IllegalStateException exception = assertThrows(
                IllegalStateException.class, device1::activate);

        assertTrue(exception.getMessage().contains("MAINTENANCE"));
    }

    @Test
    void shouldThrowExceptionFromMActiveState_whenDeviceIsInActiveState() {
        Device device1 = Device.restore(UUID.randomUUID(), "pr-01", "10.10.25.1", DeviceStatus.ACTIVE
                , Instant.now());

        assertThrows(IllegalStateException.class, device1::activate);
    }

    @Test
    void shouldThrowExceptionFromInactive_whenDeviceIsInInactiveState() {
        Device device1 = Device.restore(UUID.randomUUID(), "pr-01", "10.10.25.1", DeviceStatus.INACTIVE
                , Instant.now());

        IllegalStateException exception = assertThrows(
                IllegalStateException.class, device1::deactivate
        );

        assertTrue(exception.getMessage().contains("INACTIVE"));
    }

    @Test
    void shouldThrowExceptionFromMaintenance_whenDeviceIsAlreadyInMaintenance() {
        Device device1 = Device.restore(UUID.randomUUID(), "pr-01", "10.10.25.1", DeviceStatus.MAINTENANCE
                , Instant.now());

        assertThrows(IllegalStateException.class, device1::enterMaintenance);
    }

    @Test
    void shouldThrowExceptionFromExitMaintenance_whenDeviceHasDifferentStateThanMaintenance() {
        Device device1 = Device.restore(UUID.randomUUID(), "pr-01", "10.10.25.1", DeviceStatus.ACTIVE
                , Instant.now());
        Device device2 = Device.restore(UUID.randomUUID(), "pr-02", "10.10.25.2", DeviceStatus.INACTIVE
                , Instant.now());

        IllegalStateException exception = assertThrows(
                IllegalStateException.class, device1::exitMaintenance
        );

        IllegalStateException exception1 = assertThrows(
                IllegalStateException.class, device2::exitMaintenance
        );

        assertTrue(exception.getMessage().contains("MAINTENANCE"));
        assertTrue(exception1.getMessage().contains("MAINTENANCE"));
    }

    @Test
    void shouldBeEqualWhenIdsMatchEvenIsStatusDiffers() {
        UUID sameId = UUID.randomUUID();
        Device first = Device.restore(sameId, "pr-01", "10.10.25.50", DeviceStatus.ACTIVE, Instant.now());
        Device second = Device.restore(sameId, "pr-01", "10.10.25.50", DeviceStatus.INACTIVE, Instant.now());

        assertEquals(first, second);
        assertEquals(first.hashCode(), second.hashCode());
    }

    @Test
    void shouldNotBeEqualWhenIdsDiffer() {
        Device first = Device.restore(UUID.randomUUID(), "pr-01", "10.10.25.50", DeviceStatus.ACTIVE, Instant.now());
        Device second = Device.restore(UUID.randomUUID(), "pr-01", "10.10.25.50", DeviceStatus.ACTIVE, Instant.now());

        assertNotEquals(first, second);
    }
}
