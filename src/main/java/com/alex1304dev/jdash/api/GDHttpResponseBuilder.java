package com.alex1304dev.jdash.api;

import com.alex1304dev.jdash.component.GDComponent;

/**
 * Builds a GDHttpResponse object according to the string returned by the server
 * 
 * @param T - the type of the response it's supposed to build
 * 
 * @author Alex1304
 */
public interface GDHttpResponseBuilder<T extends GDComponent> {
	
	/**
	 * Builds the GDComponent instance according to the given raw response and the status code
	 * 
	 * @return T
	 */
	public abstract T build(String rawResponse);
}
