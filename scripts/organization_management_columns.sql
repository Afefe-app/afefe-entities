-- Organization profile/contact/branding columns + INACTIVE status (enum stored as VARCHAR by JPA STRING).
-- Run when deploying Organization Management APIs.

ALTER TABLE organization
    ADD COLUMN IF NOT EXISTS contact_email VARCHAR(320);
ALTER TABLE organization
    ADD COLUMN IF NOT EXISTS website_url VARCHAR(2048);
ALTER TABLE organization
    ADD COLUMN IF NOT EXISTS address_line TEXT;
ALTER TABLE organization
    ADD COLUMN IF NOT EXISTS description TEXT;
ALTER TABLE organization
    ADD COLUMN IF NOT EXISTS logo_url VARCHAR(2048);
ALTER TABLE organization
    ADD COLUMN IF NOT EXISTS primary_color_hex VARCHAR(16);
ALTER TABLE organization
    ADD COLUMN IF NOT EXISTS secondary_color_hex VARCHAR(16);
ALTER TABLE organization
    ADD COLUMN IF NOT EXISTS tertiary_color_hex VARCHAR(16);
