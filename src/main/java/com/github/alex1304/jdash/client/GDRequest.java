package com.github.alex1304.jdash.client;

import java.util.Map;

import com.github.alex1304.jdash.entity.GDEntity;
import com.github.alex1304.jdash.exception.GDClientException;

/**
 * A request is what is sent to a {@link GeometryDashClient} in order to perform
 * an HTTP request to Geometry Dash servers.
 * 
 * @param <E> the type of entity this request is dealing with
 */
public interface GDRequest<E extends GDEntity> {
	/**
	 * Gets the path of the request.
	 * 
	 * @return the path
	 */
	String getPath();
	
	/**
	 * Gets the POST parameters to include in the request body
	 * 
	 * @return an Map containing parameter names and their values.
	 */
	Map<String, String> getParams();
	
	/**
	 * Parses a String response into a GDEntity
	 * 
	 * @return the entity resulting of the parsing
	 * @throws GDClientException if the response corresponds to a regular API error code
	 * @throws IllegalArgumentException if the response could not be parsed
	 */
	E parseResponse(String response) throws GDClientException;
}
