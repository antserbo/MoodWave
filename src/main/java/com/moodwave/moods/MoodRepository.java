package com.moodwave.moods;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MoodRepository extends JpaRepository<Mood, Long> {
    // later you can add methods like:
    // List<Mood> findByIntensityGreaterThanEqual(int min);
}
