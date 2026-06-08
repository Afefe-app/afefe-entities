-- Populate resource_url for training content blocks missing streamable URLs.
UPDATE training_content_blocks
SET resource_url = 'https://filesamples.com/samples/video/mp4/sample_640x360.mp4'
WHERE (resource_url IS NULL OR resource_url = '' OR resource_url LIKE '%example.com%' OR resource_url LIKE '%videos.example.com%')
  AND block_type IN ('VIDEO_EMBED', 'VIDEO');

UPDATE training_content_blocks
SET resource_url = 'https://www.w3schools.com/html/html_basic.asp'
WHERE (resource_url IS NULL OR resource_url = '' OR resource_url LIKE '%example.com%')
  AND block_type = 'READING';

UPDATE training_content_blocks
SET resource_url = 'https://www.w3schools.com/html/html_basic.asp'
WHERE (resource_url IS NULL OR resource_url = '' OR resource_url LIKE '%example.com%')
  AND block_type NOT IN ('VIDEO_EMBED', 'VIDEO', 'READING');
