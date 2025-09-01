package com.moodwave.moods;

import jakarta.validation.constraints.*;

import java.time.Instant;

public record MoodEntryRequest(
        @PastOrPresent Instant ts,
        @Min(-10) @Max(10) Integer score,
        @Size(max = 2000) String note
) {
}
