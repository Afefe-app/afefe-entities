-- =============================================================================
-- Training Management — tracks (cohorts) under published programmes
-- =============================================================================
-- Adds:
--   training_tracks
--   training_track_courses
--   training_track_trainers
--   training_enrollments.track_id (nullable for legacy rows)
--
-- Safe to re-run (IF NOT EXISTS / ADD COLUMN IF NOT EXISTS).
-- =============================================================================

BEGIN;

CREATE TABLE IF NOT EXISTS training_tracks (
    id              uuid PRIMARY KEY,
    guid            varchar(64),
    created_at      timestamptz,
    updated_at      timestamptz,
    org_id          uuid         NOT NULL,
    training_id     uuid         NOT NULL,
    name            varchar(255) NOT NULL,
    code            varchar(32),
    cover_image_url text,
    start_date      date         NOT NULL,
    end_date        date         NOT NULL,
    status          varchar(40)  NOT NULL DEFAULT 'ACTIVE',
    created_by      uuid,
    updated_by      uuid,
    CONSTRAINT uk_training_tracks_training_name UNIQUE (training_id, name)
);

CREATE INDEX IF NOT EXISTS idx_training_tracks_org_training
    ON training_tracks (org_id, training_id);

CREATE INDEX IF NOT EXISTS idx_training_tracks_status
    ON training_tracks (status);

CREATE TABLE IF NOT EXISTS training_track_courses (
    id          uuid PRIMARY KEY,
    guid        varchar(64),
    created_at  timestamptz,
    updated_at  timestamptz,
    org_id      uuid NOT NULL,
    track_id    uuid NOT NULL,
    course_id   uuid NOT NULL,
    position    int  NOT NULL DEFAULT 0,
    CONSTRAINT uk_training_track_courses_track_course UNIQUE (track_id, course_id)
);

CREATE INDEX IF NOT EXISTS idx_training_track_courses_track
    ON training_track_courses (track_id);

CREATE TABLE IF NOT EXISTS training_track_trainers (
    id          uuid PRIMARY KEY,
    guid        varchar(64),
    created_at  timestamptz,
    updated_at  timestamptz,
    org_id      uuid NOT NULL,
    track_id    uuid NOT NULL,
    user_id     uuid NOT NULL,
    trainer_id  uuid,
    active      boolean NOT NULL DEFAULT true,
    CONSTRAINT uk_training_track_trainers_track_user UNIQUE (track_id, user_id)
);

CREATE INDEX IF NOT EXISTS idx_training_track_trainers_track
    ON training_track_trainers (track_id);

ALTER TABLE training_enrollments
    ADD COLUMN IF NOT EXISTS track_id uuid;

CREATE INDEX IF NOT EXISTS idx_training_enrollments_track
    ON training_enrollments (track_id);

ALTER TABLE course_ratings
    ADD COLUMN IF NOT EXISTS instructor_reply text;

ALTER TABLE course_ratings
    ADD COLUMN IF NOT EXISTS instructor_replied_at timestamptz;

COMMIT;
