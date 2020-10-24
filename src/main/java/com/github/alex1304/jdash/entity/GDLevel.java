package com.github.alex1304.jdash.entity;

import com.github.alex1304.jdash.common.DemonDifficulty;
import com.github.alex1304.jdash.common.Difficulty;
import com.github.alex1304.jdash.common.Length;
import com.github.alex1304.jdash.old.entity.GDSong;

public interface GDLevel extends GDEntity {

	long id();
	
	String name();
	
	long creatorId();
	
	String creatorName();
	
	String description();
	
	Difficulty difficulty();
	
	DemonDifficulty demonDifficulty();
	
	int stars();
	
	int featuredScore();
	
	boolean isEpic();
	
	int downloads();
	
	int likes();
	
	Length length();
	
	GDSong song();
	
	int coinCount();
	
	boolean hasCoinsVerified();
	
	int levelVersion();
	
	int gameVersion();
	
	int objectCount();
	
	int isDemon();
	
	int isAuto();
	
	long originalLevelId();
	
	int requestedStars();
	
	
}
