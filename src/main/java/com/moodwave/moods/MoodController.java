package com.moodwave.moods;

import jakarta.validation.Valid;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/api/moods")
public class MoodController {

    private final MoodService service;

    public MoodController(MoodService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MoodResponse create(@Valid @RequestBody MoodEntryRequest req) {
        return service.create(req);
    }

    @GetMapping
    public Page<MoodResponse> list(
            @RequestParam(required = false) Instant from,
            @RequestParam(required = false) Instant to,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size
    ) {

        return service.list(from, to, page, size);
    }
}
