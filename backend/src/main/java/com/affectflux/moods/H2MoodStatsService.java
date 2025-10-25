package com.affectflux.moods;

import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.List;

@Service
@Profile("!postgres")
public class H2MoodStatsService implements MoodStatsService {
    @Override
    public List<DailyMoodStatsResponse> dailyStats(Instant from, Instant to, String tz) {
        // TODO MW-24: This endpoint is Postgres-only (uses AT TIME ZONE & DATE_TRUNC)
        throw new ResponseStatusException(
                HttpStatus.NOT_IMPLEMENTED,
                "Daily stats are available when running with the 'postgres' profile."
        );
    }
}
