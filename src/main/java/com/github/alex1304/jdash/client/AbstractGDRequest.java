package com.github.alex1304.jdash.client;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.github.alex1304.jdash.exception.GDClientException;
import com.github.alex1304.jdash.exception.MissingAccessException;

abstract class AbstractGDRequest<E> implements GDRequest<E> {

	final GeometryDashClient client;
	
	AbstractGDRequest(GeometryDashClient client) {
		this.client = Objects.requireNonNull(client);
	}

	@Override
	public Map<String, String> getParams() {
		Map<String, String> params = new HashMap<>();
		putParams(params);
		return params;
	}
	
	abstract void putParams(Map<String, String> params);

	@Override
	public E parseResponse(String response) throws GDClientException {
		if (response.startsWith("-1")) {
			throw new MissingAccessException();
		}
		return parseResponse0(response);
	}
	
	abstract E parseResponse0(String response) throws GDClientException;
	
	@Override
	public boolean cacheable() {
		return true;
	}
}
