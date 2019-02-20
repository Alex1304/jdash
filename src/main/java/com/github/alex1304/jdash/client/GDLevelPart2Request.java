package com.github.alex1304.jdash.client;

import java.util.Map;
import java.util.Objects;

import com.github.alex1304.jdash.entity.GDLevelPart1;
import com.github.alex1304.jdash.entity.GDList;
import com.github.alex1304.jdash.entity.LevelSearchFilters;
import com.github.alex1304.jdash.exception.GDClientException;
import com.github.alex1304.jdash.util.Routes;

class GDLevelPart2Request extends AbstractGDRequest<GDList<GDLevelPart1>> {
	
	private final LevelSearchFilters filters;
	
	public GDLevelPart2Request(LevelSearchFilters filters) {
		this.filters = Objects.requireNonNull(filters);
	}

	@Override
	public String getPath() {
		return Routes.LEVEL_SEARCH;
	}

	@Override
	void putParams(Map<String, String> params) {
	}

	@Override
	GDList<GDLevelPart1> parseResponse0(String response) throws GDClientException {
		return null;
	}
	
//	@Override
//	public boolean equals(Object obj) {
//		
//	}
}
