CREATE TABLE incidents(
    id UUID PRIMARY KEY,
    device_id UUID NOT NULL,
    device_name VARCHAR(255) NOT NULL,
    event_type VARCHAR(255) NOT NULL,
    status VARCHAR(255) NOT NULL,
    occurrence_count INT NOT NULL,
    first_occurred_at TIMESTAMP NOT NULL,
    last_occurred_at TIMESTAMP NOT NULL,
    CONSTRAINT fk_incidents_device FOREIGN KEY (device_id) REFERENCES devices(id)
);