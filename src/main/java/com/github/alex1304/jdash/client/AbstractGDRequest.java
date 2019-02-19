package com.github.alex1304.jdash.client;

import java.util.HashMap;
import java.util.Map;

import com.github.alex1304.jdash.entity.GDEntity;

abstract class AbstractGDRequest<E extends GDEntity> implements GDRequest<E> {

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
}
