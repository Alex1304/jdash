package jdash.common;

import jdash.entity.GDLevel;
import jdash.entity.GDSong;
import jdash.internal.InternalUtils;
import jdash.client.request.GDRequest;
import reactor.util.annotation.Nullable;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Allows to define search filters for levels.
 */
public final class LevelSearchFilter {

    private EnumSet<Toggle> toggles;
    private EnumSet<Length> lengths;
    private EnumSet<Difficulty> difficulties;
    private DemonDifficulty demon;
    private GDSong song;
    private Collection<? extends GDLevel> completedLevels;

    private LevelSearchFilter(EnumSet<Toggle> toggles, EnumSet<Length> lengths, EnumSet<Difficulty> difficulties,
                              @Nullable DemonDifficulty demon, @Nullable GDSong song) {
        this.toggles = toggles;
        this.lengths = lengths;
        this.difficulties = difficulties;
        this.demon = demon;
        this.song = song;
        this.completedLevels = Set.of();
    }

    /**
     * Creates a new instance of search filters.
     *
     * @return a new instance of {@link LevelSearchFilter}
     */
    public static LevelSearchFilter create() {
        return new LevelSearchFilter(EnumSet.noneOf(Toggle.class), EnumSet.noneOf(Length.class),
                EnumSet.noneOf(Difficulty.class), null, null);
    }

    /**
     * Defines which options are toggled on.
     *
     * @param toggles the set of toggles that are on
     * @return this (for method chaining purposes)
     */
    public LevelSearchFilter withToggles(EnumSet<Toggle> toggles) {
        this.toggles = Objects.requireNonNull(toggles);
        return this;
    }

    /**
     * Defines the level lengths to filter on.
     *
     * @param lengths the set of lengths
     * @return this (for method chaining purposes)
     */
    public LevelSearchFilter withLengths(EnumSet<Length> lengths) {
        this.lengths = Objects.requireNonNull(lengths);
        return this;
    }

    /**
     * Defines the level difficulties to filter on.
     *
     * @param difficulties the set of difficulties
     * @return this (for method chaining purposes)
     */
    public LevelSearchFilter withDifficulties(EnumSet<Difficulty> difficulties) {
        this.difficulties = Objects.requireNonNull(difficulties);
        return this;
    }

    /**
     * Defines the demon filter.
     *
     * @param demonDiff the demon difficulty to filter on
     * @return this (for method chaining purposes)
     */
    public LevelSearchFilter withDemonFilter(DemonDifficulty demonDiff) {
        this.demon = Objects.requireNonNull(demonDiff);
        return this;
    }

    /**
     * Removes any demon filter previously defined by a previous call of {@link #withDemonFilter(DemonDifficulty)}.
     *
     * @return this (for method chaining purposes)
     */
    public LevelSearchFilter removeDemonFilter() {
        this.demon = null;
        return this;
    }

    /**
     * Defines the song filter.
     *
     * @param isCustom whether to filter on a custom song or a regular one
     * @param songId   the ID of the song to filter on. If the previous pareameter was set to <code>false</code>, then
     *                 it refers to the index of the level that has the song in game (Stereo Madness is 1, Back On Track
     *                 is 2, and so on)
     * @return this (for method chaining purposes)
     */
    public LevelSearchFilter withSongFilter(boolean isCustom, long songId) {
        this.song = isCustom ? GDSong.unknownSong(songId) : InternalUtils.getAudioTrack((int) songId);
        return this;
    }

    /**
     * Removes any song filter defined by a previous call of {@link #withSongFilter(boolean, long)}.
     *
     * @return this (for method chaining purposes)
     */
    public LevelSearchFilter removeSongFilter() {
        this.song = null;
        return this;
    }

    /**
     * Defines the list of completed levels. Only relevant if either {@link Toggle#ONLY_COMPLETED} or {@link
     * Toggle#UNCOMPLETED} is set on.
     *
     * @param completedLevels a Collection of levels that are considered "completed"
     * @return this (for method chaining purposes)
     */
    public LevelSearchFilter withCompletedLevels(Collection<? extends GDLevel> completedLevels) {
        this.completedLevels = Objects.requireNonNull(completedLevels);
        return this;
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

    public Optional<DemonDifficulty> getDemon() {
        return Optional.ofNullable(demon);
    }

    public Optional<GDSong> getSongFilter() {
        return Optional.ofNullable(song);
    }

    /**
     * Checks whether the given toggle was set by a previous call of {@link #withToggles(EnumSet)}
     *
     * @param t the toggle to test
     * @return true if included, false otherwise
     */
    public boolean hasToggle(Toggle t) {
        return toggles.contains(t);
    }

    public Collection<? extends GDLevel> getCompletedLevels() {
        return Collections.unmodifiableCollection(completedLevels);
    }

    /**
     * Gives a map representation of this filter, that can directly be passed to {@link GDRequest#addParameters(Map)}
     *
     * @return a map
     */
    public Map<String, String> toParams() {
        var params = new HashMap<String, String>();
        if (!difficulties.isEmpty()) {
            params.put("diff", difficulties.stream()
                    .map(d -> "" + d.getVal())
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
        if (song != null) {
            params.put("song", "" + song.id());
            params.put("customSong", song.isCustom() ? "1" : "0");
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
        return params;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LevelSearchFilter that = (LevelSearchFilter) o;
        return Objects.equals(toggles, that.toggles) && Objects.equals(lengths, that.lengths)
                && Objects.equals(difficulties, that.difficulties) && demon == that.demon
                && Objects.equals(song, that.song) && Objects.equals(completedLevels, that.completedLevels);
    }

    @Override
    public int hashCode() {
        return Objects.hash(toggles, lengths, difficulties, demon, song, completedLevels);
    }

    public enum Toggle {
        STAR, NO_STAR, UNCOMPLETED, ONLY_COMPLETED, FEATURED, ORIGINAL, TWO_PLAYER, COINS, EPIC
    }
}