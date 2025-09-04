package com.affectflux.moods;

import jakarta.validation.constraints.*;

import java.time.Instant;

public record MoodUpdateRequest(
        @PastOrPresent Instant ts,
        @Min(-10) @Max(10) Integer score,
        @Size(max = 2000) String note
) {
}
