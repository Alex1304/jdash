package com.alex1304dev.jdash.api;

/**
 * Builds a GDHttpResponse object according to the string returned by the server
 * 
 * @param T - the type of the response the factory is supposed to build
 * 
 * @author Alex1304
 *
 */
public interface GDHttpResponseFactory<T> {
	
	/**
	 * Builds the GDHttpResponse instance according to the given raw response and the status code
	 * 
	 * @return GDHttpResponse
	 */
	public abstract GDHttpResponse<T> build(String rawResponse, int statusCode);
}
