package com.github.alex1304.jdash.old.client;

import java.util.Map;
import java.util.Objects;

import com.github.alex1304.jdash.old.exception.GDClientException;
import com.github.alex1304.jdash.old.util.Routes;

class GDLoginRequest extends AbstractGDRequest<long[]> {

	private final String username;
	private final String password;
	private final String udid;
	
	GDLoginRequest(AbstractGDClient client, String username, String password, String udid) {
		super(client);
		this.username = Objects.requireNonNull(username);
		this.password = Objects.requireNonNull(password);
		this.udid = Objects.requireNonNull(udid);
	}

	@Override
	public String getPath() {
		return Routes.LOGIN;
	}

	@Override
	void putParams(Map<String, String> params) {
		params.put("userName", username);
		params.put("password", password);
		params.put("udid", udid);
		params.put("secret", "Wmfv3899gc9"); // Overrides the default one
	}

	@Override
	long[] parseResponse0(String response) throws GDClientException {
		String[] sp = response.split(",");
		return new long[] { Long.parseLong(sp[0]), Long.parseLong(sp[1]) };
	}
	
	@Override
	public boolean cacheable() {
		return false;
	}
}
