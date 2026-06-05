-- =============================================================================
-- Training certificates for fe.trainee.alt1@afefe.test (all current enrollments)
-- =============================================================================
-- Run:
--   psql -h HOST -p PORT -U app_mgt_user -d afefe_mgt -v ON_ERROR_STOP=1 \
--     -f seed_training_certificates_fe_trainee_alt1.sql
-- =============================================================================

BEGIN;

DO $$
DECLARE
    v_trainee_id uuid := 'c0fe0000-0000-4000-8000-000000000022'::uuid;
    v_now          timestamptz := now();
    v_enr          record;
    v_cert_id      uuid;
    v_idx          int := 0;
BEGIN
    FOR v_enr IN
        SELECT e.id AS enrollment_id,
               e.org_id,
               e.training_id,
               e.user_id,
               t.title,
               t.slug
        FROM training_enrollments e
        JOIN trainings t ON t.id = e.training_id
        WHERE e.user_id = v_trainee_id
        ORDER BY t.slug
    LOOP
        IF NOT EXISTS (
            SELECT 1 FROM training_certificates c
            WHERE c.user_id = v_enr.user_id AND c.training_id = v_enr.training_id
        ) THEN
            v_idx := v_idx + 1;
            v_cert_id := gen_random_uuid();
            INSERT INTO training_certificates (
                id, guid, created_at, updated_at, org_id, user_id, training_id,
                enrollment_id, issued_at, certificate_url, file_name, file_size_bytes
            ) VALUES (
                v_cert_id,
                replace(v_cert_id::text, '-', ''),
                v_now,
                v_now,
                v_enr.org_id,
                v_enr.user_id,
                v_enr.training_id,
                v_enr.enrollment_id,
                v_now - (v_idx || ' days')::interval,
                'https://example.com/certs/fe-alt1-' || v_enr.slug || '.pdf',
                'fe-alt1-' || v_enr.slug || '.pdf',
                245760
            );
        END IF;
    END LOOP;

    RAISE NOTICE 'Certificates for trainee %: %', v_trainee_id, (
        SELECT COUNT(*) FROM training_certificates WHERE user_id = v_trainee_id
    );
END $$;

COMMIT;
