package com.github.alex1304.jdash.entity;

import java.util.Objects;

public class GDLevel extends AbstractGDLevel {

	private final String creatorName;
	private final int pass;
	private final String uploadTimestamp;
	private final String lastUpdatedTimestamp;
	private final byte[] data;

	GDLevel(long id, String name, long creatorID, String description, Difficulty difficulty,
			DemonDifficulty demonDifficulty, int stars, int featuredScore, boolean isEpic, int downloads, int likes,
			Length length, GDSong song, int coinCount, boolean hasCoinsVerified, int levelVersion, int gameVersion,
			int objectCount, boolean isDemon, boolean isAuto, long originalLevelID, int requestedStars,
			String creatorName, int pass, String uploadTimestamp, String lastUpdatedTimestamp, byte[] data) {
		super(id, name, creatorName, creatorID, description, difficulty, demonDifficulty, stars, featuredScore, isEpic, downloads,
				likes, length, song, coinCount, hasCoinsVerified, levelVersion, gameVersion, objectCount, isDemon,
				isAuto, originalLevelID, requestedStars);
		this.creatorName = Objects.requireNonNull(creatorName);
		this.pass = pass;
		this.uploadTimestamp = Objects.requireNonNull(uploadTimestamp);
		this.lastUpdatedTimestamp = Objects.requireNonNull(lastUpdatedTimestamp);
		this.data = Objects.requireNonNull(data);
	}

	public static GDLevel aggregate(GDLevelPart1 part1, GDLevelPart2 part2) {
		return new GDLevel(part1.id, part1.name, part1.creatorID, part1.description, part1.difficulty,
				part1.demonDifficulty, part1.stars, part1.featuredScore, part1.isEpic, part1.downloads, part1.likes,
				part1.length, part2.song, part1.coinCount, part1.hasCoinsVerified, part1.levelVersion,
				part1.gameVersion, part1.objectCount, part1.isDemon, part1.isAuto, part1.originalLevelID,
				part1.requestedStars, part2.getCreatorName(), part1.getPass(), part1.getUploadTimestamp(),
				part1.getLastUpdatedTimestamp(), part1.getData());
	}

	public String getCreatorName() {
		return creatorName;
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
		return obj instanceof GDLevel && super.equals(obj);
	}

	@Override
	public String toString() {
		return "GDLevel [creatorName=" + creatorName + ", pass=" + pass + ", uploadTimestamp=" + uploadTimestamp
				+ ", lastUpdatedTimestamp=" + lastUpdatedTimestamp + ", data={" + data.length + " Bytes}, name="
				+ name + ", creatorID=" + creatorID + ", description=" + description + ", difficulty=" + difficulty
				+ ", demonDifficulty=" + demonDifficulty + ", stars=" + stars + ", featuredScore=" + featuredScore
				+ ", isEpic=" + isEpic + ", downloads=" + downloads + ", likes=" + likes + ", length=" + length
				+ ", song=" + song + ", coinCount=" + coinCount + ", hasCoinsVerified=" + hasCoinsVerified
				+ ", levelVersion=" + levelVersion + ", gameVersion=" + gameVersion + ", objectCount=" + objectCount
				+ ", isDemon=" + isDemon + ", isAuto=" + isAuto + ", originalLevelID=" + originalLevelID
				+ ", requestedStars=" + requestedStars + ", id=" + id + "]";
	}
}
