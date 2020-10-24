package com.github.alex1304.jdash.old.util;

import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Objects;
import java.util.Optional;

import com.github.alex1304.jdash.old.entity.DemonDifficulty;
import com.github.alex1304.jdash.old.entity.Difficulty;
import com.github.alex1304.jdash.old.entity.GDLevel;
import com.github.alex1304.jdash.old.entity.GDSong;
import com.github.alex1304.jdash.old.entity.Length;

/**
 * Allows to define search filters for levels.
 */
public final class LevelSearchFilters {
	public static enum Toggle {
		STAR, NO_STAR, UNCOMPLETED, ONLY_COMPLETED, FEATURED, ORIGINAL, TWO_PLAYER, COINS, EPIC;
	}

	private EnumSet<Toggle> toggles;
	private EnumSet<Length> lengths;
	private EnumSet<Difficulty> difficulties;
	private Optional<DemonDifficulty> demon;
	private Optional<GDSong> song;
	private Collection<? extends GDLevel> completedLevels;

	private LevelSearchFilters(EnumSet<Toggle> toggles, EnumSet<Length> lengths, EnumSet<Difficulty> difficulties,
			Optional<DemonDifficulty> demon, Optional<GDSong> song) {
		this.toggles = toggles;
		this.lengths = lengths;
		this.difficulties = difficulties;
		this.demon = demon;
		this.song = song;
		this.completedLevels = Collections.emptySet();
	}

	/**
	 * Creates a new instance of search filters.
	 * 
	 * @return a new instance of {@link LevelSearchFilters}
	 */
	public static LevelSearchFilters create() {
		return new LevelSearchFilters(EnumSet.noneOf(Toggle.class), EnumSet.noneOf(Length.class),
				EnumSet.noneOf(Difficulty.class), Optional.empty(), Optional.empty());
	}

	/**
	 * Defines which options are toggled on.
	 * 
	 * @param toggles the set of toggles that are on
	 * @return this (for method chaining purposes)
	 */
	public LevelSearchFilters withToggles(EnumSet<Toggle> toggles) {
		this.toggles = Objects.requireNonNull(toggles);
		return this;
	}

	/**
	 * Defines the level lengths to filter on.
	 * 
	 * @param lengths the set of lengths
	 * @return this (for method chaining purposes)
	 */
	public LevelSearchFilters withLengths(EnumSet<Length> lengths) {
		this.lengths = Objects.requireNonNull(lengths);
		return this;
	}

	/**
	 * Defines the level difficulties to filter on.
	 * 
	 * @param difficulties the set of difficulties
	 * @return this (for method chaining purposes)
	 */
	public LevelSearchFilters withDifficulties(EnumSet<Difficulty> difficulties) {
		this.difficulties = Objects.requireNonNull(difficulties);
		return this;
	}

	/**
	 * Defines the demon filter.
	 * 
	 * @param demonDiff the demon difficulty to filter on
	 * @return this (for method chaining purposes)
	 */
	public LevelSearchFilters withDemonFilter(DemonDifficulty demonDiff) {
		this.demon = Optional.of(Objects.requireNonNull(demonDiff));
		return this;
	}

	/**
	 * Removes any demon filter previously defined by a previous call of
	 * {@link #withDemonFilter(DemonDifficulty)}.
	 * 
	 * @return this (for method chaining purposes)
	 */
	public LevelSearchFilters removeDemonFilter() {
		this.demon = Optional.empty();
		return this;
	}

	/**
	 * Defines the song filter.
	 * 
	 * @param isCustom whether to filter on a custom song or a regular one
	 * @param songId   the ID of the song to filter on. If the previous pareameter
	 *                 was set to <code>false</code>, then it refers to the index of
	 *                 the level that has the song in game (Stereo Madness is 1,
	 *                 Back On Track is 2, and so on)
	 * @return this (for method chaining purposes)
	 */
	public LevelSearchFilters withSongFilter(boolean isCustom, long songId) {
		this.song = Optional.of(isCustom ? GDSong.unknownSong(songId) : Utils.getAudioTrack((int) songId));
		return this;
	}

	/**
	 * Removes any song filter previously defined by a previous call of
	 * {@link #removeSongFilter()}.
	 * 
	 * @return this (for method chaining purposes)
	 */
	public LevelSearchFilters removeSongFilter() {
		this.song = Optional.empty();
		return this;
	}

	/**
	 * Defines the list of completed levels. Only relevant if either
	 * {@link Toggle#ONLY_COMPLETED} or {@link Toggle#UNCOMPLETED} is set on.
	 * 
	 * @param completedLevels a Collection of levels that are considered "completed"
	 * @return this (for method chaining purposes)
	 */
	public LevelSearchFilters withCompletedLevels(Collection<? extends GDLevel> completedLevels) {
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
		return demon;
	}

	public Optional<GDSong> getSongFilter() {
		return song;
	}

	/**
	 * Checks whether the given toggle was set by a previous call of
	 * {@link #withToggles(EnumSet)}
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

	@Override
	public int hashCode() {
		return toggles.hashCode() ^ lengths.hashCode() ^ difficulties.hashCode() ^ demon.hashCode() ^ song.hashCode()
				^ completedLevels.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof LevelSearchFilters)) {
			return false;
		}
		LevelSearchFilters o = (LevelSearchFilters) obj;
		return o.toggles.equals(toggles) && o.lengths.equals(lengths) && o.difficulties.equals(difficulties);
	}
}
