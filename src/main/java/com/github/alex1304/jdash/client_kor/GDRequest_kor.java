package com.github.alex1304.jdash.client;

import java.util.Map;

import com.github.alex1304.jdash.exception.GDClientException;

/**
 * A request is what is sent to a {@link AnonymousGDClient} in order to perform
 * an HTTP request to Geometry Dash servers.(요청은 {@link AnonymousGDClient} 에 전송되는 것이다.지오메트리 대시 서버에 대한 HTTP 요청을 수행하려면 다음과 같이 하십시오.)
 * 
 * @param <E> the type of object this request is dealing with
 */
interface GDRequest<E> {
	/**
	 * Gets the path of the request.(요청 경로를 가져오십시오.)
	 * 
	 * @return the path
	 */
	String getPath();
	
	/**
	 * Gets the POST parameters to include in the request body(요청 본문에 포함할 POST 매개변수를 가져오십시오.)
	 * 
	 * @return an Map containing parameter names and their values.
	 */
	Map<String, String> getParams();
	
	/**
	 * Parses a String response into a GDEntity(문자열 응답을 GDEntity로 구문 분석)
	 * 
	 * @return the entity resulting of the parsing
	 * @throws GDClientException if the response corresponds to a regular API error code
	 * @throws IllegalArgumentException if the response could not be parsed
	 */
	E parseResponse(String response) throws GDClientException;
	
	/**
	 * Whether cache should be disabled for this request.(이 요청에 대해 캐시를 사용하지 않도록 설정해야 하는지 여부.)
	 * 
	 * @return boolean
	 */
	boolean cacheable();
}
