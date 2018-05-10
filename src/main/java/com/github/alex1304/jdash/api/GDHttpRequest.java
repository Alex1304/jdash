package com.github.alex1304.jdash.api;

import java.util.HashMap;
import java.util.Map;

import com.github.alex1304.jdash.component.GDComponent;

/**
 * Contains the parameters sent with the POST request to the Geometry Dash
 * servers. The role of this class is also to return a response factory, which
 * defines how the response to this request should be parsed.
 * 
 * @param <E> - the expected type of the HTTP response
 * 
 * @author Alex1304
 *
 */
public abstract class GDHttpRequest<E extends GDComponent> {

	private Map<String, String> params;
	private String path;
	private GDHttpResponseBuilder<E> response;
	private boolean requiresAuthentication;

	public GDHttpRequest(String path, boolean requiresAuthentication) {
		this.path = path;
		this.params = new HashMap<>();
		this.requiresAuthentication = requiresAuthentication;
		this.response = responseBuilderInstance();
	}

	/**
	 * Gets the request parameters.
	 * 
	 * @return a Map of String, String
	 */
	public Map<String, String> getParams() {
		return params;
	}

	/**
	 * Gets the path of the target URL
	 * 
	 * @return String
	 */
	public String getPath() {
		return path;
	}
	
	/**
	 * Gets the response builder object associated with this request.
	 * 
	 * @return GDHttpResponseBuilder
	 */
	public GDHttpResponseBuilder<E> getResponseBuilder() {
		return response;
	}
	
	/**
	 * The role of this method is to build an instance of GDHttpResponseFactory
	 * that will be then returned by {@link GDHttpRequest#getResponseBuilder()}
	 * This method is automatically called in the constructor of GDHttpRequest.
	 * 
	 * @return GDHttpResponseBuilder
	 */
	public abstract GDHttpResponseBuilder<E> responseBuilderInstance();

	/**
	 * Gets whether the request requires to be authenticated
	 * 
	 * @return boolean
	 */
	public boolean requiresAuthentication() {
		return requiresAuthentication;
	}

}
