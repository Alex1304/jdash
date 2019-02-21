package com.github.alex1304.jdash.client;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import com.github.alex1304.jdash.entity.GDLevel;
import com.github.alex1304.jdash.entity.GDLevelPart2;
import com.github.alex1304.jdash.entity.GDList;
import com.github.alex1304.jdash.entity.GDSong;
import com.github.alex1304.jdash.entity.GDUser;
import com.github.alex1304.jdash.entity.Length;
import com.github.alex1304.jdash.exception.GDClientException;
import com.github.alex1304.jdash.util.Indexes;
import com.github.alex1304.jdash.util.LevelSearchFilters;
import com.github.alex1304.jdash.util.LevelSearchFilters.Toggle;
import com.github.alex1304.jdash.util.LevelSearchStrategy;
import com.github.alex1304.jdash.util.Routes;
import com.github.alex1304.jdash.util.Utils;

class GDLevelPart2Request extends AbstractGDRequest<GDList<GDLevelPart2>> {
	
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
		params.put("featured", filters.getToggles().contains(Toggle.FEATURED) ? "1" : "0");
		params.put("original", filters.getToggles().contains(Toggle.ORIGINAL) ? "1" : "0");
		params.put("twoPlayer", filters.getToggles().contains(Toggle.TWO_PLAYER) ? "1" : "0");
		params.put("coins", filters.getToggles().contains(Toggle.COINS) ? "1" : "0");
		params.put("epic", filters.getToggles().contains(Toggle.EPIC) ? "1" : "0");
		params.put("star", filters.getToggles().contains(Toggle.STAR) ? "1" : "0");
		if (filters.getToggles().contains(Toggle.NO_STAR)) {
			params.put("noStar", "1");
		}
		if (filters.getSongFilter().isPresent()) {
			params.put("song", "" + filters.getSongFilter().map(GDSong::getId).get());
			params.put("customSong", filters.getSongFilter().map(GDSong::isCustom).get() ? "1" : "0");
		}
		if (filters.getDemon().isPresent()) {
			params.put("demonFilter", "" + (filters.getDemon().get().ordinal() + 1));
		}
		if (!filters.getCompletedLevels().isEmpty()) {
			params.put("completedLevels", "(" + String.join(",", filters.getCompletedLevels().stream()
					.map(GDLevel::getId).map(String::valueOf).collect(Collectors.toSet())) + ")");
		}
		if (!filters.getFollowed().isEmpty()) {
			params.put("followed", String.join(",", filters.getFollowed().stream()
					.map(GDUser::getId).map(String::valueOf).collect(Collectors.toSet())));
		}
	}

	@Override
	GDList<GDLevelPart2> parseResponse0(String response) throws GDClientException {
		GDList<GDLevelPart2> result = new GDList<>();
		String[] split1 = response.split("#");
		String levels = split1[0];
		String creators = split1[1];
		String songs = split1[2];
		Map<Long, String> structuredCreatorsInfo = Utils.structureCreatorsInfo(creators);
		Map<Long, GDSong> structuredSongsInfo = Utils.structureSongsInfo(songs);
		String[] levelArray = levels.split("\\|");
		for (int i = 0; i < levelArray.length; i++) {
			String l = levelArray[i];
			Map<Integer, String> lmap = Utils.splitToMap(l, ":");
			long songID = Long.parseLong(Utils.defaultStringIfEmptyOrNull(lmap.get(Indexes.LEVEL_SONG_ID), "0"));
			GDSong song = songID <= 0 ?
					Utils.getAudioTrack(Integer.parseInt(Utils.defaultStringIfEmptyOrNull(lmap.get(Indexes.LEVEL_AUDIO_TRACK), "0"))):
					structuredSongsInfo.getOrDefault(songID, GDSong.unknownSong(songID));
			String creatorName = structuredCreatorsInfo.getOrDefault(Long.parseLong(
					Utils.defaultStringIfEmptyOrNull(lmap.get(Indexes.LEVEL_CREATOR_ID), "0")), "-");
			result.add(new GDLevelPart2(
					Long.parseLong(Utils.defaultStringIfEmptyOrNull(lmap.get(Indexes.LEVEL_ID), "0")),
					Utils.defaultStringIfEmptyOrNull(lmap.get(Indexes.LEVEL_NAME), "-"),
					creatorName,
					Long.parseLong(Utils.defaultStringIfEmptyOrNull(lmap.get(Indexes.LEVEL_CREATOR_ID), "0")),
					Utils.b64Decode(Utils.defaultStringIfEmptyOrNull(lmap.get(Indexes.LEVEL_DESCRIPTION), "")),
					Utils.valueToDifficulty(Integer.parseInt(Utils.defaultStringIfEmptyOrNull(lmap.get(Indexes.LEVEL_DIFFICULTY), "0"))),
					Utils.valueToDemonDifficulty(Integer.parseInt(Utils.defaultStringIfEmptyOrNull(lmap.get(Indexes.LEVEL_DEMON_DIFFICULTY), "0"))),
					Integer.parseInt(Utils.defaultStringIfEmptyOrNull(lmap.get(Indexes.LEVEL_STARS), "0")),
					Integer.parseInt(Utils.defaultStringIfEmptyOrNull(lmap.get(Indexes.LEVEL_FEATURED_SCORE), "0")),
					!Utils.defaultStringIfEmptyOrNull(lmap.get(Indexes.LEVEL_IS_EPIC), "0").equals("0"),
					Integer.parseInt(Utils.defaultStringIfEmptyOrNull(lmap.get(Indexes.LEVEL_DOWNLOADS), "0")),
					Integer.parseInt(Utils.defaultStringIfEmptyOrNull(lmap.get(Indexes.LEVEL_LIKES), "0")),
					Length.values()[Integer.parseInt(Utils.defaultStringIfEmptyOrNull(lmap.get(Indexes.LEVEL_LENGTH), "0"))],
					song,
					Integer.parseInt(Utils.defaultStringIfEmptyOrNull(lmap.get(Indexes.LEVEL_COIN_COUNT), "0")),
					Utils.defaultStringIfEmptyOrNull(lmap.get(Indexes.LEVEL_COIN_VERIFIED), "0").equals("1"),
					Integer.parseInt(Utils.defaultStringIfEmptyOrNull(lmap.get(Indexes.LEVEL_VERSION), "0")),
					Integer.parseInt(Utils.defaultStringIfEmptyOrNull(lmap.get(Indexes.LEVEL_GAME_VERSION), "0")),
					Integer.parseInt(Utils.defaultStringIfEmptyOrNull(lmap.get(Indexes.LEVEL_OBJECT_COUNT), "0")),
					Utils.defaultStringIfEmptyOrNull(lmap.get(Indexes.LEVEL_IS_DEMON), "0").equals("1"),
					Utils.defaultStringIfEmptyOrNull(lmap.get(Indexes.LEVEL_IS_AUTO), "0").equals("1"),
					Long.parseLong(Utils.defaultStringIfEmptyOrNull(lmap.get(Indexes.LEVEL_ORIGINAL), "0")),
					Integer.parseInt(Utils.defaultStringIfEmptyOrNull(lmap.get(Indexes.LEVEL_REQUESTED_STARS), "0"))
					));
		}
		return result;
	}
	
//	@Override
//	public boolean equals(Object obj) {
//		
//	}
}
