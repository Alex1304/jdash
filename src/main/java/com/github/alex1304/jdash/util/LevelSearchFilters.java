package com.github.alex1304.jdash.util;

import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Objects;
import java.util.Optional;

import com.github.alex1304.jdash.entity.DemonDifficulty;
import com.github.alex1304.jdash.entity.Difficulty;
import com.github.alex1304.jdash.entity.GDLevel;
import com.github.alex1304.jdash.entity.GDSong;
import com.github.alex1304.jdash.entity.Length;

public class LevelSearchFilters {
	public static enum Toggle {
		STAR,
		NO_STAR,
		UNCOMPLETED,
		ONLY_COMPLETED,
		FEATURED,
		ORIGINAL,
		TWO_PLAYER,
		COINS,
		EPIC;
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
	
	public static LevelSearchFilters create() {
		return new LevelSearchFilters(EnumSet.noneOf(Toggle.class), EnumSet.noneOf(Length.class),
				EnumSet.noneOf(Difficulty.class), Optional.empty(), Optional.empty());
	}
	
	public LevelSearchFilters withToggles(EnumSet<Toggle> toggles) {
		this.toggles = Objects.requireNonNull(toggles);
		return this;
	}
	
	public LevelSearchFilters withLengths(EnumSet<Length> lengths) {
		this.lengths = Objects.requireNonNull(lengths);
		return this;
	}
	
	public LevelSearchFilters withDifficulties(EnumSet<Difficulty> difficulties) {
		this.difficulties = Objects.requireNonNull(difficulties);
		return this;
	}
	
	public LevelSearchFilters withDemonFilter(DemonDifficulty demonDiff) {
		this.demon = Optional.of(Objects.requireNonNull(demonDiff));
		return this;
	}
	
	public LevelSearchFilters removeDemonFilter() {
		this.demon = Optional.empty();
		return this;
	}
	
	public LevelSearchFilters withSongFilter(boolean isCustom, long songId) {
		this.song = Optional.of(isCustom ? GDSong.unknownSong(songId) : Utils.getAudioTrack((int) songId));
		return this;
	}
	
	public LevelSearchFilters removeSongFilter() {
		this.song = Optional.empty();
		return this;
	}
	
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
