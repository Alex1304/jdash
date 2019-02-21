package com.github.alex1304.jdash.client;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import com.github.alex1304.jdash.entity.GDLevelPart1;
import com.github.alex1304.jdash.entity.GDList;
import com.github.alex1304.jdash.entity.GDUser;
import com.github.alex1304.jdash.exception.GDClientException;
import com.github.alex1304.jdash.util.LevelSearchFilters;
import com.github.alex1304.jdash.util.LevelSearchFilters.Toggle;
import com.github.alex1304.jdash.util.LevelSearchStrategy;
import com.github.alex1304.jdash.util.Routes;

class GDLevelPart2Request extends AbstractGDRequest<GDList<GDLevelPart1>> {
	
	private final int page;
	private final String query;
	private final LevelSearchFilters filters;
	private final LevelSearchStrategy strategy;
	
	public GDLevelPart2Request(String query, LevelSearchFilters filters, int page) {
		this.query = Objects.requireNonNull(query);
		this.filters = Objects.requireNonNull(filters);
		this.strategy = LevelSearchStrategy.LEVEL_SEARCH_TYPE_REGULAR;
		this.page = page;
	}
	
	public GDLevelPart2Request(GDUser byUser, int page) {
		this.query = "" + byUser.getId();
		this.filters = LevelSearchFilters.create();
		this.strategy = LevelSearchStrategy.LEVEL_SEARCH_TYPE_BY_USER;
		this.page = page;
	}
	
	public GDLevelPart2Request(LevelSearchStrategy strategy, LevelSearchFilters filters, int page) {
		this.query = "";
		this.filters = Objects.requireNonNull(filters);
		this.strategy = Objects.requireNonNull(strategy);
		this.page = page;
	}

	@Override
	public String getPath() {
		return Routes.LEVEL_SEARCH;
	}

	@Override
	void putParams(Map<String, String> params) {
		params.put("type", "" + strategy.getVal());
		params.put("str", query);
		params.put("diff", String.join(",", filters.getDifficulties().stream()
				.map(d -> "" + d.getVal()).collect(Collectors.toList())));
		params.put("len", String.join(",", filters.getLengths().stream()
				.map(d -> "" + d.ordinal()).collect(Collectors.toList())));
		params.put("page", "" + page);
		params.put("uncompleted", filters.getToggles().contains(Toggle.UNCOMPLETED) ? "1" : "0");
		params.put("onlyCompleted", filters.getToggles().contains(Toggle.ONLY_COMPLETED) ? "1" : "0");
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
