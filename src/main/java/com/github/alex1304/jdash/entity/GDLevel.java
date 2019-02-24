package com.github.alex1304.jdash.entity;

import java.util.Objects;
import java.util.function.Supplier;

import reactor.core.publisher.Mono;

public class GDLevel extends AbstractGDEntity {

	private final String name;
	private final long creatorID;
	private final String creatorName;
	private final String description;
	private final Difficulty difficulty;
	private final DemonDifficulty demonDifficulty;
	private final int stars;
	private final int featuredScore;
	private final boolean isEpic;
	private final int downloads;
	private final int likes;
	private final Length length;
	private final Supplier<Mono<GDSong>> song;
	private final int coinCount;
	private final boolean hasCoinsVerified;
	private final int levelVersion;
	private final int gameVersion;
	private final int objectCount;
	private final boolean isDemon;
	private final boolean isAuto;
	private final long originalLevelID;
	private final int requestedStars;
	private final Supplier<Mono<GDLevelData>> downloader;

	public GDLevel(long id, String name, long creatorID, String description, Difficulty difficulty,
			DemonDifficulty demonDifficulty, int stars, int featuredScore, boolean isEpic, int downloads, int likes,
			Length length, Supplier<Mono<GDSong>> song, int coinCount, boolean hasCoinsVerified, int levelVersion, int gameVersion,
			int objectCount, boolean isDemon, boolean isAuto, long originalLevelID, int requestedStars,
			String creatorName, Supplier<Mono<GDLevelData>> downloader) {
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
		this.downloader = Objects.requireNonNull(downloader);
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
	
	public Mono<GDSong> getSong() {
		return song.get();
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

	public Mono<GDLevelData> download() {
		return downloader.get();
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj instanceof GDLevel && super.equals(obj);
	}

	@Override
	public String toString() {
		return "GDLevel [name=" + name + ", creatorID=" + creatorID + ", creatorName=" + creatorName + ", description="
				+ description + ", difficulty=" + difficulty + ", demonDifficulty=" + demonDifficulty + ", stars="
				+ stars + ", featuredScore=" + featuredScore + ", isEpic=" + isEpic + ", downloads=" + downloads
				+ ", likes=" + likes + ", length=" + length + ", coinCount=" + coinCount
				+ ", hasCoinsVerified=" + hasCoinsVerified + ", levelVersion=" + levelVersion + ", gameVersion="
				+ gameVersion + ", objectCount=" + objectCount + ", isDemon=" + isDemon + ", isAuto=" + isAuto
				+ ", originalLevelID=" + originalLevelID + ", requestedStars=" + requestedStars + "]";
	}
}
