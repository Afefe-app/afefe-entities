-- PostgreSQL: backfill training_content_blocks.resource_url from block_type.
-- Run after add_training_content_block_resource_url.sql. Safe to re-run (only fills NULL/blank).

BEGIN;

UPDATE training_content_blocks
SET resource_url = CASE block_type::text
    WHEN 'VIDEO_EMBED' THEN
        'https://videos.example.com/seed/' || replace(id::text, '-', '') || '.m3u8'
    WHEN 'IMAGE' THEN
        'https://picsum.photos/seed/b' || replace(id::text, '-', '') || '/1200/800'
    WHEN 'RESOURCE_FILE' THEN
        'https://www.w3.org/WAI/ER/tests/xhtml/testfiles/resources/pdf/dummy.pdf'
    WHEN 'READING' THEN
        'https://example.com/readings/' || replace(id::text, '-', '') || '.html'
    WHEN 'PRACTICE_QUIZ' THEN
        'https://example.com/quizzes/preview/' || replace(id::text, '-', '')
    ELSE
        'https://example.com/content/' || replace(id::text, '-', '')
    END
WHERE resource_url IS NULL OR btrim(resource_url) = '';

COMMIT;
