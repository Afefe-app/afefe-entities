-- Trainee attendance per scheduled class (CalendarEvent). Run against your DB when deploying.

ALTER TABLE calendar_event
    ADD COLUMN IF NOT EXISTS attendance_token_hash VARCHAR(64);

ALTER TABLE calendar_event
    ADD COLUMN IF NOT EXISTS attendance_opens_minutes_before INTEGER;

ALTER TABLE calendar_event
    ADD COLUMN IF NOT EXISTS attendance_requires_live_location BOOLEAN NOT NULL DEFAULT FALSE;

ALTER TABLE calendar_event
    ADD COLUMN IF NOT EXISTS session_latitude DECIMAL(10, 7);

ALTER TABLE calendar_event
    ADD COLUMN IF NOT EXISTS session_longitude DECIMAL(10, 7);

ALTER TABLE calendar_event
    ADD COLUMN IF NOT EXISTS attendance_geofence_radius_meters INTEGER;

CREATE TABLE IF NOT EXISTS training_session_attendance (
    id                     UUID PRIMARY KEY,
    guid                   VARCHAR(255) NOT NULL,
    created_at             TIMESTAMP    NOT NULL,
    updated_at             TIMESTAMP    NOT NULL,
    org_id                 UUID         NOT NULL,
    training_enrollment_id UUID         NOT NULL,
    calendar_event_id      UUID         NOT NULL,
    status                 VARCHAR(40)  NOT NULL,
    marked_at              TIMESTAMP    NOT NULL,
    live_location_used     BOOLEAN      NOT NULL DEFAULT FALSE,
    submitted_latitude     DECIMAL(10, 7),
    submitted_longitude    DECIMAL(10, 7),
    CONSTRAINT uk_training_session_attendance_enrollment_event UNIQUE (training_enrollment_id, calendar_event_id)
);

CREATE INDEX IF NOT EXISTS idx_training_session_attendance_enrollment ON training_session_attendance (training_enrollment_id);
