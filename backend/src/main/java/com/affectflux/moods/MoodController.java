package com.affectflux.moods;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
@RequestMapping("/api/moods")
@Tag(name = "Moods", description = "Track and query mood entries")
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
            @Parameter(description = "Start (ISO-8601)", example = "2025-09-01T00:00:00Z")
            @RequestParam(required = false) Instant from,
            @Parameter(description = "End (ISO-8601)", example = "2025-09-14T23:59:59Z")
            @RequestParam(required = false) Instant to,
            @Parameter(description = "Page number (0-based)", example = "0")
            @RequestParam(required = false) Integer page,
            @Parameter(description = "Page size (max 100)", example = "20")
            @RequestParam(required = false) Integer size
    ) {

        return service.list(from, to, page, size);
    }

    @GetMapping("/{id}")
    public MoodResponse getOne(@PathVariable Long id) {
        return service.getById(id);
    }

    @PutMapping("/{id}")
    public MoodResponse update(
            @PathVariable Long id,
            @Valid @RequestBody MoodUpdateRequest req
    ) {
        return service.update(id, req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
