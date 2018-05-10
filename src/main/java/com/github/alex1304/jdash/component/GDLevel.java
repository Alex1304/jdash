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
public class GDLevel implements GDComponent {

	private long id;
	private String name;
	private long creatorID;
	private String description;
	private GDLevelDifficulty difficulty;
	private GDLevelDemonDifficulty demonDifficulty;
	private int stars;
	private int featuredScore;
	private boolean isEpic;
	private int downloads;
	private int likes;
	private GDLevelLength length;
	private int pass;
	private long songID;
	private int coinCount;
	private boolean hasCoinsVerified;
	private int levelVersion;
	private int gameVersion;
	private int objectCount;
	private boolean isDemon;
	private boolean isAuto;
	private long originalLevelID;
	private int audioTrack;
	private int requestedStars;
	private String uploadTimestamp;
	private String lastUpdatedTimestamp;

	/**
	 * @param id
	 *            - the level ID
	 * @param name
	 *            - the level name
	 * @param creatorID
	 *            - the account ID of the creator of this level
	 * @param description
	 *            - the description of the level
	 * @param difficulty
	 *            - the level difficulty
	 * @param demonDifficulty
	 *            - the level demon difficulty
	 * @param stars
	 *            - the number of stars assigned to this level
	 * @param featuredScore
	 *            - the featured score of the level. This score is mainly used
	 *            to sort levels in the Featured section.
	 * @param epic
	 *            - whether the level is marked as Epic
	 * @param downloads
	 *            - the number of downloads of the level
	 * @param likes
	 *            - the number of likes the level received
	 * @param length
	 *            - the level length
	 * @param pass
	 *            - the passcode to copy the level into the editor. -1 if the
	 *            level is not copyable, -2 if the level is free to copy.
	 * @param songID
	 *            - the ID of the song of the level
	 * @param coinCount
	 *            - the number of coins the level has
	 * @param coinsVerified
	 *            - whether the coins are verified
	 * @param levelVersion
	 *            - the level version
	 * @param gameVersion
	 *            - whether the the game version which this level has been made on
	 * @param objectCount
	 *            - the number of objects the level has
	 * @param isDemon
	 *            - whether this level is Demon
	 * @param isAuto
	 *            - whether this level is auto
	 * @param originalLevelID
	 *            - the ID of the original level
	 * @param audioTrack
	 *            - Gets the index of the audio track (1 for Stereo Madness, 2 for Back On Track, etc)
	 * @param requestedStars
	 *            - the amount of stars requested by the uploader
	 * @param uploadTimestamp
	 *            - the timestamp of when the level has been uploaded
	 * @param lastUpdatedTimestamp
	 *            - the timestamp of when the level has been updated for the last time
	 */
	public GDLevel(long id, String name, long creatorID, String description, GDLevelDifficulty difficulty,
			GDLevelDemonDifficulty demonDifficulty, int stars, int featuredScore, boolean epic, int downloads,
			int likes, GDLevelLength length, int pass, long songID, int coinCount, boolean coinsVerified,
			int levelVersion, int gameVersion, int objectCount, boolean isDemon, boolean isAuto,
			long originalLevelID, int audioTrack, int requestedStars, String uploadTimestamp,
			String lastUpdatedTimestamp) {
		this.id = id;
		this.name = name;
		this.creatorID = creatorID;
		this.description = description;
		this.difficulty = difficulty;
		this.demonDifficulty = demonDifficulty;
		this.stars = stars;
		this.featuredScore = featuredScore;
		this.isEpic = epic;
		this.downloads = downloads;
		this.likes = likes;
		this.length = length;
		this.pass = pass;
		this.songID = songID;
		this.coinCount = coinCount;
		this.hasCoinsVerified = coinsVerified;
		this.levelVersion = levelVersion;
		this.gameVersion = gameVersion;
		this.objectCount = objectCount;
		this.isDemon = isDemon;
		this.isAuto = isAuto;
		this.originalLevelID = originalLevelID;
		this.audioTrack = audioTrack;
		this.requestedStars = requestedStars;
		this.uploadTimestamp = uploadTimestamp;
		this.lastUpdatedTimestamp = lastUpdatedTimestamp;
	}

	/**
	 * Gets the level ID
	 * 
	 * @return long
	 */
	public long getId() {
		return id;
	}

	/**
	 * Gets the level name
	 * 
	 * @return String
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the account ID of the creator of this level
	 * 
	 * @return long
	 */
	public long getCreatorID() {
		return creatorID;
	}

	/**
	 * Gets the description of the level
	 * 
	 * @return String
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Gets the level difficulty
	 * 
	 * @return GDLevelDifficulty
	 */
	public GDLevelDifficulty getDifficulty() {
		return difficulty;
	}

	/**
	 * Gets the level demon difficulty
	 * 
	 * @return GDLevelDemonDifficulty
	 */
	public GDLevelDemonDifficulty getDemonDifficulty() {
		return demonDifficulty;
	}

	/**
	 * Gets the number of stars assigned to this level
	 * 
	 * @return int
	 */
	public int getStars() {
		return stars;
	}

	/**
	 * Gets the featured score of the level. This score is mainly used to sort
	 * levels in the Featured section.
	 * 
	 * @return int
	 */
	public int getFeaturedScore() {
		return featuredScore;
	}

	/**
	 * Gets whether the level is marked as Epic
	 * 
	 * @return boolean
	 */
	public boolean isEpic() {
		return isEpic;
	}

	/**
	 * Gets the number of downloads of the level
	 *
	 * @return int
	 */
	public int getDownloads() {
		return downloads;
	}

	/**
	 * Gets the number of likes the level received
	 * 
	 * @return int
	 */
	public int getLikes() {
		return likes;
	}

	/**
	 * Gets the level length
	 * 
	 * @return GDLevelLength
	 */
	public GDLevelLength getLength() {
		return length;
	}

	/**
	 * Gets the passcode to copy the level into the editor. -1 if the level is
	 * not copyable, -2 if the level is free to copy.
	 * 
	 * @return int
	 */
	public int getPass() {
		return pass;
	}

	/**
	 * Gets the ID of the song of the level
	 * 
	 * @return long
	 */
	public long getSongID() {
		return songID;
	}

	/**
	 * Gets the number of coins the level has
	 * 
	 * @return int
	 */
	public int getCoinCount() {
		return coinCount;
	}

	/**
	 * Gets whether the coins are verified
	 * 
	 * @return boolean
	 */
	public boolean hasCoinsVerified() {
		return hasCoinsVerified;
	}

	/**
	 * Gets the level version
	 * 
	 * @return int
	 */
	public int getLevelVersion() {
		return levelVersion;
	}

	/**
	 * Gets the game version which this level has been made on
	 * 
	 * @return int
	 */
	public int getGameVersion() {
		return gameVersion;
	}

	/**
	 * Gets the number of objects the level has
	 * 
	 * @return int
	 */
	public int getObjectCount() {
		return objectCount;
	}

	/**
	 * Gets whether this level is Demon
	 * 
	 * @return boolean
	 */
	public boolean isDemon() {
		return isDemon;
	}

	/**
	 * Gets whether this level is auto
	 * 
	 * @return boolean
	 */
	public boolean isAuto() {
		return isAuto;
	}

	/**
	 * Gets the ID of the original level, or 0 if it's already an original level
	 * 
	 * @return int
	 */
	public long getOriginalLevelID() {
		return originalLevelID;
	}

	/**
	 * Gets the index of the audio track (1 for Stereo Madness, 2 for Back On Track, etc)
	 * Returns 0 if it uses a custom song.
	 * 
	 * @return the audioTrack
	 */
	public int getAudioTrack() {
		return audioTrack;
	}

	/**
	 * Gets the amount of stars requested by the uploader
	 * 
	 * @return the requestedStars
	 */
	public int getRequestedStars() {
		return requestedStars;
	}

	/**
	 * Gets the timestamp of when the level has been uploaded
	 * 
	 * @return String
	 */
	public String getUploadTimestamp() {
		return uploadTimestamp;
	}

	/**
	 * Gets the timestamp of when the level has been updated for the last time
	 * 
	 * @return the lastUpdatedTimestamp
	 */
	public String getLastUpdatedTimestamp() {
		return lastUpdatedTimestamp;
	}

	@Override
	public String toString() {
		return "GDLevel [id=" + id + ", name=" + name + ", creatorID=" + creatorID + ", description=" + description
				+ ", difficulty=" + difficulty + ", demonDifficulty=" + demonDifficulty + ", stars=" + stars
				+ ", featuredScore=" + featuredScore + ", isEpic=" + isEpic + ", downloads=" + downloads + ", likes="
				+ likes + ", length=" + length + ", pass=" + pass + ", songID=" + songID + ", coinCount=" + coinCount
				+ ", hasCoinsVerified=" + hasCoinsVerified + ", levelVersion=" + levelVersion + ", gameVersion="
				+ gameVersion + ", objectCount=" + objectCount + ", isDemon=" + isDemon + ", isAuto=" + isAuto
				+ ", originalLevelID=" + originalLevelID + ", audioTrack=" + audioTrack + ", requestedStars="
				+ requestedStars + ", uploadTimestamp=" + uploadTimestamp + ", lastUpdatedTimestamp="
				+ lastUpdatedTimestamp + "]";
	}
}
