package com.github.alex1304.jdash.component;

import com.github.alex1304.jdash.component.property.GDLevelDemonDifficulty;
import com.github.alex1304.jdash.component.property.GDLevelDifficulty;
import com.github.alex1304.jdash.component.property.GDLevelLength;

/**
 * Represents a timely level (so far Daily level and Weekly demon)
 *
 * @author Alex1304
 */
public class GDTimelyLevel extends GDLevel {

	private long nextTimelyCooldown;
	private long timelyNumber;
	
	public GDTimelyLevel() {
	}
	
	public GDTimelyLevel(GDLevel lvl) {
		this.setId(lvl.getId());
		this.setName(lvl.getName());
		this.setCreatorID(lvl.getCreatorID());
		this.setDescription(lvl.getDescription());
		this.setDifficulty(lvl.getDifficulty());
		this.setDemonDifficulty(lvl.getDemonDifficulty());
		this.setStars(lvl.getStars());
		this.setFeaturedScore(lvl.getFeaturedScore());
		this.setEpic(lvl.isEpic());
		this.setDownloads(lvl.getDownloads());
		this.setLikes(lvl.getLikes());
		this.setLength(lvl.getLength());
		this.setSong(lvl.getSong());
		this.setCoinCount(lvl.getCoinCount());
		this.setHasCoinsVerified(lvl.hasCoinsVerified());
		this.setLevelVersion(lvl.getLevelVersion());
		this.setGameVersion(lvl.getGameVersion());
		this.setObjectCount(lvl.getObjectCount());
		this.setDemon(lvl.isDemon());
		this.setAuto(lvl.isAuto());
		this.setOriginalLevelID(lvl.getOriginalLevelID());
		this.setRequestedStars(lvl.getRequestedStars());
		this.setPass(lvl.getPass());
		this.setUploadTimestamp(lvl.getUploadTimestamp());
		this.setLastUpdatedTimestamp(lvl.getLastUpdatedTimestamp());
	}
	
	public GDTimelyLevel(GDLevel lvl, long nextTimelyCooldown, long timelyNumber) {
		this(lvl);
		this.nextTimelyCooldown = nextTimelyCooldown;
		this.timelyNumber = timelyNumber;
	}
	
	public GDTimelyLevel(long id, String name, String creatorName, long creatorID, String description,
			GDLevelDifficulty difficulty, GDLevelDemonDifficulty demonDifficulty, int stars, int featuredScore,
			boolean isEpic, int downloads, int likes, GDLevelLength length, GDSong song, int coinCount,
			boolean hasCoinsVerified, int levelVersion, int gameVersion, int objectCount, boolean isDemon,
			boolean isAuto, long originalLevelID, int requestedStars, int pass, String uploadTimestamp,
			String lastUpdatedTimestamp, long nextTimelyCooldown, long timelyNumber) {
		super(id, name, creatorName, creatorID, description, difficulty, demonDifficulty, stars, featuredScore, isEpic,
				downloads, likes, length, song, coinCount, hasCoinsVerified, levelVersion, gameVersion, objectCount, isDemon,
				isAuto, originalLevelID, requestedStars, pass, uploadTimestamp, lastUpdatedTimestamp);
		this.nextTimelyCooldown = nextTimelyCooldown;
		this.timelyNumber = timelyNumber;
	}
	
	
	/**
	 * Gets the nextTimelyCooldown
	 *
	 * @return long
	 */
	public long getNextTimelyCooldown() {
		return nextTimelyCooldown;
	}

	/**
	 * Gets the timelyNumber
	 *
	 * @return long
	 */
	public long getTimelyNumber() {
		return timelyNumber;
	}


	/**
	 * Sets the nextTimelyCooldown
	 *
	 * @param nextTimelyCooldown - long
	 */
	public void setNextTimelyCooldown(long nextTimelyCooldown) {
		this.nextTimelyCooldown = nextTimelyCooldown;
	}

	/**
	 * Sets the timelyNumber
	 *
	 * @param timelyNumber - long
	 */
	public void setTimelyNumber(long timelyNumber) {
		this.timelyNumber = timelyNumber;
	}
}
