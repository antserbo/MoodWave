package com.affectflux.moods;

import org.intellij.lang.annotations.Language;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;

@Profile("postgres")
public interface MoodStatsRepository extends Repository<Mood, Long> {

    @Language("PostgreSQL")
    String DAILY_STATS_SQL = """
        WITH mm AS (SELECT * FROM public.mood)
        SELECT DATE_TRUNC('day', mm.created_at AT TIME ZONE :tz)::date AS local_day,
               COUNT(*)        AS count,
               MIN(mm.intensity) AS min,
               MAX(mm.intensity) AS max,
               AVG(mm.intensity)::float AS avg
        FROM mm
        WHERE mm.created_at >= :from AND mm.created_at < :to
        GROUP BY local_day
        ORDER BY local_day
        """;

    @Query(value = DAILY_STATS_SQL, nativeQuery = true)
    List<DailyMoodRow> findDailyStats(
            @Param("from") Instant from,
            @Param("to") Instant to,
            @Param("tz") String tz
    );
}
