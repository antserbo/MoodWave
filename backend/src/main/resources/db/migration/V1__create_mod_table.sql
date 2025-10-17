CREATE TABLE IF NOT EXISTS mood (
    id BIGSERIAL PRIMARY KEY,
    description VARCHAR(200) NOT NULL,
    intensity INTEGER NOT NULL,
    created_at TIMESTAMPTZ NOT NULL
    );

CREATE INDEX IF NOT EXISTS idx_mood_created_at ON mood (created_at);
