package com.affectflux.moods;

import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.ZoneId;
import java.util.List;

@Service
@Profile("postgres")
public class PostgresMoodStatsService implements MoodStatsService {

    private final MoodStatsRepository statsRepo;

    public PostgresMoodStatsService(MoodStatsRepository statsRepo) {
        this.statsRepo = statsRepo;
    }

    @Override
    public List<DailyMoodStatsResponse> dailyStats(Instant from, Instant to, String tz) {
        Instant now = Instant.now();
        Instant effectiveTo = (to == null) ? now : to;
        Instant effectiveFrom = (from == null) ? effectiveTo.minusSeconds(7 * 24 * 3600) : from;

        if (effectiveFrom.isAfter(effectiveTo)) {
            Instant tmp = effectiveFrom;
            effectiveFrom = effectiveTo;
            effectiveTo = tmp;
        }

        String zone = (tz == null || tz.isBlank()) ? "UTC" : tz.trim();

        try {
            ZoneId ignored = ZoneId.of(zone);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid timezone: " + zone, e);
        }


        List<DailyMoodRow> rows = statsRepo.findDailyStats(effectiveFrom, effectiveTo, zone);

        return rows.stream().map(r -> new DailyMoodStatsResponse(
                r.getLocalDay(),
                r.getCount(),
                r.getMin(),
                r.getMax(),
                (r.getAvg() == null)
                        ? null
                        : BigDecimal.valueOf(r.getAvg()).setScale(1, RoundingMode.HALF_UP)
        )).toList();
    }
}
