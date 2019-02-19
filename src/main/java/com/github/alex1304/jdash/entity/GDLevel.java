package com.github.alex1304.jdash.entity;

/**
 * Represents a level in the game.
 * 
 * @author Alex1304
 *
 */
public class GDLevel extends AbstractGDLevel {

	private final int pass;
	private final String uploadTimestamp;
	private final String lastUpdatedTimestamp;

	public GDLevel(long id, String name, String creatorName, long creatorID, String description,
			Difficulty difficulty, DemonDifficulty demonDifficulty, int stars, int featuredScore,
			boolean isEpic, int downloads, int likes, Length length, GDSong song, int coinCount,
			boolean hasCoinsVerified, int levelVersion, int gameVersion, int objectCount, boolean isDemon,
			boolean isAuto, long originalLevelID, int requestedStars, int pass, String uploadTimestamp, String lastUpdatedTimestamp) {
		super(id, name, creatorID, description, difficulty, demonDifficulty, stars, featuredScore, isEpic,
				downloads, likes, length, song, coinCount, hasCoinsVerified, levelVersion, gameVersion, objectCount, isDemon,
				isAuto, originalLevelID, requestedStars);
		this.pass = pass;
		this.uploadTimestamp = uploadTimestamp;
		this.lastUpdatedTimestamp = lastUpdatedTimestamp;
	}

	/**
	 * Gets the pass
	 *
	 * @return int
	 */
	public int getPass() {
		return pass;
	}

	/**
	 * Gets the uploadTimestamp
	 *
	 * @return String
	 */
	public String getUploadTimestamp() {
		return uploadTimestamp;
	}

	/**
	 * Gets the lastUpdatedTimestamp
	 *
	 * @return String
	 */
	public String getLastUpdatedTimestamp() {
		return lastUpdatedTimestamp;
	}
}
