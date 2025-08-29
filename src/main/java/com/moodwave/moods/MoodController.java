package com.moodwave.moods;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.*;

@RestController
@RequestMapping("/api/moods")
public class MoodController {

    private final List<Mood> store = new ArrayList<>();

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mood create(@Valid @RequestBody MoodEntryRequest req) {
        String id = UUID.randomUUID().toString();
        Instant ts = (req.ts() == null) ? Instant.now() : req.ts();
        Mood mood = new Mood(id, ts, req.score(), req.note());
        store.add(mood);

        return mood;
    }

    @GetMapping
    public List<Mood> list() {
        List<Mood> copy = new ArrayList<>(store);
        copy.sort(Comparator.comparing(Mood::ts).reversed());
        return copy;
    }
}
