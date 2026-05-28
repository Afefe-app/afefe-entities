-- Convert user_activity enum columns from ordinal smallint to VARCHAR (matches @Enumerated(STRING)).
-- Run once when deploying UserActivity mapping fix.

ALTER TABLE user_activity DROP CONSTRAINT IF EXISTS user_activity_action_type_check;
ALTER TABLE user_activity DROP CONSTRAINT IF EXISTS user_activity_user_type_check;

ALTER TABLE user_activity
    ALTER COLUMN action_type TYPE VARCHAR(40) USING (
        CASE action_type
            WHEN 0 THEN 'COURSE_PURCHASE'
            WHEN 1 THEN 'REVIEW_RATING'
            ELSE NULL
        END
    );

ALTER TABLE user_activity
    ALTER COLUMN user_type TYPE VARCHAR(40) USING (
        CASE user_type
            WHEN 0 THEN 'PLATFORM_LEARNER'
            WHEN 1 THEN 'PLATFORM_INSTRUCTOR'
            WHEN 2 THEN 'PLATFORM_ADMIN'
            WHEN 3 THEN 'PLATFORM_HR'
            WHEN 4 THEN 'PLATFORM_TRAINER'
            WHEN 5 THEN 'PLATFORM_TRAINEE'
            WHEN 6 THEN 'PLATFORM_ORGANISATION'
            ELSE NULL
        END
    );

-- Align snapshot user_type with the linked user where rows were inconsistent.
UPDATE user_activity ua
SET user_type = u.user_type
FROM users u
WHERE u.id = ua.user_id
  AND (ua.user_type IS NULL OR ua.user_type IS DISTINCT FROM u.user_type);
