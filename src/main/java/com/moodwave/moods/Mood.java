package com.moodwave.moods;

import java.time.Instant;

public record Mood(
        String id,
        Instant ts,
        Integer score,
        String note
) {}