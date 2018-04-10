package com.alex1304dev.jdash.api;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map.Entry;
import java.util.function.Consumer;

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

	/**
	 * @param accountID
	 *            - The GD account ID
	 * @param gjp
	 *            - The GD account password
	 */
	public GDHttpClient(long accountID, String password) {
		this.accountID = accountID;
		this.password = password;
	}
	
	/**
	 * Fetches the Geometry Dash API through a GDHttpRequest object. This method
	 * is NOT asynchroneous, make sure to call this method in an async task or a
	 * separate thread if you need to make some UI updates during loading time!
	 * 
	 * @param request
	 *            - the request object that contains the URL to request and the
	 *            POST parameters
	 * @param actionOnError
	 *            - a consumer that executes if an error occurs when fetching
	 *            data
	 * 
	 * @return a GDHttpResponse, or null if actionOnError has been executed
	 */
	public <T> GDHttpResponse<T> fetch(GDHttpRequest<T> request, Consumer<Exception> actionOnError)  {
		try {
			HttpURLConnection con;
			con = (HttpURLConnection) new URL(Constants.GD_API_URL + request.getPath()).openConnection();
			con.setRequestMethod("POST");
			con.setDoOutput(true);
			
			// Sending the request to the server
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			StringBuffer reqBody = new StringBuffer();
			
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
			
			GDHttpResponseFactory<T> factory = request.getResponseFactory();
			return factory.build(result.replaceAll("\n", ""), con.getResponseCode());
		} catch (IOException e) {
			actionOnError.accept(e);
			return null;
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
}
