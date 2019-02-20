package com.github.alex1304.jdash.entity;

import java.util.EnumSet;
import java.util.Objects;
import java.util.Optional;

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
	
	private final EnumSet<Toggle> toggles;
	private final EnumSet<Length> lengths;
	private final EnumSet<Difficulty> difficulties;
	private final Optional<DemonDifficulty> demon;
	private final Optional<GDSong> song;
	
	public LevelSearchFilters(EnumSet<Toggle> toggles, EnumSet<Length> lengths, EnumSet<Difficulty> difficulties,
			Optional<DemonDifficulty> demon, Optional<GDSong> song) {
		this.toggles = Objects.requireNonNull(toggles);
		this.lengths = Objects.requireNonNull(lengths);
		this.difficulties = Objects.requireNonNull(difficulties);
		this.demon = Objects.requireNonNull(demon);
		this.song = Objects.requireNonNull(song);
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

	public Optional<GDSong> getSong() {
		return song;
	}
}
