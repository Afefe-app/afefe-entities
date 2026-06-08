-- =============================================================================
-- Seed / refresh "happening now" calendar_event rows for fe.trainee.alt1@afefe.test
-- Sessions span roughly 1h before → 2h after current Africa/Lagos time on today.
-- Re-run anytime to keep happeningNow=true useful for FE testing.
-- =============================================================================

BEGIN;

DO $$
DECLARE
    v_trainee_id   uuid := 'c0fe0000-0000-4000-8000-000000000022'::uuid;
    v_trainer_u1   uuid := 'c0fe0000-0000-4000-8000-000000000011'::uuid;
    v_trainer_u2   uuid := 'c0fe0000-0000-4000-8000-000000000012'::uuid;
    v_now          timestamptz := now();
    v_today        date := CURRENT_DATE;
    v_lagos_now    timestamp;
    v_from_time    time;
    v_to_time      time;

    v_training_ids uuid[];
    v_training_id  uuid;
    v_row          record;
BEGIN
    v_lagos_now := (CURRENT_TIMESTAMP AT TIME ZONE 'Africa/Lagos');
    v_from_time := (v_lagos_now - interval '1 hour')::time;
    v_to_time := (v_lagos_now + interval '2 hours')::time;

    SELECT ARRAY_AGG(te.training_id ORDER BY te.started_at NULLS LAST, te.id)
    INTO v_training_ids
    FROM training_enrollments te
    WHERE te.user_id = v_trainee_id;

    IF v_training_ids IS NULL OR array_length(v_training_ids, 1) IS NULL THEN
        RAISE EXCEPTION 'No training enrollments for trainee %', v_trainee_id;
    END IF;

    -- Refresh any prior FE happening-now demo rows for today
    UPDATE calendar_event ce
    SET date = v_today,
        from_time = v_from_time,
        to_time = v_to_time,
        time_zone = 'AFRICA_LAGOS',
        status = 'PENDING',
        updated_at = v_now
    WHERE ce.title LIKE 'FE HAPPENING NOW —%'
      AND ce.assigned_training_id = ANY (v_training_ids);

    FOR v_row IN
        SELECT *
        FROM (VALUES
            ('c0fe0000-0000-4000-8000-000000000401'::uuid, 'FE HAPPENING NOW — Default class (live)', 'DEFAULT', false, false, 'Ocean Academy — Room LIVE-1'),
            ('c0fe0000-0000-4000-8000-000000000402'::uuid, 'FE HAPPENING NOW — Physical class (live)', 'PHYSICAL_CLASS', false, true, 'Ocean Academy — Hall LIVE-A'),
            ('c0fe0000-0000-4000-8000-000000000403'::uuid, 'FE HAPPENING NOW — Virtual class (live)', 'VIDEO_CONFERENCING', true, false, 'Microsoft Teams — FE Live Room')
        ) AS t(id, title, event_type, video_conf, add_loc, location)
    LOOP
        v_training_id := v_training_ids[1 + (ascii(substring(v_row.event_type from 1 for 1)) % array_length(v_training_ids, 1))];

        INSERT INTO calendar_event (
            id, guid, created_at, updated_at, cover_photo, date, from_time, to_time, time_zone,
            assigned_course_id, assigned_training_id, title, description, location,
            event_marker, event_marker_color, add_location, video_conferencing, event_type, status,
            price, currency, created_by_id, updated_by_id,
            attendance_opens_minutes_before, attendance_requires_live_location
        ) VALUES (
            v_row.id,
            replace(v_row.id::text, '-', ''),
            v_now, v_now,
            'https://picsum.photos/seed/fe-happening-now/640/360',
            v_today,
            v_from_time,
            v_to_time,
            'AFRICA_LAGOS',
            NULL,
            v_training_id,
            v_row.title,
            'Live session seeded for FE happeningNow=true filter testing.',
            v_row.location,
            true,
            '#22C55E',
            v_row.add_loc,
            v_row.video_conf,
            v_row.event_type,
            'PENDING',
            0,
            'NGN',
            v_trainer_u1,
            v_trainer_u2,
            15,
            false
        )
        ON CONFLICT (id) DO UPDATE SET
            date = EXCLUDED.date,
            from_time = EXCLUDED.from_time,
            to_time = EXCLUDED.to_time,
            time_zone = EXCLUDED.time_zone,
            title = EXCLUDED.title,
            description = EXCLUDED.description,
            location = EXCLUDED.location,
            event_marker = EXCLUDED.event_marker,
            event_marker_color = EXCLUDED.event_marker_color,
            add_location = EXCLUDED.add_location,
            video_conferencing = EXCLUDED.video_conferencing,
            event_type = EXCLUDED.event_type,
            status = EXCLUDED.status,
            assigned_training_id = EXCLUDED.assigned_training_id,
            updated_at = EXCLUDED.updated_at;
    END LOOP;

    RAISE NOTICE 'Happening-now window (Africa/Lagos): % - % on %', v_from_time, v_to_time, v_today;
END $$;

COMMIT;

-- Quick check
SELECT ce.title, ce.date, ce.from_time, ce.to_time, ce.time_zone, ce.event_type, ce.video_conferencing
FROM calendar_event ce
WHERE ce.title LIKE 'FE HAPPENING NOW —%'
ORDER BY ce.event_type;
