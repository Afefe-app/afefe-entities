-- =============================================================================
-- Afefe trainee / IAM dummy dataset (PostgreSQL)
-- =============================================================================
-- One organization, 2 trainers, 5 trainees, 12 trainings (10 PUBLISHED + 2
-- DRAFT), large curricula (months → weeks → lessons → blocks), many quizzes,
-- enrollments, progress, views, wishlists, ratings, certificates, industries.
--
-- Prerequisites: pgcrypto (bcrypt passwords compatible with Spring
-- BCryptPasswordEncoder). Column names must match JPA + snake_case physical
-- naming (see afefe-config ClientCaseNamingStrategy).
--
-- Login for all seeded accounts:  Password123!
--   seed.trainer1@example.com, seed.trainer2@example.com
--   seed.trainee1@example.com … seed.trainee5@example.com
--
-- organization.status: JPA has no @Enumerated on Organization.status → ORDINAL
--   (PostgreSQL smallint). PENDING=0, ACTIVE=1. plan_tier and role are STRING.
--
-- Re-runs: organization slug `afefe-seed-demo-org` is fixed; trainings use
-- deterministic slugs `afefe-seed-training-01` … `12`. Existing rows are
-- skipped where noted. To fully reset, delete the org (cascade if configured)
-- or truncate dependent tables in dev.
-- =============================================================================

BEGIN;

CREATE EXTENSION IF NOT EXISTS pgcrypto;

DO $$
DECLARE
    v_org_id          uuid;
    v_trainer_u1      uuid := 'b0e00000-0000-4000-8000-000000000011'::uuid;
    v_trainer_u2      uuid := 'b0e00000-0000-4000-8000-000000000012'::uuid;
    v_trainee_u1      uuid := 'b0e00000-0000-4000-8000-000000000021'::uuid;
    v_trainee_u2      uuid := 'b0e00000-0000-4000-8000-000000000022'::uuid;
    v_trainee_u3      uuid := 'b0e00000-0000-4000-8000-000000000023'::uuid;
    v_trainee_u4      uuid := 'b0e00000-0000-4000-8000-000000000024'::uuid;
    v_trainee_u5      uuid := 'b0e00000-0000-4000-8000-000000000025'::uuid;
    v_pw              text := crypt('Password123!', gen_salt('bf', 10));
    v_now             timestamptz := now();

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

    t_idx             int;
    m_idx             int;
    w_idx             int;
    it_idx            int;
    bl_idx            int;
    q_idx             int;
    n_months          int;
    n_weeks         int;
    n_items         int;
    n_blocks        int;
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
BEGIN
    -- ---- Organization -------------------------------------------------------
    SELECT id INTO v_org_id FROM organization WHERE slug = 'afefe-seed-demo-org' LIMIT 1;
    IF v_org_id IS NULL THEN
        v_org_id := 'b0e00000-0000-4000-8000-000000000001'::uuid;
        INSERT INTO organization (id, guid, created_at, updated_at, name, slug, status, plan_tier, role)
        VALUES (
            v_org_id,
            replace(v_org_id::text, '-', ''),
            v_now, v_now,
            'Afefe Seed Demo Org',
            'afefe-seed-demo-org',
            1::smallint,
            'TIER1',
            'OWNER'
        );
    END IF;

    -- ---- Users (skip if id already present) --------------------------------
    INSERT INTO users (id, guid, created_at, updated_at, email_address, full_name, password_hash, user_type, country, is_active, last_login_at)
    SELECT s.id, s.guid, s.created_at, s.updated_at, s.email_address, s.full_name, s.password_hash, s.user_type, s.country, s.is_active, s.last_login_at
    FROM (VALUES
        (v_trainer_u1, replace(v_trainer_u1::text, '-', ''), v_now, v_now, 'seed.trainer1@example.com', 'Seed Trainer One', v_pw, 'PLATFORM_INSTRUCTOR', 'NIGERIA', true, v_now),
        (v_trainer_u2, replace(v_trainer_u2::text, '-', ''), v_now, v_now, 'seed.trainer2@example.com', 'Seed Trainer Two', v_pw, 'PLATFORM_INSTRUCTOR', 'NIGERIA', true, v_now),
        (v_trainee_u1, replace(v_trainee_u1::text, '-', ''), v_now, v_now, 'seed.trainee1@example.com', 'Seed Trainee One', v_pw, 'PLATFORM_TRAINEE', 'NIGERIA', true, v_now),
        (v_trainee_u2, replace(v_trainee_u2::text, '-', ''), v_now, v_now, 'seed.trainee2@example.com', 'Seed Trainee Two', v_pw, 'PLATFORM_TRAINEE', 'NIGERIA', true, v_now),
        (v_trainee_u3, replace(v_trainee_u3::text, '-', ''), v_now, v_now, 'seed.trainee3@example.com', 'Seed Trainee Three', v_pw, 'PLATFORM_TRAINEE', 'NIGERIA', true, v_now),
        (v_trainee_u4, replace(v_trainee_u4::text, '-', ''), v_now, v_now, 'seed.trainee4@example.com', 'Seed Trainee Four', v_pw, 'PLATFORM_TRAINEE', 'NIGERIA', true, v_now),
        (v_trainee_u5, replace(v_trainee_u5::text, '-', ''), v_now, v_now, 'seed.trainee5@example.com', 'Seed Trainee Five', v_pw, 'PLATFORM_TRAINEE', 'NIGERIA', true, v_now)
    ) AS s(id, guid, created_at, updated_at, email_address, full_name, password_hash, user_type, country, is_active, last_login_at)
    WHERE NOT EXISTS (SELECT 1 FROM users u WHERE u.id = s.id);

    -- ---- Profiles -----------------------------------------------------------
    FOREACH u_id IN ARRAY ARRAY[
        v_trainer_u1, v_trainer_u2, v_trainee_u1, v_trainee_u2, v_trainee_u3, v_trainee_u4, v_trainee_u5
    ]::uuid[]
    LOOP
        IF NOT EXISTS (SELECT 1 FROM user_profile up WHERE up.user_id = u_id) THEN
            v_profile_id := gen_random_uuid();
            INSERT INTO user_profile (id, guid, created_at, updated_at, user_id, display_name, locale, job_title, bio)
            VALUES (
                v_profile_id,
                replace(v_profile_id::text, '-', ''),
                v_now, v_now,
                u_id,
                'Display ' || left(replace(u_id::text, '-', ''), 8),
                'en_NG',
                NULL,
                'Seeded profile'
            );
        END IF;
    END LOOP;

    -- Org members
    INSERT INTO org_members (id, guid, created_at, updated_at, organization_id, user_id, invitation_status, invited_at, joined_at)
    SELECT gen_random_uuid(), replace(gen_random_uuid()::text, '-', ''), v_now, v_now, v_org_id, x.uid, 'PENDING', v_now, NULL
    FROM (VALUES (v_trainer_u1), (v_trainer_u2), (v_trainee_u1), (v_trainee_u2), (v_trainee_u3), (v_trainee_u4), (v_trainee_u5)) AS x(uid)
    WHERE NOT EXISTS (SELECT 1 FROM org_members om WHERE om.organization_id = v_org_id AND om.user_id = x.uid);

    -- Trainers
    FOR tr_idx IN 1..2 LOOP
        u_id := CASE tr_idx WHEN 1 THEN v_trainer_u1 ELSE v_trainer_u2 END;
        IF NOT EXISTS (SELECT 1 FROM trainers t WHERE t.user_id = u_id AND t.org_id = v_org_id) THEN
            v_trainer_row := gen_random_uuid();
            INSERT INTO trainers (id, guid, created_at, updated_at, user_id, org_id, display_name, bio, website_url)
            VALUES (
                v_trainer_row,
                replace(v_trainer_row::text, '-', ''),
                v_now, v_now,
                u_id,
                v_org_id,
                'Trainer ' || tr_idx::text,
                'Bio for trainer ' || tr_idx::text,
                'https://example.com/trainers/' || tr_idx::text
            );
        END IF;
    END LOOP;

    -- Trainees rows
    FOR te_idx IN 1..5 LOOP
        u_id := CASE te_idx
            WHEN 1 THEN v_trainee_u1 WHEN 2 THEN v_trainee_u2 WHEN 3 THEN v_trainee_u3
            WHEN 4 THEN v_trainee_u4 ELSE v_trainee_u5 END;
        IF NOT EXISTS (SELECT 1 FROM trainees tr WHERE tr.user_id = u_id AND tr.organization_id = v_org_id) THEN
            v_trainee_row := gen_random_uuid();
            INSERT INTO trainees (id, guid, created_at, updated_at, user_id, user_profile_id, organization_id, display_name, bio)
            SELECT
                v_trainee_row,
                replace(v_trainee_row::text, '-', ''),
                v_now, v_now,
                u_id,
                up.id,
                v_org_id,
                'Trainee ' || te_idx::text,
                'Seeded trainee bio #' || te_idx::text
            FROM user_profile up WHERE up.user_id = u_id LIMIT 1;
        END IF;
    END LOOP;

    -- Industries (8 codes)
    FOR te_idx IN 1..8 LOOP
        IF NOT EXISTS (SELECT 1 FROM industries i WHERE i.code = 'SEED_IND_' || lpad(te_idx::text, 2, '0')) THEN
            v_ind_id := gen_random_uuid();
            INSERT INTO industries (id, guid, created_at, updated_at, name, code, description)
            VALUES (
                v_ind_id,
                replace(v_ind_id::text, '-', ''),
                v_now, v_now,
                'Seed Industry ' || te_idx::text,
                'SEED_IND_' || lpad(te_idx::text, 2, '0'),
                'Auto-generated taxonomy row'
            );
        END IF;
    END LOOP;

    -- User industry interests (3 per trainee)
    FOR te_idx IN 1..5 LOOP
        u_id := CASE te_idx
            WHEN 1 THEN v_trainee_u1 WHEN 2 THEN v_trainee_u2 WHEN 3 THEN v_trainee_u3
            WHEN 4 THEN v_trainee_u4 ELSE v_trainee_u5 END;
        FOR q_idx IN 0..2 LOOP
            SELECT i.id INTO v_ind_id FROM industries i
            WHERE i.code = 'SEED_IND_' || lpad(((te_idx + q_idx - 1) % 8 + 1)::text, 2, '0')
            LIMIT 1;
            IF v_ind_id IS NOT NULL AND NOT EXISTS (
                SELECT 1 FROM user_industry_interest x WHERE x.user_id = u_id AND x.industry_id = v_ind_id
            ) THEN
                INSERT INTO user_industry_interest (id, guid, created_at, updated_at, user_id, industry_id)
                VALUES (gen_random_uuid(), replace(gen_random_uuid()::text, '-', ''), v_now, v_now, u_id, v_ind_id);
            END IF;
        END LOOP;
    END LOOP;

    -- ---- Trainings + curriculum + quizzes -----------------------------------
    FOR t_idx IN 1..12 LOOP
        v_status := CASE WHEN t_idx <= 10 THEN 'PUBLISHED' ELSE 'DRAFT' END;
        SELECT t.id INTO v_trainer_row FROM trainers t
        WHERE t.org_id = v_org_id
        ORDER BY t.created_at NULLS LAST, t.id
        LIMIT 1 OFFSET (t_idx % 2);
        IF v_trainer_row IS NULL THEN
            RAISE NOTICE 'No trainer row; skipping training %', t_idx;
            CONTINUE;
        END IF;

        v_slug := 'afefe-seed-training-' || lpad(t_idx::text, 2, '0');
        IF EXISTS (SELECT 1 FROM trainings tr WHERE tr.org_id = v_org_id AND tr.slug = v_slug) THEN
            CONTINUE;
        END IF;

        v_title := 'Seed Programme ' || t_idx::text || ' — ' ||
            CASE (t_idx % 4)
                WHEN 0 THEN 'Leadership'
                WHEN 1 THEN 'Data Skills'
                WHEN 2 THEN 'Communication'
                ELSE 'Operations'
            END;

        v_tid := gen_random_uuid();
        INSERT INTO trainings (
            id, guid, created_at, updated_at,
            org_id, trainer_id,
            cover_image_url, description, requirement, price, currency, tags, learning_outcome_json_list,
            title, title_hash, slug, summary, level, language, status,
            estimated_minutes, price_cents, free, rating, reviews, published_at, has_certificate, programme_id
        ) VALUES (
            v_tid,
            replace(v_tid::text, '-', ''),
            v_now, v_now,
            v_org_id,
            v_trainer_row,
            'https://picsum.photos/seed/' || t_idx::text || '/800/450',
            'Long description for programme ' || t_idx::text || '. Lorem ipsum dolor sit amet, consectetur adipiscing elit.',
            '[]',
            CASE WHEN t_idx % 3 = 0 THEN 49.99 ELSE 0 END,
            'NGN',
            '["seed","demo","bulk"]',
            '[{"title":"Outcome A","description":"Demonstrate A"},{"title":"Outcome B","description":"Demonstrate B"}]',
            v_title,
            replace(v_title, ' ', ''),
            v_slug,
            'Summary for programme ' || t_idx::text,
            (ARRAY['BEGINNER', 'INTERMEDIATE', 'ADVANCED'])[1 + (t_idx % 3)],
            'en',
            v_status,
            60 + (t_idx * 15),
            CASE WHEN t_idx % 3 = 0 THEN 4999 ELSE 0 END,
            (t_idx % 3) <> 0,
            3.5 + ((t_idx % 3))::numeric,
            (t_idx * 2) % 17,
            CASE WHEN v_status = 'PUBLISHED' THEN v_now ELSE NULL END,
            true,
            'PROG-' || t_idx::text
        );

        n_months := CASE WHEN t_idx <= 3 THEN 4 WHEN t_idx <= 7 THEN 3 ELSE 2 END;
        n_weeks := CASE WHEN t_idx <= 5 THEN 3 ELSE 2 END;
        n_items := CASE WHEN t_idx <= 4 THEN 4 ELSE 3 END;

        FOR m_idx IN 1..n_months LOOP
            v_mid := gen_random_uuid();
            INSERT INTO training_months (id, guid, created_at, updated_at, training_id, position, title, summary)
            VALUES (
                v_mid,
                replace(v_mid::text, '-', ''),
                v_now, v_now,
                v_tid,
                m_idx,
                'Month ' || m_idx::text || ' — Themes & goals',
                'Summary for month ' || m_idx::text || ' of programme ' || t_idx::text
            );

            FOR w_idx IN 1..n_weeks LOOP
                v_wid := gen_random_uuid();
                INSERT INTO training_weeks (id, guid, created_at, updated_at, month_id, position, title, co_authoring_enabled)
                VALUES (
                    v_wid,
                    replace(v_wid::text, '-', ''),
                    v_now, v_now,
                    v_mid,
                    w_idx,
                    'Week ' || w_idx::text || ' — Practice & review',
                    (t_idx + w_idx) % 4 = 0
                );

                FOR it_idx IN 1..n_items LOOP
                    v_fmt := (ARRAY['VIDEO', 'READING', 'MIXED', 'PRACTICE_QUIZ'])[1 + ((m_idx + w_idx + it_idx) % 4)];
                    v_iid := gen_random_uuid();
                    INSERT INTO training_content_items (
                        id, guid, created_at, updated_at,
                        week_id, position, title, item_format, duration_seconds, is_published, has_quiz
                    ) VALUES (
                        v_iid,
                        replace(v_iid::text, '-', ''),
                        v_now, v_now,
                        v_wid,
                        it_idx,
                        'Lesson M' || m_idx::text || ' W' || w_idx::text || ' L' || it_idx::text,
                        v_fmt,
                        300 + (it_idx * 60),
                        true,
                        (it_idx % 2) = 0
                    );

                    n_blocks := 2 + (it_idx % 2);
                    FOR bl_idx IN 1..n_blocks LOOP
                        v_blk_type := (ARRAY['READING', 'VIDEO_EMBED', 'IMAGE', 'RESOURCE_FILE'])[1 + ((bl_idx + it_idx) % 4)];
                        v_bid := gen_random_uuid();
                        INSERT INTO training_content_blocks (
                            id, guid, created_at, updated_at,
                            content_item_id, sort_order, block_type, payload_json, resource_url, trainee_quiz_id, estimated_duration_seconds
                        ) VALUES (
                            v_bid,
                            replace(v_bid::text, '-', ''),
                            v_now, v_now,
                            v_iid,
                            bl_idx,
                            v_blk_type,
                            '{"body":"Seeded block for programme ' || t_idx::text || ' lesson ' || it_idx::text || ' block ' || bl_idx::text || '"}',
                            CASE v_blk_type::text
                                WHEN 'VIDEO_EMBED' THEN
                                    'https://videos.example.com/seed/p' || t_idx::text || '-m' || m_idx::text || '-w' || w_idx::text || '-l' || it_idx::text || '-b' || bl_idx::text || '.m3u8'
                                WHEN 'IMAGE' THEN
                                    'https://picsum.photos/seed/p' || t_idx::text || m_idx::text || w_idx::text || it_idx::text || bl_idx::text || '/1200/800'
                                WHEN 'RESOURCE_FILE' THEN
                                    'https://www.w3.org/WAI/ER/tests/xhtml/testfiles/resources/pdf/dummy.pdf'
                                ELSE
                                    'https://example.com/readings/p' || t_idx::text || '-m' || m_idx::text || '-w' || w_idx::text || '-l' || it_idx::text || '-b' || bl_idx::text || '.html'
                            END,
                            NULL,
                            (120 * bl_idx)::bigint
                        );
                    END LOOP;
                END LOOP;
            END LOOP;
        END LOOP;

        IF v_status = 'PUBLISHED' THEN
            FOR q_idx IN 1..2 LOOP
                v_qid := gen_random_uuid();
                INSERT INTO trainee_quizzes (id, guid, created_at, updated_at, training_id, title, attempt_limit, passing_score_percent)
                VALUES (
                    v_qid,
                    replace(v_qid::text, '-', ''),
                    v_now, v_now,
                    v_tid,
                    'Quiz ' || q_idx::text || ' — Programme ' || t_idx::text,
                    5,
                    70 + q_idx * 5
                );

                FOR bl_idx IN 1..3 LOOP
                    v_qn_id := gen_random_uuid();
                    INSERT INTO trainee_quiz_questions (id, guid, created_at, updated_at, quiz_id, position, prompt)
                    VALUES (
                        v_qn_id,
                        replace(v_qn_id::text, '-', ''),
                        v_now, v_now,
                        v_qid,
                        bl_idx,
                        'Question ' || bl_idx::text || ' for quiz ' || q_idx::text || ' (programme ' || t_idx::text || ')?'
                    );

                    v_opt_correct := gen_random_uuid();
                    INSERT INTO trainee_quiz_options (id, guid, created_at, updated_at, question_id, position, label, correct)
                    VALUES
                        (v_opt_correct, replace(v_opt_correct::text, '-', ''), v_now, v_now, v_qn_id, 1, 'Correct answer for Q' || bl_idx::text, true),
                        (gen_random_uuid(), replace(gen_random_uuid()::text, '-', ''), v_now, v_now, v_qn_id, 2, 'Distractor A', false),
                        (gen_random_uuid(), replace(gen_random_uuid()::text, '-', ''), v_now, v_now, v_qn_id, 3, 'Distractor B', false);
                END LOOP;
            END LOOP;
        END IF;
    END LOOP;

    -- ---- Enrollments ---------------------------------------------------------
    FOR t_idx IN 1..10 LOOP
        SELECT tr.id INTO v_tid FROM trainings tr
        WHERE tr.org_id = v_org_id AND tr.status = 'PUBLISHED'
        ORDER BY tr.slug
        LIMIT 1 OFFSET t_idx - 1;
        EXIT WHEN v_tid IS NULL;

        IF NOT EXISTS (SELECT 1 FROM training_enrollments e WHERE e.user_id = v_trainee_u1 AND e.training_id = v_tid) THEN
            v_enr := gen_random_uuid();
            INSERT INTO training_enrollments (id, guid, created_at, updated_at, org_id, user_id, training_id, current_month_id, status, progress_percent, started_at, completed_at)
            SELECT
                v_enr,
                replace(v_enr::text, '-', ''),
                v_now, v_now,
                v_org_id,
                v_trainee_u1,
                v_tid,
                (SELECT tm.id FROM training_months tm WHERE tm.training_id = v_tid ORDER BY tm.position LIMIT 1),
                CASE WHEN t_idx % 5 = 0 THEN 'COMPLETED' ELSE 'IN_PROGRESS' END,
                (t_idx * 7) % 100,
                v_now - (t_idx::text || ' days')::interval,
                CASE WHEN t_idx % 5 = 0 THEN v_now ELSE NULL END;
        END IF;

        IF t_idx <= 7 AND NOT EXISTS (SELECT 1 FROM training_enrollments e WHERE e.user_id = v_trainee_u2 AND e.training_id = v_tid) THEN
            v_enr := gen_random_uuid();
            INSERT INTO training_enrollments (id, guid, created_at, updated_at, org_id, user_id, training_id, current_month_id, status, progress_percent, started_at, completed_at)
            SELECT
                v_enr,
                replace(v_enr::text, '-', ''),
                v_now, v_now,
                v_org_id,
                v_trainee_u2,
                v_tid,
                (SELECT tm.id FROM training_months tm WHERE tm.training_id = v_tid ORDER BY tm.position LIMIT 1),
                'IN_PROGRESS',
                (t_idx * 3) % 100,
                v_now,
                NULL;
        END IF;

        IF t_idx % 2 = 1 AND NOT EXISTS (SELECT 1 FROM training_enrollments e WHERE e.user_id = v_trainee_u3 AND e.training_id = v_tid) THEN
            v_enr := gen_random_uuid();
            INSERT INTO training_enrollments (id, guid, created_at, updated_at, org_id, user_id, training_id, current_month_id, status, progress_percent, started_at, completed_at)
            SELECT
                v_enr,
                replace(v_enr::text, '-', ''),
                v_now, v_now,
                v_org_id,
                v_trainee_u3,
                v_tid,
                (SELECT tm.id FROM training_months tm WHERE tm.training_id = v_tid ORDER BY tm.position LIMIT 1),
                'ENROLLED',
                0,
                NULL,
                NULL;
        END IF;

        -- Trainee4: even-index programmes 2,4,6,8,10
        IF t_idx % 2 = 0 AND NOT EXISTS (SELECT 1 FROM training_enrollments e WHERE e.user_id = v_trainee_u4 AND e.training_id = v_tid) THEN
            v_enr := gen_random_uuid();
            INSERT INTO training_enrollments (id, guid, created_at, updated_at, org_id, user_id, training_id, current_month_id, status, progress_percent, started_at, completed_at)
            SELECT
                v_enr,
                replace(v_enr::text, '-', ''),
                v_now, v_now,
                v_org_id,
                v_trainee_u4,
                v_tid,
                (SELECT tm.id FROM training_months tm WHERE tm.training_id = v_tid ORDER BY tm.position LIMIT 1),
                'IN_PROGRESS',
                (t_idx * 11) % 100,
                v_now,
                NULL;
        END IF;

        -- Trainee5: first 4 programmes
        IF t_idx <= 4 AND NOT EXISTS (SELECT 1 FROM training_enrollments e WHERE e.user_id = v_trainee_u5 AND e.training_id = v_tid) THEN
            v_enr := gen_random_uuid();
            INSERT INTO training_enrollments (id, guid, created_at, updated_at, org_id, user_id, training_id, current_month_id, status, progress_percent, started_at, completed_at)
            SELECT
                v_enr,
                replace(v_enr::text, '-', ''),
                v_now, v_now,
                v_org_id,
                v_trainee_u5,
                v_tid,
                (SELECT tm.id FROM training_months tm WHERE tm.training_id = v_tid ORDER BY tm.position LIMIT 1),
                'IN_PROGRESS',
                (t_idx * 13) % 100,
                v_now,
                NULL;
        END IF;
    END LOOP;

    -- Progress for trainee1 enrollments
    FOR v_enr IN SELECT e.id FROM training_enrollments e WHERE e.user_id = v_trainee_u1 AND e.org_id = v_org_id
    LOOP
        FOR v_iid IN
            SELECT ci.id
            FROM training_content_items ci
            JOIN training_weeks w ON w.id = ci.week_id
            JOIN training_months m ON m.id = w.month_id
            JOIN training_enrollments e ON e.training_id = m.training_id
            WHERE e.id = v_enr
            ORDER BY m.position, w.position, ci.position
            LIMIT 6
        LOOP
            IF NOT EXISTS (SELECT 1 FROM training_content_item_progress p WHERE p.enrollment_id = v_enr AND p.content_item_id = v_iid) THEN
                v_prog_st := (ARRAY['NOT_STARTED', 'IN_PROGRESS', 'COMPLETED'])[1 + (floor(random() * 3)::int)];
                INSERT INTO training_content_item_progress (
                    id, guid, created_at, updated_at, enrollment_id, content_item_id,
                    progress_status, progress_percent, last_accessed_at, current_block_id
                )
                SELECT
                    gen_random_uuid(),
                    replace(gen_random_uuid()::text, '-', ''),
                    v_now, v_now,
                    v_enr,
                    v_iid,
                    v_prog_st,
                    (10 + (random() * 80)::int),
                    v_now,
                    (SELECT b.id FROM training_content_blocks b WHERE b.content_item_id = v_iid ORDER BY b.sort_order LIMIT 1);
            END IF;

            FOR v_bid IN SELECT b.id FROM training_content_blocks b WHERE b.content_item_id = v_iid ORDER BY b.sort_order LIMIT 2
            LOOP
                IF NOT EXISTS (SELECT 1 FROM training_content_block_progress bp WHERE bp.enrollment_id = v_enr AND bp.block_id = v_bid) THEN
                    v_prog_st := (ARRAY['NOT_STARTED', 'IN_PROGRESS', 'COMPLETED'])[1 + (floor(random() * 3)::int)];
                    INSERT INTO training_content_block_progress (
                        id, guid, created_at, updated_at, enrollment_id, block_id,
                        last_position_seconds, last_viewed_at, completed_at, progress_status
                    )
                    VALUES (
                        gen_random_uuid(),
                        replace(gen_random_uuid()::text, '-', ''),
                        v_now, v_now,
                        v_enr,
                        v_bid,
                        (30 + (random() * 200)::int)::int,
                        v_now,
                        CASE WHEN random() > 0.6 THEN v_now ELSE NULL END,
                        v_prog_st
                    );
                END IF;
            END LOOP;
        END LOOP;
    END LOOP;

    -- Views
    FOR r_idx, t_idx, te_idx IN
        SELECT * FROM (VALUES
            (1, 1, 1), (2, 2, 1), (3, 3, 2), (4, 4, 2),
            (5, 5, 3), (6, 6, 4), (7, 7, 5)
        ) AS s(r_idx, t_idx, te_idx)
    LOOP
        SELECT tr.id INTO v_tid FROM trainings tr
        WHERE tr.org_id = v_org_id AND tr.status = 'PUBLISHED'
        ORDER BY tr.slug
        LIMIT 1 OFFSET t_idx - 1;
        v_trainee_uid := CASE te_idx
            WHEN 1 THEN v_trainee_u1 WHEN 2 THEN v_trainee_u2 WHEN 3 THEN v_trainee_u3
            WHEN 4 THEN v_trainee_u4 ELSE v_trainee_u5 END;
        IF v_tid IS NOT NULL THEN
            INSERT INTO user_training_views (id, guid, created_at, updated_at, org_id, user_id, training_id, viewed_at)
            VALUES (gen_random_uuid(), replace(gen_random_uuid()::text, '-', ''), v_now, v_now, v_org_id, v_trainee_uid, v_tid, v_now)
            ON CONFLICT (user_id, training_id) DO UPDATE SET viewed_at = EXCLUDED.viewed_at;
        END IF;
    END LOOP;

    -- Wishlists
    FOR t_idx, te_idx IN SELECT * FROM (VALUES (2, 4), (4, 5), (6, 1), (8, 2)) AS w(t_idx, te_idx)
    LOOP
        SELECT tr.id INTO v_tid FROM trainings tr
        WHERE tr.org_id = v_org_id AND tr.status = 'PUBLISHED'
        ORDER BY tr.slug
        LIMIT 1 OFFSET t_idx - 1;
        v_trainee_uid := CASE te_idx WHEN 1 THEN v_trainee_u1 WHEN 2 THEN v_trainee_u2 WHEN 4 THEN v_trainee_u4 ELSE v_trainee_u5 END;
        IF v_tid IS NOT NULL THEN
            INSERT INTO training_wish_lists (id, guid, created_at, updated_at, org_id, user_id, training_id)
            VALUES (gen_random_uuid(), replace(gen_random_uuid()::text, '-', ''), v_now, v_now, v_org_id, v_trainee_uid, v_tid)
            ON CONFLICT (user_id, training_id) DO NOTHING;
        END IF;
    END LOOP;

    -- Ratings
    FOR t_idx, te_idx, q_idx IN SELECT * FROM (VALUES (1, 1, 5), (2, 2, 4), (3, 3, 5), (1, 4, 3)) AS r(t_idx, te_idx, q_idx)
    LOOP
        SELECT tr.id INTO v_tid FROM trainings tr
        WHERE tr.org_id = v_org_id AND tr.status = 'PUBLISHED'
        ORDER BY tr.slug
        LIMIT 1 OFFSET t_idx - 1;
        v_trainee_uid := CASE te_idx WHEN 1 THEN v_trainee_u2 WHEN 2 THEN v_trainee_u3 WHEN 3 THEN v_trainee_u4 ELSE v_trainee_u5 END;
        IF v_tid IS NOT NULL THEN
            INSERT INTO training_ratings (id, guid, created_at, updated_at, org_id, user_id, training_id, rating, review, rated_at, review_updated_at)
            VALUES (gen_random_uuid(), replace(gen_random_uuid()::text, '-', ''), v_now, v_now, v_org_id, v_trainee_uid, v_tid, q_idx, 'Seeded review text.', v_now, NULL)
            ON CONFLICT (user_id, training_id) DO NOTHING;
        END IF;
    END LOOP;

    -- Quiz attempt (first enrollment of trainee1 + first quiz)
    v_enr := NULL;
    v_qid := NULL;
    v_qn_id := NULL;
    v_opt_correct := NULL;
    SELECT e.id, q.id INTO v_enr, v_qid
    FROM training_enrollments e
    JOIN trainings tr ON tr.id = e.training_id AND tr.status = 'PUBLISHED'
    JOIN trainee_quizzes q ON q.training_id = tr.id
    WHERE e.user_id = v_trainee_u1 AND e.org_id = v_org_id
    ORDER BY e.created_at, q.created_at
    LIMIT 1;

    v_qn_id := NULL;
    v_opt_correct := NULL;
    IF v_enr IS NOT NULL AND v_qid IS NOT NULL THEN
        SELECT qq.id, o.id INTO v_qn_id, v_opt_correct
        FROM trainee_quiz_questions qq
        JOIN trainee_quiz_options o ON o.question_id = qq.id AND o.correct = true
        WHERE qq.quiz_id = v_qid
        ORDER BY qq.position, o.position
        LIMIT 1;
    END IF;

    IF v_enr IS NOT NULL AND v_qid IS NOT NULL AND v_qn_id IS NOT NULL AND v_opt_correct IS NOT NULL THEN
        IF NOT EXISTS (SELECT 1 FROM trainee_quiz_attempts a WHERE a.enrollment_id = v_enr AND a.quiz_id = v_qid AND a.attempt_number = 1) THEN
            INSERT INTO trainee_quiz_attempts (id, guid, created_at, updated_at, enrollment_id, quiz_id, attempt_number, attempt_status, score_percent, started_at, submitted_at, answers_json)
            VALUES (
                gen_random_uuid(),
                replace(gen_random_uuid()::text, '-', ''),
                v_now, v_now,
                v_enr,
                v_qid,
                1,
                'GRADED',
                85,
                v_now - interval '2 hours',
                v_now - interval '1 hour',
                json_build_object(v_qn_id::text, v_opt_correct::text)::text
            );
        END IF;
    END IF;

    -- Extra ratings from trainers (one per trainer per programme slot 4–9)
    FOR t_idx IN 4..9 LOOP
        SELECT tr.id INTO v_tid FROM trainings tr
        WHERE tr.org_id = v_org_id AND tr.status = 'PUBLISHED'
        ORDER BY tr.slug
        LIMIT 1 OFFSET t_idx - 1;
        u_id := CASE WHEN t_idx % 2 = 0 THEN v_trainer_u1 ELSE v_trainer_u2 END;
        IF v_tid IS NOT NULL THEN
            INSERT INTO training_ratings (id, guid, created_at, updated_at, org_id, user_id, training_id, rating, review, rated_at, review_updated_at)
            VALUES (gen_random_uuid(), replace(gen_random_uuid()::text, '-', ''), v_now, v_now, v_org_id, u_id, v_tid, 4 + (t_idx % 2), 'Trainer perspective on this programme.', v_now, NULL)
            ON CONFLICT (user_id, training_id) DO NOTHING;
        END IF;
    END LOOP;

    -- Extra wishlists across trainees and programmes
    FOR t_idx, te_idx IN SELECT * FROM (VALUES (3, 3), (5, 5), (7, 1), (9, 3), (10, 5)) AS x(t_idx, te_idx)
    LOOP
        SELECT tr.id INTO v_tid FROM trainings tr
        WHERE tr.org_id = v_org_id AND tr.status = 'PUBLISHED'
        ORDER BY tr.slug
        LIMIT 1 OFFSET t_idx - 1;
        v_trainee_uid := CASE te_idx WHEN 1 THEN v_trainee_u1 WHEN 3 THEN v_trainee_u3 WHEN 5 THEN v_trainee_u5 ELSE v_trainee_u2 END;
        IF v_tid IS NOT NULL THEN
            INSERT INTO training_wish_lists (id, guid, created_at, updated_at, org_id, user_id, training_id)
            VALUES (gen_random_uuid(), replace(gen_random_uuid()::text, '-', ''), v_now, v_now, v_org_id, v_trainee_uid, v_tid)
            ON CONFLICT (user_id, training_id) DO NOTHING;
        END IF;
    END LOOP;

    -- Certificates for trainee1 completed enrollments
    FOR v_enr IN SELECT e.id FROM training_enrollments e
        WHERE e.user_id = v_trainee_u1 AND e.org_id = v_org_id AND e.status = 'COMPLETED'
    LOOP
        SELECT e.training_id, e.user_id INTO v_tid, v_trainee_uid FROM training_enrollments e WHERE e.id = v_enr;
        IF NOT EXISTS (SELECT 1 FROM training_certificates c WHERE c.user_id = v_trainee_uid AND c.training_id = v_tid) THEN
            INSERT INTO training_certificates (id, guid, created_at, updated_at, org_id, user_id, training_id, enrollment_id, issued_at, certificate_url)
            VALUES (
                gen_random_uuid(),
                replace(gen_random_uuid()::text, '-', ''),
                v_now, v_now,
                v_org_id,
                v_trainee_uid,
                v_tid,
                v_enr,
                v_now,
                'https://example.com/certs/' || replace(v_tid::text, '-', '') || '.pdf'
            );
        END IF;
    END LOOP;

    RAISE NOTICE 'Seed complete. Org %', v_org_id;
END $$;

COMMIT;
