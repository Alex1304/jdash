package com.github.alex1304.jdash.entity;

/**
 * Basic info on a GD level
 * 
 * @author Alex1304
 *
 */
public class GDLevelSearchResult extends AbstractGDLevel {
	
//	private final String creatorName
	
	GDLevelSearchResult(long id, String name, String creatorName, long creatorID, String description, Difficulty difficulty,
			DemonDifficulty demonDifficulty, int stars, int featuredScore, boolean isEpic, int downloads, int likes,
			Length length, GDSong song, int coinCount, boolean hasCoinsVerified, int levelVersion, int gameVersion,
			int objectCount, boolean isDemon, boolean isAuto, long originalLevelID, int requestedStars) {
		super(id, name, creatorID, description, difficulty, demonDifficulty, stars, featuredScore, isEpic,
				downloads, likes, length, song, coinCount, hasCoinsVerified, levelVersion, gameVersion, objectCount, isDemon,
				isAuto, originalLevelID, requestedStars);
	}
	
	public GDLevel toGDLevel(int pass, String uploadTimestamp, String lastUpdatedTimestamp) {
		return new GDLevel(id, name, creatorName, creatorID, description, difficulty, demonDifficulty, stars, featuredScore, isEpic,
				downloads, likes, length, song, coinCount, hasCoinsVerified, levelVersion, gameVersion, objectCount, isDemon,
				isAuto, originalLevelID, requestedStars, pass, uploadTimestamp, lastUpdatedTimestamp);
	}

	@Override
	public String toString() {
		return "GDLevelPreview [id=" + id + ", name=" + name + ", creatorName=" + creatorName + ", creatorID="
				+ creatorID + ", description=" + description + ", difficulty=" + difficulty + ", demonDifficulty="
				+ demonDifficulty + ", stars=" + stars + ", featuredScore=" + featuredScore + ", isEpic=" + isEpic
				+ ", downloads=" + downloads + ", likes=" + likes + ", length=" + length + ", song=" + song
				+ ", coinCount=" + coinCount + ", hasCoinsVerified=" + hasCoinsVerified + ", levelVersion="
				+ levelVersion + ", gameVersion=" + gameVersion + ", objectCount=" + objectCount + ", isDemon="
				+ isDemon + ", isAuto=" + isAuto + ", originalLevelID=" + originalLevelID + ", requestedStars="
				+ requestedStars + "]";
	}
}