package com.github.alex1304.jdash.entity;

import java.util.Objects;

abstract class AbstractGDLevel extends AbstractGDEntity {
	final String name;
	final long creatorID;
	final String creatorName;
	final String description;
	final Difficulty difficulty;
	final DemonDifficulty demonDifficulty;
	final int stars;
	final int featuredScore;
	final boolean isEpic;
	final int downloads;
	final int likes;
	final Length length;
	final GDSong song;
	final int coinCount;
	final boolean hasCoinsVerified;
	final int levelVersion;
	final int gameVersion;
	final int objectCount;
	final boolean isDemon;
	final boolean isAuto;
	final long originalLevelID;
	final int requestedStars;
	
	AbstractGDLevel(long id, String name, String creatorName, long creatorID, String description,
			Difficulty difficulty, DemonDifficulty demonDifficulty, int stars, int featuredScore,
			boolean isEpic, int downloads, int likes, Length length, GDSong song, int coinCount,
			boolean hasCoinsVerified, int levelVersion, int gameVersion, int objectCount, boolean isDemon,
			boolean isAuto, long originalLevelID, int requestedStars) {
		super(id);
		this.name = Objects.requireNonNull(name);
		this.creatorName = Objects.requireNonNull(creatorName);
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
	
	@Override
	public boolean equals(Object obj) {
		return obj instanceof AbstractGDLevel && super.equals(obj);
	}
}
