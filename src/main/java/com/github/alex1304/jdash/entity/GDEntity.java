package com.github.alex1304.jdash.entity;

import com.github.alex1304.jdash.client.GeometryDashClient;

/**
 * Represents anything on Geometry Dash that can be fetched via the {@link GeometryDashClient} (a user, a level, a private message...)
 */
public interface GDEntity {
	/**
	 * The unique identifier for the entity.
	 * 
	 * @return an ID
	 */
	long getId();
}
