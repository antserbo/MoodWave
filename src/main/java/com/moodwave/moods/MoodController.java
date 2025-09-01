package com.moodwave.moods;

import jakarta.validation.Valid;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.*;

@RestController
@RequestMapping("/api/moods")
public class MoodController {

    private final MoodRepository repo;

    public MoodController(MoodRepository repo) {
        this.repo = repo;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mood create(@Valid @RequestBody MoodEntryRequest req) {
        Instant now = Instant.now();
        Instant createdAt = (req.ts() == null) ? now : req.ts();
        if (createdAt.isAfter(now)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Timestamp cannot be in the future");
        }

        Mood mood = (req.ts() == null)
                ? new Mood(req.note(), req.score())
                : new Mood(req.note(), req.score(), createdAt);

        return repo.save(mood);
    }


    @GetMapping
    public List<Mood> list() {
        return repo.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
    }
}
