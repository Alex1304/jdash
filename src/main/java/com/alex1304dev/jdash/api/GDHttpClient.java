package com.alex1304dev.jdash.api;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map.Entry;

import com.alex1304dev.jdash.component.GDComponent;
import com.alex1304dev.jdash.exceptions.GDAPIException;
import com.alex1304dev.jdash.util.Constants;

/**
 * Handles HTTP requests to Geometry Dash servers
 * 
 * @author Alex1304
 *
 */
public class GDHttpClient {

	private long accountID;
	private String password;
	private boolean isAuthenticated;

	/**
	 * @param accountID
	 *            - The GD account ID
	 * @param gjp
	 *            - The GD account password
	 */
	public GDHttpClient(long accountID, String password) {
		this.accountID = accountID;
		this.password = password;
		this.isAuthenticated = true;
	}
	
	/**
	 * Constructor that creates an anonymous (logged out) client.
	 */
	public GDHttpClient() {
		this.isAuthenticated = false;
	}
	
	/**
	 * Fetches the Geometry Dash API through a GDHttpRequest object. This method
	 * is NOT asynchroneous, make sure to call this method in an async task or a
	 * separate thread if you need to make some UI updates during loading time!
	 * 
	 * @param request
	 *            - the request object that contains the URL to request and the
	 *            POST parameters
	 * 
	 * @return a GDHttpResponse
	 */
	public <T extends GDComponent> T fetch(GDHttpRequest<T> request) throws GDAPIException  {
		try {
			HttpURLConnection con;
			con = (HttpURLConnection) new URL(Constants.GD_API_URL + request.getPath()).openConnection();
			con.setRequestMethod("POST");
			con.setDoOutput(true);
			
			// Sending the request to the server
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			StringBuffer reqBody = new StringBuffer();
			
			request.getParams().putAll(request.requiresAuthentication()
					? Constants.globalHttpRequestParamsWithAuthentication(accountID, password)
					: Constants.globalHttpRequestParams());
			
			for (Entry<String, String> param: request.getParams().entrySet())
				reqBody.append(param.getKey() + "=" + param.getValue() + "&");
			
			reqBody.deleteCharAt(reqBody.length() - 1);
			
			wr.writeBytes(reqBody.toString());
			wr.flush();
			wr.close();
			
			// Fetching response
			String result = "";
			BufferedReader rd = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String line;
			while ((line = rd.readLine()) != null) {
				result += line + "\n";
			}
			
			result = result.replaceAll("\n", "");
			
			GDHttpResponseBuilder<T> builder = request.getResponseBuilder();
			return builder.build(result);
		} catch (IOException | RuntimeException e) {
			throw new GDAPIException(e.getMessage());
		}
	}

	/**
	 * Gets the GD account ID
	 * 
	 * @return long
	 */
	public long getAccountID() {
		return accountID;
	}

	/**
	 * Gets the GD account password
	 * 
	 * @return String
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Gets whether the client is authenticated with a GD account
	 * 
	 * @return boolean
	 */
	public boolean isAuthenticated() {
		return isAuthenticated;
	}
}
