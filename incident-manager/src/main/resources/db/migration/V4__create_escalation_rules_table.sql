CREATE TABLE escalation_rules(
    id UUID PRIMARY KEY,
    event_type VARCHAR(255) NOT NULL,
    threshold_count INT NOT NULL,
    time_window_minutes INT NOT NULL
);

INSERT INTO escalation_rules(
id, event_type, threshold_count, time_window_minutes) VALUES (
    gen_random_uuid(),
    'UNRESPONSIVE',
    10,
    5
),
(
    gen_random_uuid(),
    'HIGH_CPU',
    5,
    5
);