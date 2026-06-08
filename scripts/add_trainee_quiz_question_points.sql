-- Add per-question points for trainee quizzes (defaults handled in application code).
ALTER TABLE trainee_quiz_questions
    ADD COLUMN IF NOT EXISTS points INTEGER;

UPDATE trainee_quiz_questions
SET points = 1
WHERE points IS NULL;
