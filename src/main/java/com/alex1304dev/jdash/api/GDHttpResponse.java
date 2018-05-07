package com.alex1304dev.jdash.api;

import com.alex1304dev.jdash.component.GDComponent;

/**
 * Stores the response returned by the Geometry Dash servers.
 * 
 * @param <T>
 *            - the response type
 * 
 * @author Alex1304
 *
 */
public class GDHttpResponse<T extends GDComponent> {

	private T response;
	private int statusCode;

	/**
	 * @param response
	 *            - the response object
	 * @param statusCode
	 *            - the HTTP status code of the response
	 */
	public GDHttpResponse(T response, int statusCode) {
		this.response = response;
		this.statusCode = statusCode;
	}

	/**
	 * Gets the response object
	 * 
	 * @return T
	 */
	public T getResponse() {
		return response;
	}

	/**
	 * Gets the HTTP status code of the response
	 * 
	 * @return int
	 */
	public int getStatusCode() {
		return statusCode;
	}

}