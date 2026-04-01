-- Run against PostgreSQL after deploying entity changes.
ALTER TABLE content_notes
    ADD COLUMN IF NOT EXISTS asset_progress NUMERIC(5, 2) NULL;

COMMENT ON COLUMN content_notes.asset_progress IS 'Optional snapshot of lesson-asset progress when the note was taken (e.g. percent 0–100).';
