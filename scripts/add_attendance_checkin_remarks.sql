-- Optional facilitator check-in / check-out / remarks on session attendance marks.

ALTER TABLE training_session_attendance
    ADD COLUMN IF NOT EXISTS check_in_time TIME;

ALTER TABLE training_session_attendance
    ADD COLUMN IF NOT EXISTS check_out_time TIME;

ALTER TABLE training_session_attendance
    ADD COLUMN IF NOT EXISTS remarks VARCHAR(1000);
