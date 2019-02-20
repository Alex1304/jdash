package com.github.alex1304.jdash.entity;

import java.util.Objects;

/**
 * Represents a level in the game.
 * 
 * @author Alex1304
 *
 */
public class GDLevelPart1 extends AbstractGDLevel {

	private final int pass;
	private final String uploadTimestamp;
	private final String lastUpdatedTimestamp;
	private final byte[] data;

	public GDLevelPart1(long id, String name, String creatorName, long creatorID, String description,
			Difficulty difficulty, DemonDifficulty demonDifficulty, int stars, int featuredScore, boolean isEpic,
			int downloads, int likes, Length length, GDSong song, int coinCount, boolean hasCoinsVerified,
			int levelVersion, int gameVersion, int objectCount, boolean isDemon, boolean isAuto, long originalLevelID,
			int requestedStars, int pass, String uploadTimestamp, String lastUpdatedTimestamp, byte[] data) {
		super(id, name, creatorID, description, difficulty, demonDifficulty, stars, featuredScore, isEpic, downloads,
				likes, length, song, coinCount, hasCoinsVerified, levelVersion, gameVersion, objectCount, isDemon,
				isAuto, originalLevelID, requestedStars);
		this.pass = pass;
		this.uploadTimestamp = Objects.requireNonNull(uploadTimestamp);
		this.lastUpdatedTimestamp = Objects.requireNonNull(lastUpdatedTimestamp);
		this.data = Objects.requireNonNull(data);
	}

	public int getPass() {
		return pass;
	}

	public String getUploadTimestamp() {
		return uploadTimestamp;
	}

	public String getLastUpdatedTimestamp() {
		return lastUpdatedTimestamp;
	}

	public byte[] getData() {
		return data;
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj instanceof GDLevelPart1 && super.equals(obj);
	}

	@Override
	public String toString() {
		return "GDLevelPart1 [pass=" + pass + ", uploadTimestamp=" + uploadTimestamp + ", lastUpdatedTimestamp="
				+ lastUpdatedTimestamp + ", data=" + data.length + "B, name=" + name + ", creatorID="
				+ creatorID + ", description=" + description + ", difficulty=" + difficulty + ", demonDifficulty="
				+ demonDifficulty + ", stars=" + stars + ", featuredScore=" + featuredScore + ", isEpic=" + isEpic
				+ ", downloads=" + downloads + ", likes=" + likes + ", length=" + length + ", song=" + song
				+ ", coinCount=" + coinCount + ", hasCoinsVerified=" + hasCoinsVerified + ", levelVersion="
				+ levelVersion + ", gameVersion=" + gameVersion + ", objectCount=" + objectCount + ", isDemon="
				+ isDemon + ", isAuto=" + isAuto + ", originalLevelID=" + originalLevelID + ", requestedStars="
				+ requestedStars + ", id=" + id + "]";
	}
}
