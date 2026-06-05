-- PostgreSQL patch: curriculum grades + single VIDEO block + READING body + quiz links
-- Safe to re-run on staging after fe-demo seed.
-- Run: psql "$DATABASE_URL" -v ON_ERROR_STOP=1 -f seed_curriculum_grades_patch.sql

BEGIN;

DO $$
DECLARE
    v_now timestamptz := now();
    v_tid uuid;
    v_iid uuid;
    v_bid uuid;
    v_qid uuid;
    v_q_idx int;
    v_fmt text;
    v_blk_type text;
    bl_idx int;
    n_blocks int;
    it_idx int;
BEGIN
    FOR v_tid IN
        SELECT tr.id FROM trainings tr
        WHERE tr.slug LIKE 'fe-demo-training-%' AND tr.status = 'PUBLISHED'
    LOOP
        -- Trim extra VIDEO_EMBED blocks on VIDEO items (keep lowest sort_order)
        FOR v_iid IN
            SELECT ci.id FROM training_content_items ci
            JOIN training_weeks w ON w.id = ci.week_id
            JOIN training_months m ON m.id = w.month_id
            WHERE m.training_id = v_tid AND ci.item_format = 'VIDEO'
        LOOP
            DELETE FROM training_content_blocks b
            WHERE b.content_item_id = v_iid
              AND b.block_type = 'VIDEO_EMBED'
              AND b.id NOT IN (
                  SELECT id FROM training_content_blocks
                  WHERE content_item_id = v_iid AND block_type = 'VIDEO_EMBED'
                  ORDER BY sort_order LIMIT 1
              );
            IF NOT EXISTS (
                SELECT 1 FROM training_content_blocks b
                WHERE b.content_item_id = v_iid AND b.block_type = 'VIDEO_EMBED'
            ) THEN
                v_bid := gen_random_uuid();
                INSERT INTO training_content_blocks (
                    id, guid, created_at, updated_at, content_item_id, sort_order, block_type,
                    payload_json, resource_url, trainee_quiz_id, estimated_duration_seconds
                ) VALUES (
                    v_bid, replace(v_bid::text, '-', ''), v_now, v_now, v_iid, 1, 'VIDEO_EMBED', '{}',
                    'https://videos.example.com/fe/patch-' || replace(v_iid::text, '-', '') || '.m3u8',
                    NULL, 300
                );
            END IF;
        END LOOP;

        -- Ensure READING items have payloadJson.body on READING blocks
        UPDATE training_content_blocks b
        SET payload_json = '{"body":"<p>Patched reading content.</p>"}', updated_at = v_now
        FROM training_content_items ci
        JOIN training_weeks w ON w.id = ci.week_id
        JOIN training_months m ON m.id = w.month_id
        WHERE b.content_item_id = ci.id
          AND m.training_id = v_tid
          AND ci.item_format = 'READING'
          AND b.block_type = 'READING'
          AND (b.payload_json IS NULL OR b.payload_json NOT LIKE '%"body"%');

        -- Ensure PRACTICE_QUIZ items have a PRACTICE_QUIZ block
        FOR v_iid IN
            SELECT ci.id, ci.position FROM training_content_items ci
            JOIN training_weeks w ON w.id = ci.week_id
            JOIN training_months m ON m.id = w.month_id
            WHERE m.training_id = v_tid AND ci.item_format = 'PRACTICE_QUIZ'
        LOOP
            IF NOT EXISTS (
                SELECT 1 FROM training_content_blocks b
                WHERE b.content_item_id = v_iid AND b.block_type = 'PRACTICE_QUIZ'
            ) THEN
                v_bid := gen_random_uuid();
                INSERT INTO training_content_blocks (
                    id, guid, created_at, updated_at, content_item_id, sort_order, block_type,
                    payload_json, resource_url, trainee_quiz_id, estimated_duration_seconds
                ) VALUES (
                    v_bid, replace(v_bid::text, '-', ''), v_now, v_now, v_iid, 1, 'PRACTICE_QUIZ',
                    '{}', NULL, NULL, 600
                );
            END IF;
        END LOOP;

        -- Link PRACTICE_QUIZ blocks to quizzes
        v_q_idx := 0;
        FOR v_iid IN
            SELECT ci.id FROM training_content_items ci
            JOIN training_weeks w ON w.id = ci.week_id
            JOIN training_months m ON m.id = w.month_id
            WHERE m.training_id = v_tid AND ci.item_format = 'PRACTICE_QUIZ'
            ORDER BY m.position, w.position, ci.position
        LOOP
            SELECT q.id INTO v_qid FROM trainee_quizzes q
            WHERE q.training_id = v_tid ORDER BY q.created_at LIMIT 1 OFFSET (v_q_idx % GREATEST((
                SELECT count(*)::int FROM trainee_quizzes WHERE training_id = v_tid
            ), 1));
            IF v_qid IS NOT NULL THEN
                UPDATE training_content_blocks SET trainee_quiz_id = v_qid, updated_at = v_now
                WHERE content_item_id = v_iid AND block_type = 'PRACTICE_QUIZ';
            END IF;
            v_q_idx := v_q_idx + 1;
        END LOOP;
    END LOOP;
END $$;

COMMIT;
