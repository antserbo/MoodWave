package com.moodwave.moods;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import java.time.Instant;

public record MoodEntryRequest(
        Instant ts,
        @Min(-10) @Max(10) Integer score,
        @Size(max = 2000) String note
) {}
