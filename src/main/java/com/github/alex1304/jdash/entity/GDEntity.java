package com.github.alex1304.jdash.entity;

/**
 * Represents anything on Geometry Dash that has a unique ID (a user, a level, a private message...)
 */
public interface GDEntity {
	/**
	 * The unique identifier for the entity.
	 * 
	 * @return an ID
	 */
	long getId();
}
