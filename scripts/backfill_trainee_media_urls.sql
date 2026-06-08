-- Backfill broken trainee content block media URLs (example.com / videos.example.com placeholders).
UPDATE training_content_blocks
SET resource_url = 'https://filesamples.com/samples/video/mp4/sample_640x360.mp4'
WHERE resource_url IS NOT NULL
  AND (
        resource_url LIKE '%videos.example.com%'
        OR resource_url LIKE '%commondatastorage.googleapis.com%'
        OR resource_url LIKE '%storage.googleapis.com%'
        OR resource_url LIKE '%.m3u8'
      )
  AND block_type IN ('VIDEO_EMBED', 'VIDEO');

UPDATE training_content_blocks
SET resource_url = 'https://www.w3schools.com/html/html_basic.asp'
WHERE resource_url IS NOT NULL
  AND resource_url LIKE '%example.com%'
  AND block_type = 'READING';

UPDATE training_content_blocks
SET resource_url = 'https://www.w3schools.com/html/html_basic.asp'
WHERE resource_url IS NOT NULL
  AND resource_url LIKE '%example.com%'
  AND block_type NOT IN ('VIDEO_EMBED', 'VIDEO');

-- Backfill certificate PDF links.
UPDATE training_certificates
SET certificate_url = 'https://www.w3.org/WAI/ER/tests/xhtml/testfiles/resources/pdf/dummy.pdf',
    file_name = COALESCE(file_name, 'certificate.pdf'),
    file_size_bytes = COALESCE(file_size_bytes, 13264)
WHERE certificate_url IS NULL
   OR certificate_url LIKE '%example.com%';
