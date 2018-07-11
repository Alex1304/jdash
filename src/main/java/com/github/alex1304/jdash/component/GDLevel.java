package com.github.alex1304.jdash.component;

import com.github.alex1304.jdash.component.property.GDLevelDemonDifficulty;
import com.github.alex1304.jdash.component.property.GDLevelDifficulty;
import com.github.alex1304.jdash.component.property.GDLevelLength;

/**
 * Represents a level in the game.
 * 
 * @author Alex1304
 *
 */
public class GDLevel extends GDLevelPreview {

	private int pass;
	private String uploadTimestamp;
	private String lastUpdatedTimestamp;
	
	public GDLevel() {
	}
	
	public GDLevel(GDLevelPreview lp) {
		this.setId(lp.getId());
		this.setName(lp.getName());
		this.setCreatorID(lp.getCreatorID());
		this.setDescription(lp.getDescription());
		this.setDifficulty(lp.getDifficulty());
		this.setDemonDifficulty(lp.getDemonDifficulty());
		this.setStars(lp.getStars());
		this.setFeaturedScore(lp.getFeaturedScore());
		this.setEpic(lp.isEpic());
		this.setDownloads(lp.getDownloads());
		this.setLikes(lp.getLikes());
		this.setLength(lp.getLength());
		this.setSong(lp.getSong());
		this.setCoinCount(lp.getCoinCount());
		this.setCoinsVerified(lp.hasCoinsVerified());
		this.setLevelVersion(lp.getLevelVersion());
		this.setGameVersion(lp.getGameVersion());
		this.setObjectCount(lp.getObjectCount());
		this.setDemon(lp.isDemon());
		this.setAuto(lp.isAuto());
		this.setOriginalLevelID(lp.getOriginalLevelID());
		this.setRequestedStars(lp.getRequestedStars());
	}
	
	public GDLevel(GDLevelPreview lp, int pass, String uploadTimestamp, String lastUpdatedTimestamp) {
		this(lp);
		this.pass = pass;
		this.uploadTimestamp = uploadTimestamp;
		this.lastUpdatedTimestamp = lastUpdatedTimestamp;
	}

	public GDLevel(long id, String name, String creatorName, long creatorID, String description,
			GDLevelDifficulty difficulty, GDLevelDemonDifficulty demonDifficulty, int stars, int featuredScore,
			boolean isEpic, int downloads, int likes, GDLevelLength length, GDSong song, int coinCount,
			boolean hasCoinsVerified, int levelVersion, int gameVersion, int objectCount, boolean isDemon,
			boolean isAuto, long originalLevelID, int requestedStars, int pass, String uploadTimestamp, String lastUpdatedTimestamp) {
		super(id, name, creatorName, creatorID, description, difficulty, demonDifficulty, stars, featuredScore, isEpic,
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

	/**
	 * Sets the pass
	 *
	 * @param pass - int
	 */
	public void setPass(int pass) {
		this.pass = pass;
	}

	/**
	 * Sets the uploadTimestamp
	 *
	 * @param uploadTimestamp - String
	 */
	public void setUploadTimestamp(String uploadTimestamp) {
		this.uploadTimestamp = uploadTimestamp;
	}

	/**
	 * Sets the lastUpdatedTimestamp
	 *
	 * @param lastUpdatedTimestamp - String
	 */
	public void setLastUpdatedTimestamp(String lastUpdatedTimestamp) {
		this.lastUpdatedTimestamp = lastUpdatedTimestamp;
	}

	@Override
	public String toString() {
		return "GDLevel [pass=" + pass + ", uploadTimestamp=" + uploadTimestamp + ", lastUpdatedTimestamp="
				+ lastUpdatedTimestamp + ", getId()=" + getId() + ", getName()=" + getName() + ", getCreatorName()="
				+ getCreatorName() + ", getCreatorID()=" + getCreatorID() + ", getDescription()=" + getDescription()
				+ ", getDifficulty()=" + getDifficulty() + ", getDemonDifficulty()=" + getDemonDifficulty()
				+ ", getStars()=" + getStars() + ", getFeaturedScore()=" + getFeaturedScore() + ", isEpic()=" + isEpic()
				+ ", getDownloads()=" + getDownloads() + ", getLikes()=" + getLikes() + ", getLength()=" + getLength()
				+ ", getSong()=" + getSong() + ", getCoinCount()=" + getCoinCount() + ", hasCoinsVerified()="
				+ hasCoinsVerified() + ", getLevelVersion()=" + getLevelVersion() + ", getGameVersion()="
				+ getGameVersion() + ", getObjectCount()=" + getObjectCount() + ", isDemon()=" + isDemon()
				+ ", isAuto()=" + isAuto() + ", getOriginalLevelID()=" + getOriginalLevelID() + ", getRequestedStars()="
				+ getRequestedStars() + "]";
	}
}
