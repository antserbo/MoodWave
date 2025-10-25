package com.affectflux.moods;

import java.time.LocalDate;

public interface DailyMoodRow {
    LocalDate getLocalDay();
    long getCount();
    Integer getMin();
    Integer getMax();
    Double getAvg();
}
