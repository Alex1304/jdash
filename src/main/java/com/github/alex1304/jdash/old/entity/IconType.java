package com.github.alex1304.jdash.old.entity;

import java.util.Objects;
import java.util.function.ToIntFunction;

public enum IconType {
	CUBE(GDUser::getCubeIconId),
	SHIP(GDUser::getShipIconId),
	BALL(GDUser::getBallIconId),
	UFO(GDUser::getUfoIconId),
	WAVE(GDUser::getWaveIconId),
	ROBOT(GDUser::getRobotIconId),
	SPIDER(GDUser::getSpiderIconId);
	
	private ToIntFunction<GDUser> idGetter;
	
	IconType(ToIntFunction<GDUser> idGetter) {
		this.idGetter = idGetter;
	}
	
	public int idForUser(GDUser user) {
		return idGetter.applyAsInt(Objects.requireNonNull(user));
	}
}
