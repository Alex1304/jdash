package com.alex1304dev.jdash.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Useful constants to be uses anywhere in the project.
 * 
 * @author Alex1304
 *
 */
public interface Constants {

	/**
	 * The API URL of Geometry Dash
	 */
	public static final String GD_API_URL = "http://www.boomlings.com/database";

	/**
	 * Global params to be put in each request that doesn't require
	 * authentication
	 * 
	 * @return a Map of String, String, representing the global params.
	 */
	static Map<String, String> globalHttpRequestParams() {
		Map<String, String> params = new HashMap<>();
		params.put("gameVersion", "21");
		params.put("binaryVersion", "34");
		params.put("gdw", "0");
		params.put("secret", "Wmfd2893gb7");

		return params;
	}

	/**
	 * Global params to be put in each request where authentication is required
	 * 
	 * @param accountID
	 *            - the client account ID
	 * @param password
	 *            - the client account password
	 * @return a Map of String, String, representing the global params with
	 *         authentication info
	 */
	static Map<String, String> globalHttpRequestParamsWithAuthentication(long accountID, String password) {
		Map<String, String> params = globalHttpRequestParams();
		params.put("accountID", "" + accountID);
		params.put("gjp", password);

		return params;
	}
}
