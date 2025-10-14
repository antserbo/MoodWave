package com.affectflux.moods;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import java.time.Instant;

public record MoodEntryRequest(

        @Schema(description = "When the mood happened (UTC). Defaults to now if omitted.",
                example = "2025-09-14T10:00:00Z")
        @PastOrPresent
        Instant ts,

        @Schema(description = "Mood intensity score from -10 (worst) to 10 (best).",
                example = "3")
        @Min(-10) @Max(10)
        Integer score,

        @Schema(description = "Optional note or description (up to 2000 chars).",
                example = "Felt much better after a walk outside")
        @Size(max = 2000)
        String note
) {}
