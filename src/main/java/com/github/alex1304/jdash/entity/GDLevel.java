package com.github.alex1304.jdash.entity;

import java.util.Objects;
import java.util.function.Supplier;

import com.github.alex1304.jdash.exception.MissingAccessException;
import com.github.alex1304.jdash.exception.SongNotAllowedForUseException;

import reactor.core.publisher.Mono;

public final class GDLevel extends AbstractGDEntity {

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
	private final Mono<GDLevelData> data;
	private final Supplier<Mono<GDLevel>> refresher;

	public GDLevel(long id, String name, long creatorID, String description, Difficulty difficulty,
			DemonDifficulty demonDifficulty, int stars, int featuredScore, boolean isEpic, int downloads, int likes,
			Length length, Supplier<Mono<GDSong>> song, int coinCount, boolean hasCoinsVerified, int levelVersion, int gameVersion,
			int objectCount, boolean isDemon, boolean isAuto, long originalLevelID, int requestedStars,
			String creatorName, Supplier<Mono<GDLevelData>> downloader, Supplier<Mono<GDLevel>> refresher) {
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
		this.data = Objects.requireNonNull(downloader).get().cache();
		this.refresher = Objects.requireNonNull(refresher);
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
	
	/**
	 * Gets the song that this level uses.
	 * 
	 * @return a Mono emitting the song. If the song is not allowed for use in
	 *         Geometry Dash, a {@link SongNotAllowedForUseException} is emitted.
	 */
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
	
	/**
	 * Gets the level download data, which includes binary data of the level, the
	 * upload an d last updated dates, and the copy passcode.
	 * 
	 * @return a Mono emitting the download data for this level.
	 */
	public Mono<GDLevelData> download() {
		return data;
	}
	
	/**
	 * Performs a request to know whether the level has been deleted.
	 * 
	 * @return true if the level is still on the GD servers, false if deleted.
	 */
	public Mono<Boolean> isDeleted() {
		return refresher.get()
				.map(__ -> false)
				.onErrorReturn(MissingAccessException.class, true);
	}
	
	/**
	 * Returns a new GDLevel that is a refreshed version of this one.
	 * 
	 * @return a new GDLevel
	 */
	public Mono<GDLevel> refresh() {
		return refresher.get();
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
