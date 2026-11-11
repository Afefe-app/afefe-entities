-- =============================================================================
-- Training Management — delivery window on trainings
-- =============================================================================
-- Adds optional start_date / end_date used by create-training under a programme.
-- Safe to re-run (ADD COLUMN IF NOT EXISTS).
-- =============================================================================

BEGIN;

ALTER TABLE trainings
    ADD COLUMN IF NOT EXISTS start_date date;

ALTER TABLE trainings
    ADD COLUMN IF NOT EXISTS end_date date;

COMMIT;
