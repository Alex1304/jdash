package com.github.alex1304.jdash.entity;

public class GDLevelPart2 extends AbstractGDLevel {

	public GDLevelPart2(long id, String name, String creatorName, long creatorID, String description, Difficulty difficulty,
			DemonDifficulty demonDifficulty, int stars, int featuredScore, boolean isEpic, int downloads, int likes,
			Length length, GDSong song, int coinCount, boolean hasCoinsVerified, int levelVersion, int gameVersion,
			int objectCount, boolean isDemon, boolean isAuto, long originalLevelID, int requestedStars) {
		super(id, name, creatorName, creatorID, description, difficulty, demonDifficulty, stars, featuredScore, isEpic,
				downloads, likes, length, song, coinCount, hasCoinsVerified, levelVersion, gameVersion, objectCount, isDemon,
				isAuto, originalLevelID, requestedStars);
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj instanceof GDLevelPart2 && super.equals(obj);
	}

	@Override
	public String toString() {
		return "GDLevelPart2 [name=" + name + ", creatorID=" + creatorID + ", creatorName=" + creatorName
				+ ", description=" + description + ", difficulty=" + difficulty + ", demonDifficulty=" + demonDifficulty
				+ ", stars=" + stars + ", featuredScore=" + featuredScore + ", isEpic=" + isEpic + ", downloads="
				+ downloads + ", likes=" + likes + ", length=" + length + ", song=" + song + ", coinCount=" + coinCount
				+ ", hasCoinsVerified=" + hasCoinsVerified + ", levelVersion=" + levelVersion + ", gameVersion="
				+ gameVersion + ", objectCount=" + objectCount + ", isDemon=" + isDemon + ", isAuto=" + isAuto
				+ ", originalLevelID=" + originalLevelID + ", requestedStars=" + requestedStars + ", id=" + id + "]";
	}
}