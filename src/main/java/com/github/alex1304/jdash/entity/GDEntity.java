package com.github.alex1304.jdash.entity;

import com.github.alex1304.jdash.client.GDClient;

public interface GDEntity {

	/**
	 * Gets the client backing this entity.
	 * 
	 * @return the client
	 */
	GDClient client();
}
