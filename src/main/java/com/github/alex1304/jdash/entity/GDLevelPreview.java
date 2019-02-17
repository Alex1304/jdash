package com.github.alex1304.jdash.entity;

import java.util.Objects;

/**
 * Basic info on a GD level
 * 
 * @author Alex1304
 *
 */
public class GDLevelPreview implements GDEntity {
	public static enum Length {
		TINY,
		SHORT,
		MEDIUM,
		LONG,
		XL;
	}
	public static enum Difficulty {
		NA,
		AUTO,
		EASY,
		NORMAL,
		HARD,
		HARDER,
		INSANE,
		DEMON;
	}
	public enum DemonDifficulty {
		EASY,
		MEDIUM,
		HARD,
		INSANE,
		EXTREME;
	}
	
	private long id;
	private String name;
	private String creatorName;
	private long creatorID;
	private String description;
	private Difficulty difficulty;
	private DemonDifficulty demonDifficulty;
	private int stars;
	private int featuredScore;
	private boolean isEpic;
	private int downloads;
	private int likes;
	private Length length;
	private GDSong song;
	private int coinCount;
	private boolean hasCoinsVerified;
	private int levelVersion;
	private int gameVersion;
	private int objectCount;
	private boolean isDemon;
	private boolean isAuto;
	private long originalLevelID;
	private int requestedStars;
	
	public GDLevelPreview() {
	}
	
	public GDLevelPreview(long id, String name, String creatorName, long creatorID, String description,
			Difficulty difficulty, DemonDifficulty demonDifficulty, int stars, int featuredScore,
			boolean isEpic, int downloads, int likes, Length length, GDSong song, int coinCount,
			boolean hasCoinsVerified, int levelVersion, int gameVersion, int objectCount, boolean isDemon,
			boolean isAuto, long originalLevelID, int requestedStars) {
		this.id = id;
		this.name = Objects.requireNonNull(name);
		this.creatorName = creatorName;
		this.creatorID = creatorID;
		this.description = Objects.requireNonNull(description);
		this.difficulty =  Objects.requireNonNull(difficulty);
		this.demonDifficulty = Objects.requireNonNull(demonDifficulty);
		this.stars = stars;
		this.featuredScore = featuredScore;
		this.isEpic = isEpic;
		this.downloads = downloads;
		this.likes = likes;
		this.length = length;
		this.song = Objects.requireNonNull(song);
		this.coinCount = coinCount;
		this.hasCoinsVerified = hasCoinsVerified;
		this.levelVersion = levelVersion;
		this.gameVersion = gameVersion;
		this.objectCount = objectCount;
		this.isDemon = isDemon;
		this.isAuto = isAuto;
		this.originalLevelID = originalLevelID;
		this.requestedStars = requestedStars;
	}
	
	@Override
	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public long getCreatorID() {
		return creatorID;
	}

	public String getDescription() {
		return description;
	}

	public Difficulty getDifficulty() {
		return difficulty;
	}

	public DemonDifficulty getDemonDifficulty() {
		return demonDifficulty;
	}

	public int getStars() {
		return stars;
	}

	public int getFeaturedScore() {
		return featuredScore;
	}

	public boolean isEpic() {
		return isEpic;
	}

	public int getDownloads() {
		return downloads;
	}

	public int getLikes() {
		return likes;
	}

	public Length getLength() {
		return length;
	}
	
	public GDSong getSong() {
		return song;
	}

	public int getCoinCount() {
		return coinCount;
	}

	public boolean hasCoinsVerified() {
		return hasCoinsVerified;
	}

	public int getLevelVersion() {
		return levelVersion;
	}

	public int getGameVersion() {
		return gameVersion;
	}

	public int getObjectCount() {
		return objectCount;
	}

	public boolean isDemon() {
		return isDemon;
	}

	public boolean isAuto() {
		return isAuto;
	}

	public long getOriginalLevelID() {
		return originalLevelID;
	}

	public int getRequestedStars() {
		return requestedStars;
	}
	
	public GDLevel toGDLevel(int pass, String uploadTimestamp, String lastUpdatedTimestamp) {
		return new GDLevel(id, name, creatorName, creatorID, description, difficulty, demonDifficulty, stars, featuredScore, isEpic,
				downloads, likes, length, song, coinCount, hasCoinsVerified, levelVersion, gameVersion, objectCount, isDemon,
				isAuto, originalLevelID, requestedStars, pass, uploadTimestamp, lastUpdatedTimestamp);
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof GDUser)) {
			return false;
		}
		GDLevelPreview level = (GDLevelPreview) obj;
		return level.id == id;
	}
	
	@Override
	public int hashCode() {
		return Long.hashCode(id);
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