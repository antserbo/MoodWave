package com.affectflux.moods;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;

@Schema(name = "MoodResponse", description = "API view of a mood entry")
public record MoodResponse(

        @Schema(description = "Unique ID of the mood entry", example = "42")
        Long id,

        @Schema(description = "When the mood was recorded (UTC)", example = "2025-09-14T10:00:00Z")
        Instant createdAt,

        @Schema(description = "Mood intensity score from -10 (worst) to 10 (best)", example = "5")
        Integer score,

        @Schema(description = "Optional note attached to the mood entry",
                example = "Had a great workout today")
        String note
) {
    public static MoodResponse fromEntity(Mood m) {
        return new MoodResponse(
                m.getId(),
                m.getCreatedAt(),
                m.getIntensity(),
                m.getDescription()
        );
    }
}
