-- PostgreSQL: training_content_notes table for afefe-trainee programme notes.
-- Run once per environment: psql "$DATABASE_URL" -v ON_ERROR_STOP=1 -f add_training_content_notes.sql

CREATE TABLE IF NOT EXISTS training_content_notes (
    id                  UUID PRIMARY KEY,
    guid                VARCHAR(64) NOT NULL,
    created_at          TIMESTAMPTZ NOT NULL,
    updated_at          TIMESTAMPTZ NOT NULL,
    org_id              UUID NOT NULL,
    user_id             UUID NOT NULL,
    content_item_id     UUID,
    object_type         VARCHAR(40) NOT NULL,
    object_id           VARCHAR(36) NOT NULL,
    body                TEXT NOT NULL,
    asset_progress      NUMERIC(5, 2) DEFAULT 0,
    asset_progress_time VARCHAR(255)
);

CREATE INDEX IF NOT EXISTS idx_training_content_notes_user_org
    ON training_content_notes (user_id, org_id);

CREATE INDEX IF NOT EXISTS idx_training_content_notes_object
    ON training_content_notes (user_id, org_id, object_id);
