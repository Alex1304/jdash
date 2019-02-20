package com.github.alex1304.jdash.entity;

import java.util.Objects;

/**
 * Basic info on a GD level
 * 
 * @author Alex1304
 *
 */
public class GDLevelPart2 extends AbstractGDLevel {
	
	private final String creatorName;
	
	GDLevelPart2(long id, String name, String creatorName, long creatorID, String description, Difficulty difficulty,
			DemonDifficulty demonDifficulty, int stars, int featuredScore, boolean isEpic, int downloads, int likes,
			Length length, GDSong song, int coinCount, boolean hasCoinsVerified, int levelVersion, int gameVersion,
			int objectCount, boolean isDemon, boolean isAuto, long originalLevelID, int requestedStars) {
		super(id, name, creatorID, description, difficulty, demonDifficulty, stars, featuredScore, isEpic,
				downloads, likes, length, song, coinCount, hasCoinsVerified, levelVersion, gameVersion, objectCount, isDemon,
				isAuto, originalLevelID, requestedStars);
		this.creatorName = Objects.requireNonNull(creatorName);
	}

	public String getCreatorName() {
		return creatorName;
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj instanceof GDLevelPart2 && super.equals(obj);
	}

	@Override
	public String toString() {
		return "GDLevelPart2 [creatorName=" + creatorName + ", name=" + name + ", creatorID=" + creatorID
				+ ", description=" + description + ", difficulty=" + difficulty + ", demonDifficulty=" + demonDifficulty
				+ ", stars=" + stars + ", featuredScore=" + featuredScore + ", isEpic=" + isEpic + ", downloads="
				+ downloads + ", likes=" + likes + ", length=" + length + ", song=" + song + ", coinCount=" + coinCount
				+ ", hasCoinsVerified=" + hasCoinsVerified + ", levelVersion=" + levelVersion + ", gameVersion="
				+ gameVersion + ", objectCount=" + objectCount + ", isDemon=" + isDemon + ", isAuto=" + isAuto
				+ ", originalLevelID=" + originalLevelID + ", requestedStars=" + requestedStars + ", id=" + id + "]";
	}
}