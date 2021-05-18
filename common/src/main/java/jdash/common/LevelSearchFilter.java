package jdash.common;

import jdash.common.entity.GDLevel;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Allows to define search filters for levels. This class is immutable.
 */
public final class LevelSearchFilter {

    private final EnumSet<Toggle> toggles;
    private final EnumSet<Length> lengths;
    private final EnumSet<Difficulty> difficulties;
    private final DemonDifficulty demon;
    private final long songId;
    private final Collection<? extends GDLevel> completedLevels;

    private LevelSearchFilter(EnumSet<Toggle> toggles, EnumSet<Length> lengths, EnumSet<Difficulty> difficulties,
                              DemonDifficulty demon, long songId,
                              Collection<? extends GDLevel> completedLevels) {
        this.toggles = toggles;
        this.lengths = lengths;
        this.difficulties = difficulties;
        this.demon = demon;
        this.songId = songId;
        this.completedLevels = completedLevels;
    }

    /**
     * Creates a new instance of search filter with default values.
     *
     * @return a new instance of {@link LevelSearchFilter}
     */
    public static LevelSearchFilter create() {
        return new LevelSearchFilter(EnumSet.noneOf(Toggle.class), EnumSet.noneOf(Length.class),
                EnumSet.noneOf(Difficulty.class), null, -1, Set.of());
    }

    /**
     * Returns a copy of this {@link LevelSearchFilter} with the given set of toggles.
     *
     * @param toggles the set of toggles that are on
     * @return a new LevelSearchFilter with the updated filters
     */
    public LevelSearchFilter withToggles(EnumSet<Toggle> toggles) {
        Objects.requireNonNull(toggles);
        return new LevelSearchFilter(toggles, lengths, difficulties, demon, songId, completedLevels);
    }

    /**
     * Returns a copy of this {@link LevelSearchFilter} with the given set of lengths.
     *
     * @param lengths the set of lengths
     * @return a new LevelSearchFilter with the updated filters
     */
    public LevelSearchFilter withLengths(EnumSet<Length> lengths) {
        Objects.requireNonNull(lengths);
        return new LevelSearchFilter(toggles, lengths, difficulties, demon, songId, completedLevels);
    }

    /**
     * Returns a copy of this {@link LevelSearchFilter} with the given set of difficulties.
     *
     * @param difficulties the set of difficulties
     * @return a new LevelSearchFilter with the updated filters
     */
    public LevelSearchFilter withDifficulties(EnumSet<Difficulty> difficulties) {
        Objects.requireNonNull(difficulties);
        return new LevelSearchFilter(toggles, lengths, difficulties, demon, songId, completedLevels);
    }

    /**
     * Returns a copy of this {@link LevelSearchFilter} with the given demon filter.
     *
     * @param demon the demon difficulty to filter on.
     * @return a new LevelSearchFilter with the updated filters
     */
    public LevelSearchFilter withDemonFilter(DemonDifficulty demon) {
        Objects.requireNonNull(demon);
        return new LevelSearchFilter(toggles, lengths, difficulties, demon, songId, completedLevels);
    }

    /**
     * Returns a copy of this {@link LevelSearchFilter} but without the demon filter.
     *
     * @return a new LevelSearchFilter with the updated filters
     */
    public LevelSearchFilter withoutDemonFilter() {
        return new LevelSearchFilter(toggles, lengths, difficulties, null, songId, completedLevels);
    }

    /**
     * Returns a copy of this {@link LevelSearchFilter} with the given song filter.
     *
     * @param songId   the ID of the song to filter on. If {@link Toggle#CUSTOM_SONG} is absent, then
     *                 it refers to the index of the level that has the song in game (Stereo Madness is 0, Back On Track
     *                 is 1, and so on)
     * @return a new LevelSearchFilter with the updated filters
     */
    public LevelSearchFilter withSongFilter(long songId) {
        return new LevelSearchFilter(toggles, lengths, difficulties, demon, songId, completedLevels);
    }

    /**
     * Returns a copy of this {@link LevelSearchFilter} but without the song filter.
     *
     * @return a new LevelSearchFilter with the updated filters
     */
    public LevelSearchFilter withoutSongFilter() {
        return new LevelSearchFilter(toggles, lengths, difficulties, demon, -1, completedLevels);
    }

    /**
     * Returns a copy of this {@link LevelSearchFilter} with the given list of completed levels. Only relevant if either
     * {@link Toggle#ONLY_COMPLETED} or {@link Toggle#UNCOMPLETED} is set on.
     *
     * @param completedLevels a Collection of levels that are considered "completed"
     * @return a new LevelSearchFilter with the updated filters
     */
    public LevelSearchFilter withCompletedLevels(Collection<? extends GDLevel> completedLevels) {
        Objects.requireNonNull(completedLevels);
        return new LevelSearchFilter(toggles, lengths, difficulties, demon, songId, completedLevels);
    }

    public EnumSet<Toggle> getToggles() {
        return toggles;
    }

    public EnumSet<Length> getLengths() {
        return lengths;
    }

    public EnumSet<Difficulty> getDifficulties() {
        return difficulties;
    }

    public Optional<DemonDifficulty> getDemonFilter() {
        return Optional.ofNullable(demon);
    }

    public long getSongFilter() {
        return songId;
    }

    /**
     * Checks whether the given toggle was set by a previous call of {@link #withToggles(EnumSet)}
     *
     * @param t the toggle to test
     * @return true if included, false otherwise
     */
    public boolean hasToggle(Toggle t) {
        Objects.requireNonNull(t);
        return toggles.contains(t);
    }

    public Collection<? extends GDLevel> getCompletedLevels() {
        return Collections.unmodifiableCollection(completedLevels);
    }

    /**
     * Gives a map representation of this filter, that can be directly sent as request parameters by a web client to
     * Geometry Dash servers. The returned {@link Map} is unmodifiable and won't reflect changes made to this {@link
     * LevelSearchFilter} after this call.
     *
     * @return a map
     */
    public Map<String, String> toMap() {
        var params = new HashMap<String, String>();
        if (!difficulties.isEmpty()) {
            params.put("diff", difficulties.stream()
                    .map(d -> "" + d.getValue())
                    .collect(Collectors.joining(",")));
        }
        if (!lengths.isEmpty()) {
            params.put("len", lengths.stream()
                    .map(d -> "" + d.ordinal())
                    .collect(Collectors.joining(",")));
        }
        params.put("uncompleted", toggles.contains(Toggle.UNCOMPLETED) ? "1" : "0");
        params.put("onlyCompleted", toggles.contains(Toggle.ONLY_COMPLETED) ? "1" : "0");
        params.put("featured", toggles.contains(Toggle.FEATURED) ? "1" : "0");
        params.put("original", toggles.contains(Toggle.ORIGINAL) ? "1" : "0");
        params.put("twoPlayer", toggles.contains(Toggle.TWO_PLAYER) ? "1" : "0");
        params.put("coins", toggles.contains(Toggle.COINS) ? "1" : "0");
        params.put("epic", toggles.contains(Toggle.EPIC) ? "1" : "0");
        params.put("star", toggles.contains(Toggle.STAR) ? "1" : "0");
        if (toggles.contains(Toggle.NO_STAR)) {
            params.put("noStar", "1");
        }
        if (songId != -1) {
            params.put("song", "" + songId);
            params.put("customSong", toggles.contains(Toggle.CUSTOM_SONG) ? "1" : "0");
        }
        if (demon != null) {
            params.put("demonFilter", "" + (demon.ordinal() + 1));
        }
        if ((toggles.contains(Toggle.ONLY_COMPLETED) || toggles.contains(Toggle.UNCOMPLETED))
                && !completedLevels.isEmpty()) {
            params.put("completedLevels", "(" + completedLevels.stream()
                    .map(GDLevel::id)
                    .map(String::valueOf)
                    .collect(Collectors.joining(",")) + ")");
        }
        return Collections.unmodifiableMap(params);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LevelSearchFilter that = (LevelSearchFilter) o;
        return Objects.equals(toggles, that.toggles) && Objects.equals(lengths, that.lengths)
                && Objects.equals(difficulties, that.difficulties) && demon == that.demon
                && Objects.equals(songId, that.songId) && Objects.equals(completedLevels, that.completedLevels);
    }

    @Override
    public int hashCode() {
        return Objects.hash(toggles, lengths, difficulties, demon, songId, completedLevels);
    }

    public enum Toggle {
        STAR, NO_STAR, UNCOMPLETED, ONLY_COMPLETED, FEATURED, ORIGINAL, TWO_PLAYER, COINS, EPIC, CUSTOM_SONG
    }
}