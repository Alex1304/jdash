package com.github.alex1304.jdash.client;

import java.util.Map;
import java.util.Objects;

import com.github.alex1304.jdash.entity.GDTimelyLevel;
import com.github.alex1304.jdash.entity.GDTimelyLevel.TimelyType;
import com.github.alex1304.jdash.exception.GDClientException;
import com.github.alex1304.jdash.exception.NoTimelyAvailableException;
import com.github.alex1304.jdash.util.Routes;

public class GDTimelyRequest extends AbstractGDRequest<GDTimelyLevel> {
	
	private final TimelyType type;

	public GDTimelyRequest(AbstractGDClient client, TimelyType type) {
		super(client);
		this.type = Objects.requireNonNull(type);
	}

	@Override
	public String getPath() {
		return Routes.GET_TIMELY;
	}

	@Override
	void putParams(Map<String, String> params) {
		params.put("weekly", type == TimelyType.WEEKLY ? "1" : "0");
	}

	@Override
	GDTimelyLevel parseResponse0(String response) throws GDClientException {
		if (response.length() < 3) {
			throw new NoTimelyAvailableException();
		}
		if (!response.matches("[0-9]+\\|[0-9]+")) {
			throw new IllegalArgumentException("Unknown response");
		}
		String[] split = response.split("\\|");
		long timelyNumber = Long.parseLong(split[0]) % 100_000;
		return new GDTimelyLevel(timelyNumber, Long.parseLong(split[1]), () -> client.fetch(new GDLevelSingleRequest(client, type.getDownloadId())), type);
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj instanceof GDTimelyRequest && ((GDTimelyRequest) obj).type == type;
	}
	
	@Override
	public int hashCode() {
		return type.hashCode();
	}
	
	@Override
	public boolean cacheable() {
		return false;
	}
}
