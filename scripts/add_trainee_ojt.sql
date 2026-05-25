-- Trainee self-service OJT session logs + per-programme milestones.
-- Run once when deploying afefe-trainee OJT APIs.

ALTER TABLE trainings
    ADD COLUMN IF NOT EXISTS ojt_duration_days INTEGER;

ALTER TABLE trainings
    ADD COLUMN IF NOT EXISTS min_ojt_hours INTEGER;

CREATE TABLE IF NOT EXISTS training_ojt_milestone (
    id             UUID PRIMARY KEY,
    guid           VARCHAR(255) NOT NULL,
    created_at     TIMESTAMP    NOT NULL,
    updated_at     TIMESTAMP    NOT NULL,
    training_id    UUID         NOT NULL,
    position       INTEGER      NOT NULL,
    title          VARCHAR(512) NOT NULL,
    offset_days    INTEGER      NOT NULL,
    milestone_type VARCHAR(40)  NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_training_ojt_milestone_training
    ON training_ojt_milestone (training_id, position);

CREATE TABLE IF NOT EXISTS trainee_ojt_session_log (
    id                       UUID PRIMARY KEY,
    guid                     VARCHAR(255) NOT NULL,
    created_at               TIMESTAMP    NOT NULL,
    updated_at               TIMESTAMP    NOT NULL,
    org_id                   UUID         NOT NULL,
    training_enrollment_id   UUID         NOT NULL,
    supervisor_user_id       UUID,
    supervisor_name          VARCHAR(255) NOT NULL,
    session_location         VARCHAR(1024) NOT NULL,
    session_date             DATE         NOT NULL,
    duration_hours           INTEGER      NOT NULL,
    description              TEXT         NOT NULL,
    live_location_used       BOOLEAN      NOT NULL DEFAULT FALSE,
    submitted_latitude       DECIMAL(10, 7),
    submitted_longitude      DECIMAL(10, 7),
    supporting_documents_json TEXT,
    display_session_id       VARCHAR(32)  NOT NULL,
    CONSTRAINT uk_trainee_ojt_session_log_display_id UNIQUE (display_session_id)
);

CREATE INDEX IF NOT EXISTS idx_trainee_ojt_session_log_enrollment
    ON trainee_ojt_session_log (training_enrollment_id, session_date DESC);
