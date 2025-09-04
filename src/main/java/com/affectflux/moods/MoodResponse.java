package com.affectflux.moods;

import java.time.Instant;

public record MoodResponse(
        Long id,
        Instant createdAt,
        Integer score,
        String note
) {
    static MoodResponse fromEntity(Mood m) {
        return new MoodResponse(
                m.getId(),
                m.getCreatedAt(),
                m.getIntensity(),
                m.getDescription()
        );
    }
}
