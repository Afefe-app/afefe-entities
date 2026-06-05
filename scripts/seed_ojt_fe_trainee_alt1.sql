-- =============================================================================
-- OJT session logs for fe.trainee.alt1@afefe.test on fe-demo-training-01
-- Safe to re-run (fixed row ids + ON CONFLICT).
--
-- Run:
--   psql "$DATABASE_URL" -v ON_ERROR_STOP=1 -f seed_ojt_fe_trainee_alt1.sql
-- =============================================================================

BEGIN;

DO $$
DECLARE
    v_trainee_u2      uuid := 'c0fe0000-0000-4000-8000-000000000022'::uuid;
    v_trainer_u1      uuid := 'c0fe0000-0000-4000-8000-000000000011'::uuid;
    v_trainer_u2      uuid := 'c0fe0000-0000-4000-8000-000000000012'::uuid;
    v_now             timestamptz := now();
    v_today           date := CURRENT_DATE;
    v_org_id          uuid;
    v_tid             uuid;
    v_enr             uuid;
    v_ojt_log_1       uuid := 'c0fe0000-0000-4000-8000-000000000521'::uuid;
    v_ojt_log_2       uuid := 'c0fe0000-0000-4000-8000-000000000522'::uuid;
    v_ojt_log_3       uuid := 'c0fe0000-0000-4000-8000-000000000523'::uuid;
BEGIN
    SELECT o.id INTO v_org_id
    FROM users u
    JOIN org_members om ON om.user_id = u.id
    JOIN organization o ON o.id = om.organization_id
    WHERE lower(u.email_address) = 'fe.trainee.alt1@afefe.test'
    ORDER BY o.created_at NULLS LAST
    LIMIT 1;

    IF v_org_id IS NULL THEN
        RAISE EXCEPTION 'fe.trainee.alt1@afefe.test org membership not found';
    END IF;

    SELECT tr.id INTO v_tid
    FROM trainings tr
    WHERE tr.org_id = v_org_id AND tr.slug = 'fe-demo-training-01'
    LIMIT 1;

    IF v_tid IS NULL THEN
        RAISE EXCEPTION 'fe-demo-training-01 not found for trainee org %', v_org_id;
    END IF;

    UPDATE trainings
    SET ojt_duration_days = COALESCE(ojt_duration_days, 90),
        min_ojt_hours = COALESCE(min_ojt_hours, 120),
        updated_at = v_now
    WHERE id = v_tid;

    SELECT e.id INTO v_enr
    FROM training_enrollments e
    WHERE e.user_id = v_trainee_u2 AND e.training_id = v_tid
    LIMIT 1;

    IF v_enr IS NULL THEN
        RAISE EXCEPTION 'No training enrollment for alt1 on fe-demo-training-01';
    END IF;

    INSERT INTO trainee_ojt_session_log (
        id, guid, created_at, updated_at, org_id, training_enrollment_id,
        supervisor_user_id, supervisor_name, session_location, session_date,
        duration_hours, description, live_location_used, submitted_latitude, submitted_longitude,
        supporting_documents_json, display_session_id
    ) VALUES
        (v_ojt_log_1, replace(v_ojt_log_1::text, '-', ''), v_now - interval '20 days', v_now, v_org_id, v_enr,
         v_trainer_u1, 'FE Trainer 1', 'Ocean Academy — Workshop floor', v_today - 20,
         7, 'Orientation week: shadowed onboarding and documentation workflows.', false, NULL, NULL,
         '[{"fileName":"alt1-onboarding-notes.pdf","fileUrl":"https://res.cloudinary.com/demo/raw/upload/v1/fe/alt1-onboarding-notes.pdf","fileSizeBytes":204800,"contentType":"application/pdf"}]',
         'STU-00521'),
        (v_ojt_log_2, replace(v_ojt_log_2::text, '-', ''), v_now - interval '13 days', v_now, v_org_id, v_enr,
         v_trainer_u2, 'FE Trainer 2', 'Ocean Academy — Room B', v_today - 13,
         5, 'Practiced team stand-ups and received feedback from the lead trainer.', false, NULL, NULL, NULL,
         'STU-00522'),
        (v_ojt_log_3, replace(v_ojt_log_3::text, '-', ''), v_now - interval '6 days', v_now, v_org_id, v_enr,
         v_trainer_u2, 'FE Trainer 2', 'Lagos HQ — Operations desk', v_today - 6,
         4, 'Supported live operations and logged daily KPI observations.', true, 6.5244000, 3.3792000, NULL,
         'STU-00523')
    ON CONFLICT (id) DO UPDATE SET
        training_enrollment_id = EXCLUDED.training_enrollment_id,
        session_date = EXCLUDED.session_date,
        duration_hours = EXCLUDED.duration_hours,
        description = EXCLUDED.description,
        updated_at = EXCLUDED.updated_at;

    RAISE NOTICE 'OJT logs seeded for alt1 enrollment %', v_enr;
END $$;

COMMIT;

SELECT l.display_session_id, l.session_date, l.duration_hours, u.email_address
FROM trainee_ojt_session_log l
JOIN training_enrollments e ON e.id = l.training_enrollment_id
JOIN users u ON u.id = e.user_id
WHERE lower(u.email_address) = 'fe.trainee.alt1@afefe.test'
ORDER BY l.session_date;
