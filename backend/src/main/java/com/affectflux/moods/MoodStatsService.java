package com.affectflux.moods;

import java.time.Instant;
import java.util.List;

public interface MoodStatsService {
    List<DailyMoodStatsResponse> dailyStats(Instant from, Instant to, String tz);
}
