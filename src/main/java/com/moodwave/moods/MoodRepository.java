package com.moodwave.moods;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;

public interface MoodRepository extends JpaRepository<Mood, Long> {

    Page<Mood> findByCreatedAtBetween(Instant startInclusive, Instant endInclusive, Pageable pageable);
}
