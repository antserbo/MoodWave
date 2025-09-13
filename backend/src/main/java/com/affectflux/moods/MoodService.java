package com.affectflux.moods;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.List;

@Service
public class MoodService {

    private final MoodRepository repo;
    private final int maxPageSize;

    public MoodService(MoodRepository repo,
                       @Value("${app.paging.max-size:100}") int maxPageSize) {
        this.repo = repo;
        this.maxPageSize = maxPageSize;
    }

    @Transactional
    public MoodResponse create(MoodEntryRequest req) {
        Instant now = Instant.now();
        Instant createdAt = (req.ts() == null) ? now : req.ts();
        if (createdAt.isAfter(now)) {
            throw new IllegalArgumentException("Timestamp cannot be in the future.");
        }

        String normalizedNote = (req.note() == null) ? "" : req.note().trim();

        Mood mood = (req.ts() == null)
                ? new Mood(normalizedNote, req.score())
                : new Mood(normalizedNote, req.score(), createdAt);

        return MoodResponse.fromEntity(repo.save(mood));
    }

    @Transactional(readOnly = true)
    public List<MoodResponse> listNewestFirst() {
        return repo.findAll(Sort.by(Sort.Direction.DESC, "createdAt"))
                .stream()
                .map(MoodResponse::fromEntity)
                .toList();
    }


    private static final int MAX_PAGE_SIZE = 100;

    @Transactional(readOnly = true)
    public Page<MoodResponse> list(
            Instant from,
            Instant to,
            Integer page,
            Integer size
    ) {
        int p = (page == null || page < 0) ? 0 : page;
        int s = (size == null || size <= 0) ? 20 : Math.min(size, MAX_PAGE_SIZE);

        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        Pageable pageable = PageRequest.of(p, s, sort);

        Page<Mood> pageResult;
        if (from == null && to == null) {
            pageResult = repo.findAll(pageable);
        } else {
            Instant start = (from == null) ? Instant.EPOCH : from;
            Instant end = (to == null) ? Instant.now() : to;

            if (start.isAfter(end)) {
                Instant tmp = start;
                start = end;
                end = tmp;
            }

            pageResult = repo.findByCreatedAtBetween(start, end, pageable);
        }

        return pageResult.map(MoodResponse::fromEntity);
    }

    @Transactional(readOnly = true)
    public MoodResponse getById(Long id) {
        Mood m = repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Mood not found"));
        return MoodResponse.fromEntity(m);
    }

    @Transactional
    public MoodResponse update(Long id, MoodUpdateRequest req) {
        Mood m = repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Mood not found"));

        if (req.score() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Mood score is required");
        }

        if (req.ts() != null && req.ts().isAfter(Instant.now())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Mood timestamp cannot be in the future");
        }

        m.setIntensity(req.score());
        if (req.note() != null) {
            m.setDescription(req.note().trim());
        }

        if (req.ts() != null) {
            m.setCreatedAt(req.ts());
        }

        return MoodResponse.fromEntity(repo.save(m));
    }

    @Transactional
    public void delete(Long id) {
        Mood m = repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Mood not found"));
        repo.delete(m);
    }

}
