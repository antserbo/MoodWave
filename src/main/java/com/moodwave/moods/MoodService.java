package com.moodwave.moods;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.*;

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

        Mood mood = (req.ts() == null)
                ? new Mood(req.note(), req.score())
                : new Mood(req.note(), req.score(), createdAt);

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

}
