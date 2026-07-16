CREATE TABLE events(
    id UUID PRIMARY KEY,
    device_id UUID NOT NULL,
    device_name VARCHAR(255) NOT NULL,
    event_type VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    CONSTRAINT fk_events_device FOREIGN KEY (device_id) REFERENCES devices(id)
);