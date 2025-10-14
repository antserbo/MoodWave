package com.affectflux.moods;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import java.time.Instant;

public record MoodUpdateRequest(

        @Schema(description = "Updated mood intensity score from -10 (worst) to 10 (best).",
                example = "-2", required = true)
        @Min(-10) @Max(10)
        Integer score,

        @Schema(description = "Optional updated note (up to 2000 chars).",
                example = "Still tired, but better than yesterday")
        @Size(max = 2000)
        String note,

        @Schema(description = "Optional updated timestamp. Must not be in the future.",
                example = "2025-09-13T21:00:00Z")
        @PastOrPresent
        Instant ts
) {}
