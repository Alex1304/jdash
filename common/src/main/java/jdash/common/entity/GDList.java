package jdash.common.entity;

import jdash.common.DemonDifficulty;
import jdash.common.Difficulty;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * Represents a list of levels in Geometry Dash.
 *
 * @param id                  The ID of the list.
 * @param name                The name of the list.
 * @param description         The description of the list.
 * @param version             The version of the list.
 * @param downloads           The number of downloads of the list.
 * @param likes               The number of likes of the list.
 * @param isFeatured          Whether the list is featured.
 * @param uploadTimestamp     The timestamp at which the list was uploaded
 * @param lastUpdateTimestamp The timestamp at which the list was last updated. May be {@link Instant#EPOCH} if the
 *                            level is on version 1.
 * @param iconId              The icon ID of the list.
 * @param creatorAccountId    The account ID of the user that uploaded the list.
 * @param creatorName         The name of the user that uploaded the list.
 * @param levelIds            The list of level IDs the list contains.
 * @param diamonds            The diamonds awarded by completing the list.
 * @param minCompletion       The minimum number of levels to beat in order to claim the rewards of the list.
 */
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
) {

    /**
     * Attempts to translate the {@link #iconId()} of this list into a {@link Difficulty}. If it represents a
     * {@link DemonDifficulty} or an unknown value, it will return an empty optional.
     *
     * @return a {@link Difficulty}, if applicable
     */
    public Optional<Difficulty> iconAsDifficulty() {
        return Optional.ofNullable(switch (iconId) {
            case -1 -> Difficulty.NA;
            case 0 -> Difficulty.AUTO;
            case 1 -> Difficulty.EASY;
            case 2 -> Difficulty.NORMAL;
            case 3 -> Difficulty.HARD;
            case 4 -> Difficulty.HARDER;
            case 5 -> Difficulty.INSANE;
            default -> null;
        });
    }

    /**
     * Attempts to translate the {@link #iconId()} of this list into a {@link DemonDifficulty}. If it represents a
     * {@link Difficulty} or an unknown value, it will return an empty optional.
     *
     * @return a {@link DemonDifficulty}, if applicable
     */
    public Optional<DemonDifficulty> iconAsDemonDifficulty() {
        return Optional.ofNullable(switch (iconId) {
            case 6 -> DemonDifficulty.EASY;
            case 7 -> DemonDifficulty.MEDIUM;
            case 8 -> DemonDifficulty.HARD;
            case 9 -> DemonDifficulty.INSANE;
            case 10 -> DemonDifficulty.EXTREME;
            default -> null;
        });
    }
}
