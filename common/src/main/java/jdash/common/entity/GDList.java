package jdash.common.entity;

import java.time.Instant;
import java.util.List;

public record GDList(
        long id,
        String name,
        String description,
        int version,
        int downloads,
        int likes,
        boolean isFeatured,
        Instant uploadTimestamp,
        Instant lastUpdateTimestamp,
        int iconId,
        long creatorAccountId,
        String creatorName,
        List<Long> levelIds,
        int diamonds,
        int minCompletion
) {}
