package com.betkowski.incidentmanager.application.exceptions;

public class DeviceNotFoundException extends RuntimeException {
    public DeviceNotFoundException(String message) {
        super(message);
    }
}
