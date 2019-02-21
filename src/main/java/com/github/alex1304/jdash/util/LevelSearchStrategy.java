package com.github.alex1304.jdash.util;

public enum LevelSearchStrategy {
	LEVEL_SEARCH_TYPE_REGULAR(0),
	LEVEL_SEARCH_TYPE_MOST_DOWNLOADED(1),
	LEVEL_SEARCH_TYPE_MOST_LIKED(2),
	LEVEL_SEARCH_TYPE_TRENDING(3),
	LEVEL_SEARCH_TYPE_RECENT(4),
	LEVEL_SEARCH_TYPE_BY_USER(5),
	LEVEL_SEARCH_TYPE_FEATURED(6),
	LEVEL_SEARCH_TYPE_MAGIC(7),
	LEVEL_SEARCH_TYPE_AWARDED(11),
	LEVEL_SEARCH_TYPE_FOLLOWED(11),
	LEVEL_SEARCH_TYPE_HALL_OF_FAME(16);
	
	private final int val;
	
	LevelSearchStrategy(int val) {
		this.val = val;
	}

	public int getVal() {
		return val;
	}
}
