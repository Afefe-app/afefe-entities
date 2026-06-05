-- PostgreSQL: sample training_content_notes for fe.trainee.alt1@afefe.test on fe-demo-training-01
-- Run: psql "$DATABASE_URL" -v ON_ERROR_STOP=1 -f seed_training_content_notes_fe_trainee_alt1.sql

BEGIN;

DO $$
DECLARE
    v_org_id      uuid;
    v_trainee_u2  uuid;
    v_tid         uuid;
    v_iid         uuid;
    v_bid         uuid;
    v_now         timestamptz := now();
BEGIN
    SELECT o.id INTO v_org_id
    FROM users u
    JOIN org_members om ON om.user_id = u.id
    JOIN organization o ON o.id = om.organization_id
    WHERE lower(u.email_address) = 'fe.trainee.alt1@afefe.test'
    ORDER BY o.created_at NULLS LAST
    LIMIT 1;

    SELECT u.id INTO v_trainee_u2 FROM users u
    WHERE lower(u.email_address) = 'fe.trainee.alt1@afefe.test' LIMIT 1;

    SELECT tr.id INTO v_tid FROM trainings tr
    WHERE tr.org_id = v_org_id AND tr.slug = 'fe-demo-training-01' LIMIT 1;

    IF v_org_id IS NULL OR v_trainee_u2 IS NULL OR v_tid IS NULL THEN
        RAISE EXCEPTION 'Missing org, trainee, or fe-demo-training-01';
    END IF;

    FOR v_iid IN
        SELECT ci.id FROM training_content_items ci
        JOIN training_weeks w ON w.id = ci.week_id
        JOIN training_months m ON m.id = w.month_id
        WHERE m.training_id = v_tid
        ORDER BY m.position, w.position, ci.position LIMIT 3
    LOOP
        IF NOT EXISTS (
            SELECT 1 FROM training_content_notes n
            WHERE n.user_id = v_trainee_u2 AND n.object_id = v_iid::text AND n.object_type = 'CONTENT_ITEM'
        ) THEN
            INSERT INTO training_content_notes (
                id, guid, created_at, updated_at, org_id, user_id, content_item_id,
                object_type, object_id, body, asset_progress, asset_progress_time
            ) VALUES (
                gen_random_uuid(), replace(gen_random_uuid()::text, '-', ''), v_now, v_now,
                v_org_id, v_trainee_u2, v_iid, 'CONTENT_ITEM', v_iid::text,
                'Patched demo note on lesson ' || replace(v_iid::text, '-', ''), 0, NULL
            );
        END IF;
    END LOOP;

    SELECT b.id, b.content_item_id INTO v_bid, v_iid
    FROM training_content_blocks b
    JOIN training_content_items ci ON ci.id = b.content_item_id
    JOIN training_weeks w ON w.id = ci.week_id
    JOIN training_months m ON m.id = w.month_id
    WHERE m.training_id = v_tid AND b.block_type = 'VIDEO_EMBED'
    ORDER BY m.position, w.position, ci.position LIMIT 1;

    IF v_bid IS NOT NULL AND NOT EXISTS (
        SELECT 1 FROM training_content_notes n
        WHERE n.user_id = v_trainee_u2 AND n.object_id = v_bid::text AND n.object_type = 'BLOCK'
    ) THEN
        INSERT INTO training_content_notes (
            id, guid, created_at, updated_at, org_id, user_id, content_item_id,
            object_type, object_id, body, asset_progress, asset_progress_time
        ) VALUES (
            gen_random_uuid(), replace(gen_random_uuid()::text, '-', ''), v_now, v_now,
            v_org_id, v_trainee_u2, v_iid, 'BLOCK', v_bid::text,
            'Video checkpoint note — key concept recap', 42.50, '00:05:30'
        );
    END IF;
END $$;

COMMIT;
