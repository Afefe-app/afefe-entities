-- PostgreSQL: add canonical content URL column for training blocks.
-- Run once per environment before deploying JPA entity that maps resourceUrl.

ALTER TABLE training_content_blocks
    ADD COLUMN IF NOT EXISTS resource_url TEXT NULL;

COMMENT ON COLUMN training_content_blocks.resource_url IS
    'Primary URL served to clients for this block (video, image, document, etc.); complements payload_json.';
