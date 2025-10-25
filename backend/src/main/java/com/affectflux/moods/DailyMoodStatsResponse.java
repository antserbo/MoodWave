package com.affectflux.moods;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.math.BigDecimal;

public record DailyMoodStatsResponse (
    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate day,
    long count,
    Integer min,
    Integer max,
    BigDecimal avg
){}
