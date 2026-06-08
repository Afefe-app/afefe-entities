-- =============================================================================
-- Afefe Trainee — FULL FE demo dataset (PostgreSQL)
-- =============================================================================
-- Seeds every afefe-trainee read screen in the super organization (role=OWNER).
--
-- Screens covered:
--   search / recommended / recently-viewed / overview / curriculum / lesson player
--   my learning (enrollments) / saved wishlist / ratings / quizzes + attempts
--   certificates / calendar upcoming / attendance history + mark flow
--   profile industry interests / OJT overview + session logs
--
-- Prerequisites:
--   CREATE EXTENSION IF NOT EXISTS pgcrypto;
--   Run afefe-entities/scripts/add_training_session_attendance.sql once if needed.
--   Run afefe-entities/scripts/add_trainee_ojt.sql once if needed.
--
-- Run:
--   psql "$DATABASE_URL" -v ON_ERROR_STOP=1 -f seed_trainee_fe_demo_data.sql
-- =============================================================================

BEGIN;

CREATE EXTENSION IF NOT EXISTS pgcrypto;

DO $$
DECLARE
    v_org_id          uuid;
    v_trainer_u1      uuid := 'c0fe0000-0000-4000-8000-000000000011'::uuid;
    v_trainer_u2      uuid := 'c0fe0000-0000-4000-8000-000000000012'::uuid;
    v_trainee_u1      uuid := 'c0fe0000-0000-4000-8000-000000000021'::uuid;
    v_trainee_u2      uuid := 'c0fe0000-0000-4000-8000-000000000022'::uuid;
    v_trainee_u3      uuid := 'c0fe0000-0000-4000-8000-000000000023'::uuid;
    v_pw              text := '$2a$10$YziBC/yc8tvpN6/tv/0YA.Y.T/9AFQx39NgWfwjBFakoWoXc/Oi0.';
    v_now             timestamptz := now();
    v_today           date := CURRENT_DATE;

    v_tid             uuid;
    v_mid             uuid;
    v_wid             uuid;
    v_iid             uuid;
    v_bid             uuid;
    v_qid             uuid;
    v_qn_id           uuid;
    v_opt_correct     uuid;
    v_enr             uuid;
    v_trainee_uid     uuid;
    v_ce_id           uuid;
    v_att_id          uuid;
    v_token_hash      text;
    v_attendance_token text := 'FE-DEMO-ATTEND-TOKEN';

    t_idx             int;
    m_idx             int;
    w_idx             int;
    it_idx            int;
    bl_idx            int;
    q_idx             int;
    n_months          int;
    n_weeks           int;
    n_items           int;
    n_blocks          int;
    v_status          text;
    v_trainer_row     uuid;
    v_trainee_row     uuid;
    v_profile_id      uuid;
    v_slug            text;
    v_title           text;
    v_ind_id          uuid;
    u_id              uuid;
    tr_idx            int;
    te_idx            int;
    r_idx             int;
    v_fmt             text;
    v_blk_type        text;
    v_prog_st         text;

    v_ce_past_present uuid := 'c0fe0000-0000-4000-8000-000000000301'::uuid;
    v_ce_past_late    uuid := 'c0fe0000-0000-4000-8000-000000000302'::uuid;
    v_ce_upcoming_1   uuid := 'c0fe0000-0000-4000-8000-000000000311'::uuid;
    v_ce_upcoming_2   uuid := 'c0fe0000-0000-4000-8000-000000000312'::uuid;
    v_ce_upcoming_3   uuid := 'c0fe0000-0000-4000-8000-000000000313'::uuid;
    v_att_present_id  uuid := 'c0fe0000-0000-4000-8000-000000000401'::uuid;
    v_att_late_id     uuid := 'c0fe0000-0000-4000-8000-000000000402'::uuid;

    v_ojt_ms_start    uuid := 'c0fe0000-0000-4000-8000-000000000501'::uuid;
    v_ojt_ms_week4    uuid := 'c0fe0000-0000-4000-8000-000000000502'::uuid;
    v_ojt_ms_complete uuid := 'c0fe0000-0000-4000-8000-000000000503'::uuid;
    v_ojt_log_1       uuid := 'c0fe0000-0000-4000-8000-000000000511'::uuid;
    v_ojt_log_2       uuid := 'c0fe0000-0000-4000-8000-000000000512'::uuid;
    v_ojt_log_3       uuid := 'c0fe0000-0000-4000-8000-000000000513'::uuid;
    v_ojt_log_alt1_1  uuid := 'c0fe0000-0000-4000-8000-000000000521'::uuid;
    v_ojt_log_alt1_2  uuid := 'c0fe0000-0000-4000-8000-000000000522'::uuid;
    v_ojt_log_alt1_3  uuid := 'c0fe0000-0000-4000-8000-000000000523'::uuid;
    v_ojt_display_1   text := 'STU-00511';
    v_ojt_display_2   text := 'STU-00512';
    v_ojt_display_3   text := 'STU-00513';
    v_ojt_display_alt1_1 text := 'STU-00521';
    v_ojt_display_alt1_2 text := 'STU-00522';
    v_ojt_display_alt1_3 text := 'STU-00523';
BEGIN
    -- ---- Super organization (trainee IAM tenant) ---------------------------
    SELECT id INTO v_org_id
    FROM organization
    WHERE role = 'OWNER'
    ORDER BY created_at NULLS LAST, id
    LIMIT 1;

    IF v_org_id IS NULL THEN
        v_org_id := 'd6c8e3bd-64a8-4c5a-85dd-4bd6cdb7f44d'::uuid;
        INSERT INTO organization (id, guid, created_at, updated_at, name, slug, status, plan_tier, role)
        VALUES (v_org_id, replace(v_org_id::text, '-', ''), v_now, v_now,
                'Ocean', 'org-seed-owner', 'ACTIVE', 'TIER3', 'OWNER')
        ON CONFLICT (id) DO NOTHING;
    END IF;

    -- ---- Users -------------------------------------------------------------
    INSERT INTO users (id, guid, created_at, updated_at, email_address, full_name, password_hash, user_type, country, is_active, last_login_at)
    SELECT s.id, s.guid, s.created_at, s.updated_at, s.email_address, s.full_name, s.password_hash, s.user_type, s.country, s.is_active, s.last_login_at
    FROM (VALUES
        (v_trainer_u1, replace(v_trainer_u1::text, '-', ''), v_now, v_now, 'fe.trainer.demo@afefe.test', 'FE Demo Trainer One', v_pw, 'PLATFORM_TRAINER', 'NIGERIA', true, v_now),
        (v_trainer_u2, replace(v_trainer_u2::text, '-', ''), v_now, v_now, 'fe.trainer.alt@afefe.test', 'FE Demo Trainer Two', v_pw, 'PLATFORM_TRAINER', 'NIGERIA', true, v_now),
        (v_trainee_u1, replace(v_trainee_u1::text, '-', ''), v_now, v_now, 'fe.trainee.demo@afefe.test', 'FE Demo Trainee', v_pw, 'PLATFORM_TRAINEE', 'NIGERIA', true, v_now),
        (v_trainee_u2, replace(v_trainee_u2::text, '-', ''), v_now, v_now, 'fe.trainee.alt1@afefe.test', 'FE Alt Trainee One', v_pw, 'PLATFORM_TRAINEE', 'NIGERIA', true, v_now),
        (v_trainee_u3, replace(v_trainee_u3::text, '-', ''), v_now, v_now, 'fe.trainee.alt2@afefe.test', 'FE Alt Trainee Two', v_pw, 'PLATFORM_TRAINEE', 'NIGERIA', true, v_now)
    ) AS s(id, guid, created_at, updated_at, email_address, full_name, password_hash, user_type, country, is_active, last_login_at)
    WHERE NOT EXISTS (SELECT 1 FROM users u WHERE u.id = s.id);

    UPDATE users SET password_hash = v_pw, is_active = true, updated_at = v_now
    WHERE id IN (v_trainer_u1, v_trainer_u2, v_trainee_u1, v_trainee_u2, v_trainee_u3);

    -- ---- Profiles ----------------------------------------------------------
    FOREACH u_id IN ARRAY ARRAY[v_trainer_u1, v_trainer_u2, v_trainee_u1, v_trainee_u2, v_trainee_u3]::uuid[]
    LOOP
        IF NOT EXISTS (SELECT 1 FROM user_profile up WHERE up.user_id = u_id) THEN
            v_profile_id := gen_random_uuid();
            INSERT INTO user_profile (id, guid, created_at, updated_at, user_id, display_name, locale, job_title, bio)
            VALUES (v_profile_id, replace(v_profile_id::text, '-', ''), v_now, v_now, u_id,
                    'Display ' || left(replace(u_id::text, '-', ''), 8), 'en_NG', NULL, 'FE demo profile');
        END IF;
    END LOOP;

    -- ---- Org members (APPROVED) --------------------------------------------
    INSERT INTO org_members (id, guid, created_at, updated_at, organization_id, user_id, invitation_status, invited_at, joined_at)
    SELECT gen_random_uuid(), replace(gen_random_uuid()::text, '-', ''), v_now, v_now, v_org_id, x.uid, 'APPROVED', v_now, v_now
    FROM (VALUES (v_trainer_u1), (v_trainer_u2), (v_trainee_u1), (v_trainee_u2), (v_trainee_u3)) AS x(uid)
    WHERE NOT EXISTS (SELECT 1 FROM org_members om WHERE om.organization_id = v_org_id AND om.user_id = x.uid);

    UPDATE org_members SET invitation_status = 'APPROVED', joined_at = COALESCE(joined_at, v_now), updated_at = v_now
    WHERE organization_id = v_org_id
      AND user_id IN (v_trainer_u1, v_trainer_u2, v_trainee_u1, v_trainee_u2, v_trainee_u3);

    -- ---- Trainers ----------------------------------------------------------
    FOR tr_idx IN 1..2 LOOP
        u_id := CASE tr_idx WHEN 1 THEN v_trainer_u1 ELSE v_trainer_u2 END;
        IF NOT EXISTS (SELECT 1 FROM trainers t WHERE t.user_id = u_id AND t.org_id = v_org_id) THEN
            v_trainer_row := gen_random_uuid();
            INSERT INTO trainers (id, guid, created_at, updated_at, user_id, org_id, display_name, bio, work_place, website_url)
            VALUES (v_trainer_row, replace(v_trainer_row::text, '-', ''), v_now, v_now, u_id, v_org_id,
                    'FE Trainer ' || tr_idx::text, 'Bio for FE trainer ' || tr_idx::text,
                    'Ocean Academy', 'https://trainer' || tr_idx::text || '.fe-demo.afefe.test');
        END IF;
    END LOOP;

    -- ---- Trainees ----------------------------------------------------------
    FOR te_idx IN 1..3 LOOP
        u_id := CASE te_idx WHEN 1 THEN v_trainee_u1 WHEN 2 THEN v_trainee_u2 ELSE v_trainee_u3 END;
        IF NOT EXISTS (SELECT 1 FROM trainees tr WHERE tr.user_id = u_id AND tr.organization_id = v_org_id) THEN
            v_trainee_row := gen_random_uuid();
            INSERT INTO trainees (id, guid, created_at, updated_at, user_id, user_profile_id, organization_id, display_name, bio, certified)
            SELECT v_trainee_row, replace(v_trainee_row::text, '-', ''), v_now, v_now, u_id, up.id, v_org_id,
                   'FE Trainee ' || te_idx::text, 'FE demo trainee bio #' || te_idx::text, false
            FROM user_profile up WHERE up.user_id = u_id LIMIT 1;
        END IF;
    END LOOP;

    -- ---- Industries (profile interests screen) -----------------------------
    FOR te_idx IN 1..8 LOOP
        IF NOT EXISTS (SELECT 1 FROM industries i WHERE i.code = 'FE_DEMO_IND_' || lpad(te_idx::text, 2, '0')) THEN
            v_ind_id := gen_random_uuid();
            INSERT INTO industries (id, guid, created_at, updated_at, name, code, description)
            VALUES (v_ind_id, replace(v_ind_id::text, '-', ''), v_now, v_now,
                    'FE Demo Industry ' || te_idx::text, 'FE_DEMO_IND_' || lpad(te_idx::text, 2, '0'),
                    'Taxonomy row for FE profile interests');
        END IF;
    END LOOP;

    FOR te_idx IN 1..3 LOOP
        u_id := CASE te_idx WHEN 1 THEN v_trainee_u1 WHEN 2 THEN v_trainee_u2 ELSE v_trainee_u3 END;
        FOR q_idx IN 0..2 LOOP
            SELECT i.id INTO v_ind_id FROM industries i
            WHERE i.code = 'FE_DEMO_IND_' || lpad(((te_idx + q_idx - 1) % 8 + 1)::text, 2, '0') LIMIT 1;
            IF v_ind_id IS NOT NULL AND NOT EXISTS (
                SELECT 1 FROM user_industry_interest x WHERE x.user_id = u_id AND x.industry_id = v_ind_id
            ) THEN
                INSERT INTO user_industry_interest (id, guid, created_at, updated_at, user_id, industry_id)
                VALUES (gen_random_uuid(), replace(gen_random_uuid()::text, '-', ''), v_now, v_now, u_id, v_ind_id);
            END IF;
        END LOOP;
    END LOOP;

    -- ---- Trainings + curriculum + quizzes ----------------------------------
    FOR t_idx IN 1..12 LOOP
        v_status := CASE WHEN t_idx <= 10 THEN 'PUBLISHED' ELSE 'DRAFT' END;
        SELECT t.id INTO v_trainer_row FROM trainers t
        WHERE t.org_id = v_org_id ORDER BY t.created_at NULLS LAST, t.id
        LIMIT 1 OFFSET (t_idx % 2);
        IF v_trainer_row IS NULL THEN CONTINUE; END IF;

        v_slug := 'fe-demo-training-' || lpad(t_idx::text, 2, '0');
        IF EXISTS (SELECT 1 FROM trainings tr WHERE tr.org_id = v_org_id AND tr.slug = v_slug) THEN
            CONTINUE;
        END IF;

        v_title := 'FE Demo Programme ' || t_idx::text || ' — ' ||
            CASE (t_idx % 4) WHEN 0 THEN 'Leadership' WHEN 1 THEN 'Data Skills' WHEN 2 THEN 'Communication' ELSE 'Operations' END;

        v_tid := gen_random_uuid();
        INSERT INTO trainings (
            id, guid, created_at, updated_at, org_id, trainer_id,
            cover_image_url, description, requirement, price, currency, tags, learning_outcome_json_list,
            title, title_hash, slug, summary, level, language, status,
            estimated_minutes, price_cents, free, rating, reviews, published_at, has_certificate, programme_id,
            created_by_id, updated_by_id
        ) VALUES (
            v_tid, replace(v_tid::text, '-', ''), v_now, v_now, v_org_id, v_trainer_row,
            'https://picsum.photos/seed/fe' || t_idx::text || '/800/450',
            'Long description for FE programme ' || t_idx::text || '.',
            '[]', CASE WHEN t_idx % 3 = 0 THEN 49.99 ELSE 0 END, 'NGN', '["fe-demo","seed"]',
            '[{"title":"Outcome A","description":"Demonstrate A"},{"title":"Outcome B","description":"Demonstrate B"}]',
            v_title, replace(v_title, ' ', ''), v_slug, 'Summary for programme ' || t_idx::text,
            (ARRAY['BEGINNER', 'INTERMEDIATE', 'ADVANCED'])[1 + (t_idx % 3)], 'en', v_status,
            60 + (t_idx * 15), CASE WHEN t_idx % 3 = 0 THEN 4999 ELSE 0 END, (t_idx % 3) <> 0,
            3.5 + ((t_idx % 3))::numeric, (t_idx * 2) % 17,
            CASE WHEN v_status = 'PUBLISHED' THEN v_now ELSE NULL END, true, 'FE-PROG-' || t_idx::text,
            CASE WHEN t_idx % 2 = 0 THEN v_trainer_u1 ELSE v_trainer_u2 END,
            CASE WHEN t_idx % 2 = 0 THEN v_trainer_u1 ELSE v_trainer_u2 END
        );

        n_months := CASE WHEN t_idx <= 3 THEN 3 WHEN t_idx <= 7 THEN 2 ELSE 1 END;
        n_weeks := CASE WHEN t_idx <= 5 THEN 2 ELSE 2 END;
        n_items := CASE WHEN t_idx <= 4 THEN 3 ELSE 2 END;

        FOR m_idx IN 1..n_months LOOP
            v_mid := gen_random_uuid();
            INSERT INTO training_months (id, guid, created_at, updated_at, training_id, position, title, summary)
            VALUES (v_mid, replace(v_mid::text, '-', ''), v_now, v_now, v_tid, m_idx,
                    'Month ' || m_idx::text || ' — Themes', 'Month ' || m_idx::text || ' summary');

            FOR w_idx IN 1..n_weeks LOOP
                v_wid := gen_random_uuid();
                INSERT INTO training_weeks (id, guid, created_at, updated_at, month_id, position, title, co_authoring_enabled)
                VALUES (v_wid, replace(v_wid::text, '-', ''), v_now, v_now, v_mid, w_idx,
                        'Week ' || w_idx::text || ' — Practice', (t_idx + w_idx) % 4 = 0);

                FOR it_idx IN 1..n_items LOOP
                    v_fmt := (ARRAY['VIDEO', 'READING', 'MIXED', 'PRACTICE_QUIZ'])[1 + ((m_idx + w_idx + it_idx) % 4)];
                    v_iid := gen_random_uuid();
                    INSERT INTO training_content_items (
                        id, guid, created_at, updated_at, week_id, position, title, item_format, duration_seconds, is_published, has_quiz
                    ) VALUES (
                        v_iid, replace(v_iid::text, '-', ''), v_now, v_now, v_wid, it_idx,
                        'Lesson M' || m_idx::text || ' W' || w_idx::text || ' L' || it_idx::text,
                        v_fmt, 300 + (it_idx * 60), true, (it_idx % 2) = 0
                    );

                    IF v_fmt = 'VIDEO' THEN
                        v_bid := gen_random_uuid();
                        INSERT INTO training_content_blocks (
                            id, guid, created_at, updated_at, content_item_id, sort_order, block_type, payload_json, resource_url, trainee_quiz_id, estimated_duration_seconds
                        ) VALUES (
                            v_bid, replace(v_bid::text, '-', ''), v_now, v_now, v_iid, 1, 'VIDEO_EMBED', '{}',
                            'https://filesamples.com/samples/video/mp4/sample_640x360.mp4',
                            NULL, 300::bigint
                        );
                    ELSIF v_fmt = 'READING' THEN
                        v_bid := gen_random_uuid();
                        INSERT INTO training_content_blocks (
                            id, guid, created_at, updated_at, content_item_id, sort_order, block_type, payload_json, resource_url, trainee_quiz_id, estimated_duration_seconds
                        ) VALUES (
                            v_bid, replace(v_bid::text, '-', ''), v_now, v_now, v_iid, 1, 'READING',
                            '{"body":"<p>FE reading for programme ' || t_idx::text || ', lesson ' || it_idx::text || '.</p>"}',
                            'https://www.w3schools.com/html/html_basic.asp',
                            NULL, 180::bigint
                        );
                    ELSIF v_fmt = 'PRACTICE_QUIZ' THEN
                        v_bid := gen_random_uuid();
                        INSERT INTO training_content_blocks (
                            id, guid, created_at, updated_at, content_item_id, sort_order, block_type, payload_json, resource_url, trainee_quiz_id, estimated_duration_seconds
                        ) VALUES (
                            v_bid, replace(v_bid::text, '-', ''), v_now, v_now, v_iid, 1, 'PRACTICE_QUIZ', '{}', NULL, NULL, 600::bigint
                        );
                    ELSE
                        n_blocks := 2 + (it_idx % 2);
                        FOR bl_idx IN 1..n_blocks LOOP
                            v_blk_type := (ARRAY['READING', 'VIDEO_EMBED', 'IMAGE', 'RESOURCE_FILE'])[1 + ((bl_idx + it_idx) % 4)];
                            v_bid := gen_random_uuid();
                            INSERT INTO training_content_blocks (
                                id, guid, created_at, updated_at, content_item_id, sort_order, block_type, payload_json, resource_url, trainee_quiz_id, estimated_duration_seconds
                            ) VALUES (
                                v_bid, replace(v_bid::text, '-', ''), v_now, v_now, v_iid, bl_idx, v_blk_type,
                                '{"body":"FE block p' || t_idx::text || ' l' || it_idx::text || ' b' || bl_idx::text || '"}',
                                CASE v_blk_type::text
                                    WHEN 'VIDEO_EMBED' THEN 'https://filesamples.com/samples/video/mp4/sample_640x360.mp4'
                                    WHEN 'IMAGE' THEN 'https://picsum.photos/seed/fe' || t_idx::text || it_idx::text || bl_idx::text || '/1200/800'
                                    WHEN 'RESOURCE_FILE' THEN 'https://www.w3.org/WAI/ER/tests/xhtml/testfiles/resources/pdf/dummy.pdf'
                                    ELSE 'https://www.w3schools.com/html/html_basic.asp'
                                END, NULL, (120 * bl_idx)::bigint
                            );
                        END LOOP;
                    END IF;
                END LOOP;
            END LOOP;
        END LOOP;

        IF v_status = 'PUBLISHED' THEN
            FOR q_idx IN 1..2 LOOP
                v_qid := gen_random_uuid();
                INSERT INTO trainee_quizzes (id, guid, created_at, updated_at, training_id, title, attempt_limit, passing_score_percent)
                VALUES (v_qid, replace(v_qid::text, '-', ''), v_now, v_now, v_tid,
                        'Quiz ' || q_idx::text || ' — Programme ' || t_idx::text, 5, 70 + q_idx * 5);

                FOR bl_idx IN 1..3 LOOP
                    v_qn_id := gen_random_uuid();
                    INSERT INTO trainee_quiz_questions (id, guid, created_at, updated_at, quiz_id, position, prompt)
                    VALUES (v_qn_id, replace(v_qn_id::text, '-', ''), v_now, v_now, v_qid, bl_idx,
                            'Q' || bl_idx::text || ' for quiz ' || q_idx::text || ' (programme ' || t_idx::text || ')?');

                    v_opt_correct := gen_random_uuid();
                    INSERT INTO trainee_quiz_options (id, guid, created_at, updated_at, question_id, position, label, correct)
                    VALUES
                        (v_opt_correct, replace(v_opt_correct::text, '-', ''), v_now, v_now, v_qn_id, 1, 'Correct answer Q' || bl_idx::text, true),
                        (gen_random_uuid(), replace(gen_random_uuid()::text, '-', ''), v_now, v_now, v_qn_id, 2, 'Distractor A', false),
                        (gen_random_uuid(), replace(gen_random_uuid()::text, '-', ''), v_now, v_now, v_qn_id, 3, 'Distractor B', false);
                END LOOP;
            END LOOP;

            -- Link PRACTICE_QUIZ content blocks to trainee quizzes (round-robin per training)
            q_idx := 0;
            FOR v_iid IN
                SELECT ci.id FROM training_content_items ci
                JOIN training_weeks w ON w.id = ci.week_id
                JOIN training_months m ON m.id = w.month_id
                WHERE m.training_id = v_tid AND ci.item_format = 'PRACTICE_QUIZ'
                ORDER BY m.position, w.position, ci.position
            LOOP
                SELECT q.id INTO v_qid FROM trainee_quizzes q
                WHERE q.training_id = v_tid ORDER BY q.created_at LIMIT 1 OFFSET (q_idx % 2);
                IF v_qid IS NOT NULL THEN
                    UPDATE training_content_blocks SET trainee_quiz_id = v_qid, updated_at = v_now
                    WHERE content_item_id = v_iid AND block_type = 'PRACTICE_QUIZ';
                END IF;
                q_idx := q_idx + 1;
            END LOOP;
        END IF;
    END LOOP;

    -- ---- Enrollments -------------------------------------------------------
    FOR t_idx IN 1..10 LOOP
        SELECT tr.id INTO v_tid FROM trainings tr
        WHERE tr.org_id = v_org_id AND tr.status = 'PUBLISHED' AND tr.slug LIKE 'fe-demo-training-%'
        ORDER BY tr.slug LIMIT 1 OFFSET t_idx - 1;
        EXIT WHEN v_tid IS NULL;

        -- Demo trainee: all 10 programmes (COMPLETED every 5th)
        IF NOT EXISTS (SELECT 1 FROM training_enrollments e WHERE e.user_id = v_trainee_u1 AND e.training_id = v_tid) THEN
            v_enr := gen_random_uuid();
            INSERT INTO training_enrollments (id, guid, created_at, updated_at, org_id, user_id, training_id, current_month_id, status, progress_percent, started_at, completed_at)
            SELECT v_enr, replace(v_enr::text, '-', ''), v_now, v_now, v_org_id, v_trainee_u1, v_tid,
                   (SELECT tm.id FROM training_months tm WHERE tm.training_id = v_tid ORDER BY tm.position LIMIT 1),
                   CASE WHEN t_idx % 5 = 0 THEN 'COMPLETED' WHEN t_idx % 3 = 0 THEN 'ENROLLED' ELSE 'IN_PROGRESS' END,
                   CASE WHEN t_idx % 5 = 0 THEN 100 WHEN t_idx % 3 = 0 THEN 0 ELSE (t_idx * 7) % 100 END,
                   CASE WHEN t_idx % 3 = 0 THEN NULL ELSE v_now - (t_idx::text || ' days')::interval END,
                   CASE WHEN t_idx % 5 = 0 THEN v_now - interval '3 days' ELSE NULL END;
        END IF;

        -- Alt trainee 1: first 7 programmes
        IF t_idx <= 7 AND NOT EXISTS (SELECT 1 FROM training_enrollments e WHERE e.user_id = v_trainee_u2 AND e.training_id = v_tid) THEN
            v_enr := gen_random_uuid();
            INSERT INTO training_enrollments (id, guid, created_at, updated_at, org_id, user_id, training_id, current_month_id, status, progress_percent, started_at, completed_at)
            SELECT v_enr, replace(v_enr::text, '-', ''), v_now, v_now, v_org_id, v_trainee_u2, v_tid,
                   (SELECT tm.id FROM training_months tm WHERE tm.training_id = v_tid ORDER BY tm.position LIMIT 1),
                   'IN_PROGRESS', (t_idx * 3) % 100, v_now, NULL;
        END IF;

        -- Alt trainee 2: odd-index programmes
        IF t_idx % 2 = 1 AND NOT EXISTS (SELECT 1 FROM training_enrollments e WHERE e.user_id = v_trainee_u3 AND e.training_id = v_tid) THEN
            v_enr := gen_random_uuid();
            INSERT INTO training_enrollments (id, guid, created_at, updated_at, org_id, user_id, training_id, current_month_id, status, progress_percent, started_at, completed_at)
            SELECT v_enr, replace(v_enr::text, '-', ''), v_now, v_now, v_org_id, v_trainee_u3, v_tid,
                   (SELECT tm.id FROM training_months tm WHERE tm.training_id = v_tid ORDER BY tm.position LIMIT 1),
                   'ENROLLED', 0, NULL, NULL;
        END IF;
    END LOOP;

    -- ---- Progress (demo trainee) -------------------------------------------
    FOR v_enr IN SELECT e.id FROM training_enrollments e
        WHERE e.user_id = v_trainee_u1 AND e.org_id = v_org_id AND e.status IN ('IN_PROGRESS', 'COMPLETED')
    LOOP
        FOR v_iid IN
            SELECT ci.id FROM training_content_items ci
            JOIN training_weeks w ON w.id = ci.week_id
            JOIN training_months m ON m.id = w.month_id
            JOIN training_enrollments e ON e.training_id = m.training_id
            WHERE e.id = v_enr
            ORDER BY m.position, w.position, ci.position LIMIT 8
        LOOP
            IF NOT EXISTS (SELECT 1 FROM training_content_item_progress p WHERE p.enrollment_id = v_enr AND p.content_item_id = v_iid) THEN
                v_prog_st := (ARRAY['NOT_STARTED', 'IN_PROGRESS', 'COMPLETED'])[1 + (floor(random() * 3)::int)];
                INSERT INTO training_content_item_progress (
                    id, guid, created_at, updated_at, enrollment_id, content_item_id, progress_status, progress_percent, last_accessed_at, current_block_id
                )
                SELECT gen_random_uuid(), replace(gen_random_uuid()::text, '-', ''), v_now, v_now, v_enr, v_iid, v_prog_st,
                       (10 + (random() * 80)::int), v_now,
                       (SELECT b.id FROM training_content_blocks b WHERE b.content_item_id = v_iid ORDER BY b.sort_order LIMIT 1);
            END IF;

            FOR v_bid IN SELECT b.id FROM training_content_blocks b WHERE b.content_item_id = v_iid ORDER BY b.sort_order LIMIT 2
            LOOP
                IF NOT EXISTS (SELECT 1 FROM training_content_block_progress bp WHERE bp.enrollment_id = v_enr AND bp.block_id = v_bid) THEN
                    v_prog_st := (ARRAY['NOT_STARTED', 'IN_PROGRESS', 'COMPLETED'])[1 + (floor(random() * 3)::int)];
                    INSERT INTO training_content_block_progress (
                        id, guid, created_at, updated_at, enrollment_id, block_id, last_position_seconds, last_viewed_at, completed_at, progress_status
                    ) VALUES (
                        gen_random_uuid(), replace(gen_random_uuid()::text, '-', ''), v_now, v_now, v_enr, v_bid,
                        (30 + (random() * 200)::int)::int, v_now,
                        CASE WHEN random() > 0.5 THEN v_now ELSE NULL END, v_prog_st
                    );
                END IF;
            END LOOP;
        END LOOP;
    END LOOP;

    -- ---- Recently viewed (demo trainee: 7 programmes) ----------------------
    FOR t_idx IN 1..7 LOOP
        SELECT tr.id INTO v_tid FROM trainings tr
        WHERE tr.org_id = v_org_id AND tr.status = 'PUBLISHED' AND tr.slug LIKE 'fe-demo-training-%'
        ORDER BY tr.slug LIMIT 1 OFFSET t_idx - 1;
        IF v_tid IS NOT NULL THEN
            INSERT INTO user_training_views (id, guid, created_at, updated_at, org_id, user_id, training_id, viewed_at)
            VALUES (gen_random_uuid(), replace(gen_random_uuid()::text, '-', ''), v_now, v_now, v_org_id, v_trainee_u1, v_tid, v_now - (t_idx::text || ' hours')::interval)
            ON CONFLICT (user_id, training_id) DO UPDATE SET viewed_at = EXCLUDED.viewed_at;
        END IF;
    END LOOP;

    -- ---- Wishlists / saved -------------------------------------------------
    FOR t_idx IN SELECT unnest(ARRAY[2, 4, 6, 8, 9]) LOOP
        SELECT tr.id INTO v_tid FROM trainings tr
        WHERE tr.org_id = v_org_id AND tr.status = 'PUBLISHED' AND tr.slug LIKE 'fe-demo-training-%'
        ORDER BY tr.slug LIMIT 1 OFFSET t_idx - 1;
        IF v_tid IS NOT NULL THEN
            INSERT INTO training_wish_lists (id, guid, created_at, updated_at, org_id, user_id, training_id)
            VALUES (gen_random_uuid(), replace(gen_random_uuid()::text, '-', ''), v_now, v_now, v_org_id, v_trainee_u1, v_tid)
            ON CONFLICT (user_id, training_id) DO NOTHING;
        END IF;
    END LOOP;

    FOR t_idx, te_idx IN SELECT * FROM (VALUES (3, 2), (5, 3), (7, 2)) AS w(t_idx, te_idx) LOOP
        SELECT tr.id INTO v_tid FROM trainings tr
        WHERE tr.org_id = v_org_id AND tr.status = 'PUBLISHED' AND tr.slug LIKE 'fe-demo-training-%'
        ORDER BY tr.slug LIMIT 1 OFFSET t_idx - 1;
        v_trainee_uid := CASE te_idx WHEN 2 THEN v_trainee_u2 ELSE v_trainee_u3 END;
        IF v_tid IS NOT NULL THEN
            INSERT INTO training_wish_lists (id, guid, created_at, updated_at, org_id, user_id, training_id)
            VALUES (gen_random_uuid(), replace(gen_random_uuid()::text, '-', ''), v_now, v_now, v_org_id, v_trainee_uid, v_tid)
            ON CONFLICT (user_id, training_id) DO NOTHING;
        END IF;
    END LOOP;

    -- ---- Ratings / reviews -------------------------------------------------
    FOR t_idx, te_idx, q_idx IN SELECT * FROM (VALUES
        (1, 1, 5), (2, 1, 4), (3, 2, 5), (4, 2, 4), (5, 3, 3), (1, 2, 5), (2, 3, 4)
    ) AS r(t_idx, te_idx, q_idx) LOOP
        SELECT tr.id INTO v_tid FROM trainings tr
        WHERE tr.org_id = v_org_id AND tr.status = 'PUBLISHED' AND tr.slug LIKE 'fe-demo-training-%'
        ORDER BY tr.slug LIMIT 1 OFFSET t_idx - 1;
        v_trainee_uid := CASE te_idx WHEN 1 THEN v_trainee_u1 WHEN 2 THEN v_trainee_u2 ELSE v_trainee_u3 END;
        IF v_tid IS NOT NULL THEN
            INSERT INTO training_ratings (id, guid, created_at, updated_at, org_id, user_id, training_id, rating, review, rated_at, review_updated_at)
            VALUES (gen_random_uuid(), replace(gen_random_uuid()::text, '-', ''), v_now, v_now, v_org_id, v_trainee_uid, v_tid, q_idx,
                    'FE demo review — programme ' || t_idx::text || ' from trainee ' || te_idx::text || '.', v_now, NULL)
            ON CONFLICT (user_id, training_id) DO NOTHING;
        END IF;
    END LOOP;

    FOR t_idx IN 4..9 LOOP
        SELECT tr.id INTO v_tid FROM trainings tr
        WHERE tr.org_id = v_org_id AND tr.status = 'PUBLISHED' AND tr.slug LIKE 'fe-demo-training-%'
        ORDER BY tr.slug LIMIT 1 OFFSET t_idx - 1;
        u_id := CASE WHEN t_idx % 2 = 0 THEN v_trainer_u1 ELSE v_trainer_u2 END;
        IF v_tid IS NOT NULL THEN
            INSERT INTO training_ratings (id, guid, created_at, updated_at, org_id, user_id, training_id, rating, review, rated_at, review_updated_at)
            VALUES (gen_random_uuid(), replace(gen_random_uuid()::text, '-', ''), v_now, v_now, v_org_id, u_id, v_tid, 4 + (t_idx % 2),
                    'Trainer perspective on FE programme ' || t_idx::text || '.', v_now, NULL)
            ON CONFLICT (user_id, training_id) DO NOTHING;
        END IF;
    END LOOP;

    -- ---- Quiz attempts (demo trainee) --------------------------------------
    -- GRADED attempt on first quiz
    v_enr := NULL; v_qid := NULL; v_qn_id := NULL; v_opt_correct := NULL;
    SELECT e.id, q.id INTO v_enr, v_qid
    FROM training_enrollments e
    JOIN trainings tr ON tr.id = e.training_id AND tr.status = 'PUBLISHED' AND tr.slug = 'fe-demo-training-01'
    JOIN trainee_quizzes q ON q.training_id = tr.id
    WHERE e.user_id = v_trainee_u1 AND e.org_id = v_org_id
    ORDER BY q.created_at LIMIT 1;

    IF v_enr IS NOT NULL AND v_qid IS NOT NULL THEN
        SELECT qq.id, o.id INTO v_qn_id, v_opt_correct
        FROM trainee_quiz_questions qq
        JOIN trainee_quiz_options o ON o.question_id = qq.id AND o.correct = true
        WHERE qq.quiz_id = v_qid ORDER BY qq.position LIMIT 1;

        IF NOT EXISTS (SELECT 1 FROM trainee_quiz_attempts a WHERE a.enrollment_id = v_enr AND a.quiz_id = v_qid AND a.attempt_number = 1) THEN
            INSERT INTO trainee_quiz_attempts (id, guid, created_at, updated_at, enrollment_id, quiz_id, attempt_number, attempt_status, score_percent, started_at, submitted_at, answers_json)
            VALUES (gen_random_uuid(), replace(gen_random_uuid()::text, '-', ''), v_now, v_now, v_enr, v_qid, 1, 'GRADED', 85,
                    v_now - interval '2 hours', v_now - interval '1 hour',
                    json_build_object(v_qn_id::text, v_opt_correct::text)::text);
        END IF;
    END IF;

    -- IN_PROGRESS attempt on second quiz (same programme)
    v_qid := NULL;
    SELECT q.id INTO v_qid FROM trainee_quizzes q
    JOIN trainings tr ON tr.id = q.training_id AND tr.slug = 'fe-demo-training-01'
    ORDER BY q.created_at OFFSET 1 LIMIT 1;

    IF v_enr IS NOT NULL AND v_qid IS NOT NULL AND NOT EXISTS (
        SELECT 1 FROM trainee_quiz_attempts a WHERE a.enrollment_id = v_enr AND a.quiz_id = v_qid AND a.attempt_number = 1
    ) THEN
        INSERT INTO trainee_quiz_attempts (id, guid, created_at, updated_at, enrollment_id, quiz_id, attempt_number, attempt_status, score_percent, started_at, submitted_at, answers_json)
        VALUES (gen_random_uuid(), replace(gen_random_uuid()::text, '-', ''), v_now, v_now, v_enr, v_qid, 1, 'IN_PROGRESS', NULL,
                v_now - interval '30 minutes', NULL, '{}');
    END IF;

    -- ---- Certificates (demo trainee completed programmes) ------------------
    FOR v_enr IN SELECT e.id FROM training_enrollments e
        WHERE e.user_id = v_trainee_u1 AND e.org_id = v_org_id AND e.status = 'COMPLETED'
    LOOP
        SELECT e.training_id, e.user_id INTO v_tid, v_trainee_uid FROM training_enrollments e WHERE e.id = v_enr;
        IF NOT EXISTS (SELECT 1 FROM training_certificates c WHERE c.user_id = v_trainee_uid AND c.training_id = v_tid) THEN
            INSERT INTO training_certificates (id, guid, created_at, updated_at, org_id, user_id, training_id, enrollment_id, issued_at, certificate_url, file_name, file_size_bytes)
            VALUES (gen_random_uuid(), replace(gen_random_uuid()::text, '-', ''), v_now, v_now, v_org_id, v_trainee_uid, v_tid, v_enr,
                    v_now - interval '3 days', 'https://www.w3.org/WAI/ER/tests/xhtml/testfiles/resources/pdf/dummy.pdf',
                    'fe-cert.pdf', 245760);
        END IF;
    END LOOP;

    -- ---- Calendar class sessions + attendance ------------------------------
    SELECT tr.id INTO v_tid FROM trainings tr
    WHERE tr.org_id = v_org_id AND tr.slug = 'fe-demo-training-01' LIMIT 1;

    IF v_tid IS NOT NULL THEN
        -- Match AttendanceTokenHasher: UUID string + NUL byte + token (UTF-8)
        v_token_hash := encode(
            digest(
                convert_to(v_ce_upcoming_1::text, 'UTF8') || decode('00', 'hex') || convert_to(v_attendance_token, 'UTF8'),
                'sha256'
            ),
            'hex'
        );

        INSERT INTO calendar_event (
            id, guid, created_at, updated_at, cover_photo, date, from_time, to_time, time_zone,
            assigned_course_id, assigned_training_id, title, description, location,
            event_marker, add_location, video_conferencing, event_type, status, price, currency,
            created_by_id, updated_by_id,
            attendance_token_hash, attendance_opens_minutes_before, attendance_requires_live_location,
            session_latitude, session_longitude, attendance_geofence_radius_meters
        ) VALUES
            (v_ce_past_present, replace(v_ce_past_present::text, '-', ''), v_now, v_now,
             '', v_today - 14, '09:00', '11:00', 'UTC', NULL, v_tid,
             'Class 1 — Kick-off', 'Past session with PRESENT attendance.', 'Ocean Academy — Room A',
             false, true, false, 'DEFAULT', 'COMPLETED', 0, 'NGN', v_trainer_u1, v_trainer_u1,
             NULL, 10, false, 6.5244, 3.3792, 150),
            (v_ce_past_late, replace(v_ce_past_late::text, '-', ''), v_now, v_now,
             '', v_today - 7, '14:00', '16:00', 'UTC', NULL, v_tid,
             'Class 2 — Stand-ups', 'Past session with LATE attendance.', 'Ocean Academy — Room B',
             false, true, false, 'DEFAULT', 'COMPLETED', 0, 'NGN', v_trainer_u1, v_trainer_u1,
             NULL, 10, false, 6.5244, 3.3792, 150),
            (v_ce_upcoming_1, replace(v_ce_upcoming_1::text, '-', ''), v_now, v_now,
             'https://picsum.photos/seed/fe-ce1/640/360', v_today + 7, '10:00', '12:00', 'UTC', NULL, v_tid,
             'Class 3 — Presentations', 'Upcoming session with attendance token.', 'Ocean Academy — Room A',
             false, true, false, 'DEFAULT', 'PENDING', 0, 'NGN', v_trainer_u1, v_trainer_u1,
             v_token_hash, 15, false, 6.5244, 3.3792, 150),
            (v_ce_upcoming_2, replace(v_ce_upcoming_2::text, '-', ''), v_now, v_now,
             '', v_today + 14, '10:00', '12:00', 'UTC', NULL, v_tid,
             'Class 4 — Feedback loops', 'Second upcoming session.', 'Ocean Academy — Room C',
             false, true, false, 'DEFAULT', 'PENDING', 0, 'NGN', v_trainer_u1, v_trainer_u1,
             NULL, 10, false, NULL, NULL, NULL)
        ON CONFLICT (id) DO UPDATE SET date = EXCLUDED.date, status = EXCLUDED.status,
            attendance_token_hash = COALESCE(EXCLUDED.attendance_token_hash, calendar_event.attendance_token_hash);

        SELECT e.id INTO v_enr FROM training_enrollments e
        WHERE e.user_id = v_trainee_u1 AND e.training_id = v_tid LIMIT 1;

        IF v_enr IS NOT NULL THEN
            INSERT INTO training_session_attendance (
                id, guid, created_at, updated_at, org_id, training_enrollment_id, calendar_event_id,
                status, marked_at, live_location_used, submitted_latitude, submitted_longitude
            ) VALUES
                (v_att_present_id, replace(v_att_present_id::text, '-', ''), v_now, v_now, v_org_id, v_enr, v_ce_past_present,
                 'PRESENT', (v_today - 14)::timestamptz + time '08:55', false, NULL, NULL),
                (v_att_late_id, replace(v_att_late_id::text, '-', ''), v_now, v_now, v_org_id, v_enr, v_ce_past_late,
                 'LATE', (v_today - 7)::timestamptz + time '14:12', false, NULL, NULL)
            ON CONFLICT (training_enrollment_id, calendar_event_id) DO UPDATE SET
                status = EXCLUDED.status, marked_at = EXCLUDED.marked_at;
        END IF;
    END IF;

    -- Upcoming session on programme 2 (calendar variety)
    SELECT tr.id INTO v_tid FROM trainings tr WHERE tr.org_id = v_org_id AND tr.slug = 'fe-demo-training-02' LIMIT 1;
    IF v_tid IS NOT NULL THEN
        INSERT INTO calendar_event (
            id, guid, created_at, updated_at, cover_photo, date, from_time, to_time, time_zone,
            assigned_course_id, assigned_training_id, title, description, location,
            event_marker, add_location, video_conferencing, event_type, status, price, currency,
            created_by_id, updated_by_id
        ) VALUES (
            v_ce_upcoming_3, replace(v_ce_upcoming_3::text, '-', ''), v_now, v_now,
            '', v_today + 10, '15:00', '17:00', 'UTC', NULL, v_tid,
            'Class 1 — Data clinic', 'Virtual upcoming session.', 'Microsoft Teams',
            false, false, true, 'VIDEO_CONFERENCING', 'PENDING', 0, 'NGN', v_trainer_u2, v_trainer_u2
        ) ON CONFLICT (id) DO UPDATE SET date = EXCLUDED.date;
    END IF;

    -- ---- OJT (programme 1 — milestones + sample session logs) --------------
    SELECT tr.id INTO v_tid FROM trainings tr WHERE tr.org_id = v_org_id AND tr.slug = 'fe-demo-training-01' LIMIT 1;
    IF v_tid IS NOT NULL THEN
        UPDATE trainings
        SET ojt_duration_days = 90, min_ojt_hours = 120, updated_at = v_now
        WHERE id = v_tid;

        INSERT INTO training_ojt_milestone (id, guid, created_at, updated_at, training_id, position, title, offset_days, milestone_type)
        VALUES
            (v_ojt_ms_start, replace(v_ojt_ms_start::text, '-', ''), v_now, v_now, v_tid, 1, 'Start OJT', 0, 'START'),
            (v_ojt_ms_week4, replace(v_ojt_ms_week4::text, '-', ''), v_now, v_now, v_tid, 2, 'Week 4 check-in', 28, 'WEEK'),
            (v_ojt_ms_complete, replace(v_ojt_ms_complete::text, '-', ''), v_now, v_now, v_tid, 3, 'Complete OJT hours', 90, 'COMPLETION')
        ON CONFLICT (id) DO UPDATE SET title = EXCLUDED.title, offset_days = EXCLUDED.offset_days, milestone_type = EXCLUDED.milestone_type;

        SELECT e.id INTO v_enr FROM training_enrollments e
        WHERE e.user_id = v_trainee_u1 AND e.training_id = v_tid LIMIT 1;

        IF v_enr IS NOT NULL THEN
            INSERT INTO trainee_ojt_session_log (
                id, guid, created_at, updated_at, org_id, training_enrollment_id,
                supervisor_user_id, supervisor_name, session_location, session_date,
                duration_hours, description, live_location_used, submitted_latitude, submitted_longitude,
                supporting_documents_json, display_session_id
            ) VALUES
                (v_ojt_log_1, replace(v_ojt_log_1::text, '-', ''), v_now - interval '21 days', v_now, v_org_id, v_enr,
                 v_trainer_u1, 'FE Trainer 1', 'Ocean Academy — Workshop floor', v_today - 21,
                 8, 'Shadowed supervisor on client onboarding workflows and documentation.', false, NULL, NULL,
                 '[{"fileName":"onboarding-notes.pdf","fileUrl":"https://res.cloudinary.com/demo/raw/upload/v1/fe/onboarding-notes.pdf","fileSizeBytes":204800,"contentType":"application/pdf"}]',
                 v_ojt_display_1),
                (v_ojt_log_2, replace(v_ojt_log_2::text, '-', ''), v_now - interval '14 days', v_now, v_org_id, v_enr,
                 v_trainer_u1, 'FE Trainer 1', 'Ocean Academy — Room B', v_today - 14,
                 6, 'Practiced stand-up presentations and received feedback from the team lead.', false, NULL, NULL, NULL,
                 v_ojt_display_2),
                (v_ojt_log_3, replace(v_ojt_log_3::text, '-', ''), v_now - interval '7 days', v_now, v_org_id, v_enr,
                 v_trainer_u2, 'FE Trainer 2', 'Lagos HQ — Operations desk', v_today - 7,
                 4, 'Supported live operations queue and logged daily KPI observations.', true, 6.5244000, 3.3792000, NULL,
                 v_ojt_display_3)
            ON CONFLICT (id) DO UPDATE SET
                session_date = EXCLUDED.session_date,
                duration_hours = EXCLUDED.duration_hours,
                description = EXCLUDED.description;
        END IF;

        -- Alt trainee 1 (fe.trainee.alt1@afefe.test) — same programme, separate enrollment
        SELECT e.id INTO v_enr FROM training_enrollments e
        WHERE e.user_id = v_trainee_u2 AND e.training_id = v_tid LIMIT 1;

        IF v_enr IS NOT NULL THEN
            INSERT INTO trainee_ojt_session_log (
                id, guid, created_at, updated_at, org_id, training_enrollment_id,
                supervisor_user_id, supervisor_name, session_location, session_date,
                duration_hours, description, live_location_used, submitted_latitude, submitted_longitude,
                supporting_documents_json, display_session_id
            ) VALUES
                (v_ojt_log_alt1_1, replace(v_ojt_log_alt1_1::text, '-', ''), v_now - interval '20 days', v_now, v_org_id, v_enr,
                 v_trainer_u1, 'FE Trainer 1', 'Ocean Academy — Workshop floor', v_today - 20,
                 7, 'Orientation week: shadowed onboarding and documentation workflows.', false, NULL, NULL,
                 '[{"fileName":"alt1-onboarding-notes.pdf","fileUrl":"https://res.cloudinary.com/demo/raw/upload/v1/fe/alt1-onboarding-notes.pdf","fileSizeBytes":204800,"contentType":"application/pdf"}]',
                 v_ojt_display_alt1_1),
                (v_ojt_log_alt1_2, replace(v_ojt_log_alt1_2::text, '-', ''), v_now - interval '13 days', v_now, v_org_id, v_enr,
                 v_trainer_u2, 'FE Trainer 2', 'Ocean Academy — Room B', v_today - 13,
                 5, 'Practiced team stand-ups and received feedback from the lead trainer.', false, NULL, NULL, NULL,
                 v_ojt_display_alt1_2),
                (v_ojt_log_alt1_3, replace(v_ojt_log_alt1_3::text, '-', ''), v_now - interval '6 days', v_now, v_org_id, v_enr,
                 v_trainer_u2, 'FE Trainer 2', 'Lagos HQ — Operations desk', v_today - 6,
                 4, 'Supported live operations and logged daily KPI observations.', true, 6.5244000, 3.3792000, NULL,
                 v_ojt_display_alt1_3)
            ON CONFLICT (id) DO UPDATE SET
                training_enrollment_id = EXCLUDED.training_enrollment_id,
                session_date = EXCLUDED.session_date,
                duration_hours = EXCLUDED.duration_hours,
                description = EXCLUDED.description;
        END IF;
    END IF;

    -- ---- Training content notes (alt trainee 1, programme 1) ----------------
    SELECT tr.id INTO v_tid FROM trainings tr
    WHERE tr.org_id = v_org_id AND tr.slug = 'fe-demo-training-01' LIMIT 1;

    IF v_tid IS NOT NULL THEN
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
                    'FE demo note on lesson ' || replace(v_iid::text, '-', ''), 0, NULL
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
    END IF;

    RAISE NOTICE 'FE full trainee seed complete. Org %', v_org_id;
END $$;

COMMIT;

-- Verification
SELECT 'LOGIN' AS section, u.email_address, 'Trainee@123' AS password, u.user_type, u.full_name
FROM users u
WHERE lower(u.email_address) LIKE 'fe.%@afefe.test'
ORDER BY u.user_type, u.email_address;
