package com.alex1304dev.jdash.api;

import java.util.Map;

import com.alex1304dev.jdash.component.GDComponent;

/**
 * Contains the parameters sent with the POST request to the Geometry Dash
 * servers
 * 
 * @param <E> - the expected type of the HTTP response
 * 
 * @author Alex1304
 *
 */
public abstract class GDHttpRequest<E extends GDComponent> {

	private Map<String, String> params;
	private String path;
	private GDHttpResponseFactory<E> responseFactory;

	public GDHttpRequest(String path, Map<String, String> params) {
		this.path = path;
		this.params = params;
		this.responseFactory = responseFactoryInstance();
	}

	/**
	 * Gets the request parameters.
	 * 
	 * @return a Map of String => String
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
	 * Gets the response factory object associated with this request.
	 * 
	 * @return GDHttpResponseFactory
	 */
	public GDHttpResponseFactory<E> getResponseFactory() {
		return responseFactory;
	}
	
	/**
	 * The role of this method is to build an instance of GDHttpResponseFactory
	 * that will be then returned by {@link GDHttpRequest#getResponseFactory()}
	 * This method is automatically called in the constructor of GDHttpRequest.
	 * 
	 * @return
	 */
	public abstract GDHttpResponseFactory<E> responseFactoryInstance();

}
