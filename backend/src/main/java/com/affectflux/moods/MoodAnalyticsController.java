package com.affectflux.moods;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/api/moods/stats")
public class MoodAnalyticsController {

    private final MoodStatsService service;

    public MoodAnalyticsController(MoodStatsService service) {
        this.service = service;
    }

    @GetMapping("/daily")
    @Operation(
            summary = "Daily mood aggregates",
            description = "Groups moods by the user's local day and returns count, avg, min, max for each day."
    )
    public List<DailyMoodStatsResponse> daily(
            @RequestParam(required = false)
            @Parameter(description = "Start (inclusive), UTC; default = now-7d")
            Instant from,
            @RequestParam(required = false)
            @Parameter(description = "End (exclusive), UTC; default = now")
            Instant to,
            @RequestParam(required = false, defaultValue = "UTC")
            @Parameter(description = "IANA time zone, e.g. Europe/Berlin")
            String tz
    ) {
        return service.dailyStats(from, to, tz);
    }
}
