-- =============================================================================
-- Seed upcoming calendar_event rows for fe.trainee.alt1@afefe.test
-- At least 50 per event_type: DEFAULT, PHYSICAL_CLASS, VIDEO_CONFERENCING
-- =============================================================================
-- Prerequisites:
--   ALTER on calendar_event_event_type_check (PHYSICAL_CLASS) — applied below
--
-- Run:
--   psql -h HOST -p PORT -U app_mgt_user -d afefe_mgt -v ON_ERROR_STOP=1 \
--     -f seed_calendar_events_fe_trainee_alt1.sql
-- =============================================================================

BEGIN;

-- Allow all CalendarEventType enum values in PostgreSQL
ALTER TABLE calendar_event DROP CONSTRAINT IF EXISTS calendar_event_event_type_check;
ALTER TABLE calendar_event ADD CONSTRAINT calendar_event_event_type_check
    CHECK (event_type::text = ANY (ARRAY[
        'DEFAULT'::character varying,
        'VIDEO_CONFERENCING'::character varying,
        'PHYSICAL_CLASS'::character varying
    ]::text[]));

DO $$
DECLARE
    v_trainee_id      uuid := 'c0fe0000-0000-4000-8000-000000000022'::uuid;
    v_trainer_u1      uuid := 'c0fe0000-0000-4000-8000-000000000011'::uuid;
    v_trainer_u2      uuid := 'c0fe0000-0000-4000-8000-000000000012'::uuid;
    v_now             timestamptz := now();
    v_today           date := CURRENT_DATE;

    v_training_ids    uuid[];
    v_training_id     uuid;
    v_trainer_id      uuid;
    v_i               int;
    v_seq             int;
    v_event_date      date;
    v_from_time       time;
    v_to_time         time;
    v_event_type      text;
    v_video           boolean;
    v_add_location    boolean;
    v_location        text;
    v_title           text;
    v_description     text;
    v_types           text[] := ARRAY['DEFAULT', 'PHYSICAL_CLASS', 'VIDEO_CONFERENCING'];
    v_per_type        int := 50;
    v_type_idx        int;
BEGIN
    SELECT ARRAY_AGG(te.training_id ORDER BY te.started_at NULLS LAST, te.id)
    INTO v_training_ids
    FROM training_enrollments te
    WHERE te.user_id = v_trainee_id;

    IF v_training_ids IS NULL OR array_length(v_training_ids, 1) IS NULL THEN
        RAISE EXCEPTION 'No training enrollments for trainee %', v_trainee_id;
    END IF;

    RAISE NOTICE 'Seeding calendar events for % training(s)', array_length(v_training_ids, 1);

    FOREACH v_event_type IN ARRAY v_types
    LOOP
        v_video := (v_event_type = 'VIDEO_CONFERENCING');
        v_add_location := (v_event_type IN ('DEFAULT', 'PHYSICAL_CLASS'));

        FOR v_i IN 1..v_per_type LOOP
            v_training_id := v_training_ids[1 + ((v_i - 1) % array_length(v_training_ids, 1))];
            v_trainer_id := CASE WHEN v_i % 2 = 0 THEN v_trainer_u1 ELSE v_trainer_u2 END;
            v_event_date := v_today + (v_i + CASE v_event_type
                WHEN 'DEFAULT' THEN 0
                WHEN 'PHYSICAL_CLASS' THEN 60
                ELSE 120
            END);
            v_from_time := make_time(8 + (v_i % 8), (v_i * 7) % 60, 0);
            v_to_time := v_from_time + interval '2 hours';

            IF v_event_type = 'VIDEO_CONFERENCING' THEN
                v_location := 'Microsoft Teams — Room ' || v_i;
                v_title := format('Virtual class %s — session %s', v_i, v_event_type);
                v_description := format('Seeded virtual upcoming session #%s for FE trainee alt1.', v_i);
            ELSIF v_event_type = 'PHYSICAL_CLASS' THEN
                v_location := format('Ocean Academy — Hall %s', chr(64 + ((v_i - 1) % 26) + 1));
                v_title := format('Physical class %s — session %s', v_i, v_event_type);
                v_description := format('Seeded physical upcoming session #%s for FE trainee alt1.', v_i);
            ELSE
                v_location := format('Ocean Academy — Room %s', ((v_i - 1) % 12) + 1);
                v_title := format('Class %s — session %s', v_i, v_event_type);
                v_description := format('Seeded default upcoming session #%s for FE trainee alt1.', v_i);
            END IF;

            INSERT INTO calendar_event (
                id, guid, created_at, updated_at, cover_photo, date, from_time, to_time, time_zone,
                assigned_course_id, assigned_training_id, title, description, location,
                event_marker, add_location, video_conferencing, event_type, status, price, currency,
                created_by_id, updated_by_id,
                attendance_opens_minutes_before, attendance_requires_live_location,
                session_latitude, session_longitude, attendance_geofence_radius_meters
            ) VALUES (
                gen_random_uuid(),
                replace(gen_random_uuid()::text, '-', ''),
                v_now, v_now,
                CASE WHEN v_i % 5 = 0 THEN 'https://picsum.photos/seed/fe-cal-' || v_event_type || '-' || v_i || '/640/360' ELSE '' END,
                v_event_date,
                v_from_time,
                v_to_time::time,
                'AFRICA_LAGOS',
                NULL,
                v_training_id,
                v_title,
                v_description,
                v_location,
                false,
                v_add_location,
                v_video,
                v_event_type,
                'PENDING',
                0,
                'NGN',
                v_trainer_id,
                v_trainer_id,
                10,
                false,
                CASE WHEN v_add_location THEN 6.5244 + (v_i % 10) * 0.001 ELSE NULL END,
                CASE WHEN v_add_location THEN 3.3792 + (v_i % 10) * 0.001 ELSE NULL END,
                CASE WHEN v_add_location THEN 150 ELSE NULL END
            );
        END LOOP;

        RAISE NOTICE 'Inserted % rows for event_type=%', v_per_type, v_event_type;
    END LOOP;
END $$;

-- Verification for trainee org-scoped upcoming training events
DO $$
DECLARE
    v_trainee_id uuid := 'c0fe0000-0000-4000-8000-000000000022'::uuid;
    v_today date := CURRENT_DATE;
    r record;
BEGIN
    FOR r IN
        SELECT ce.event_type, COUNT(*) AS cnt
        FROM calendar_event ce
        JOIN training_enrollments te ON te.training_id = ce.assigned_training_id AND te.user_id = v_trainee_id
        WHERE ce.assigned_training_id IS NOT NULL
          AND ce.assigned_course_id IS NULL
          AND ce.date >= v_today
          AND ce.status = 'PENDING'
        GROUP BY ce.event_type
        ORDER BY ce.event_type
    LOOP
        RAISE NOTICE 'VERIFY event_type=% count=%', r.event_type, r.cnt;
        IF r.cnt < 50 THEN
            RAISE EXCEPTION 'Expected at least 50 upcoming PENDING events for %, got %', r.event_type, r.cnt;
        END IF;
    END LOOP;
END $$;

COMMIT;
