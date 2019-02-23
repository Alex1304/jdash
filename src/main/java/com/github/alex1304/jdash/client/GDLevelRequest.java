package com.github.alex1304.jdash.client;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import com.github.alex1304.jdash.entity.GDLevel;
import com.github.alex1304.jdash.entity.GDList;
import com.github.alex1304.jdash.entity.GDSong;
import com.github.alex1304.jdash.entity.GDUser;
import com.github.alex1304.jdash.entity.Length;
import com.github.alex1304.jdash.exception.GDClientException;
import com.github.alex1304.jdash.util.Indexes;
import com.github.alex1304.jdash.util.LevelSearchFilters;
import com.github.alex1304.jdash.util.LevelSearchFilters.Toggle;

import reactor.core.publisher.Mono;

import com.github.alex1304.jdash.util.LevelSearchStrategy;
import com.github.alex1304.jdash.util.ParseUtils;
import com.github.alex1304.jdash.util.Routes;
import com.github.alex1304.jdash.util.Utils;

class GDLevelRequest extends AbstractGDRequest<GDList<GDLevel>> {
	
	private final GeometryDashClient client;
	private final int page;
	private final String query;
	private final LevelSearchFilters filters;
	private final LevelSearchStrategy strategy;
	
	private GDLevelRequest(GeometryDashClient client, int page, String query, LevelSearchFilters filters,
			LevelSearchStrategy strategy) {
		this.client = Objects.requireNonNull(client);
		this.page = page;
		this.query = Objects.requireNonNull(query);
		this.filters = Objects.requireNonNull(filters);
		this.strategy = Objects.requireNonNull(strategy);
	}

	GDLevelRequest(GeometryDashClient client, String query, LevelSearchFilters filters, int page) {
		this(client, page, query, filters, LevelSearchStrategy.REGULAR);
	}
	
	GDLevelRequest(GeometryDashClient client, GDUser byUser, int page) {
		this(client, page, "" + byUser.getId(), LevelSearchFilters.create(), LevelSearchStrategy.BY_USER);
	}
	
	GDLevelRequest(GeometryDashClient client, LevelSearchStrategy strategy, LevelSearchFilters filters, int page) {
		this(client, page, "", filters, strategy);
	}

	@Override
	public String getPath() {
		return Routes.LEVEL_SEARCH;
	}

	@Override
	void putParams(Map<String, String> params) {
		params.put("type", "" + strategy.getVal());
		params.put("str", query);
		if (!filters.getDifficulties().isEmpty()) {
			params.put("diff", String.join(",", filters.getDifficulties().stream()
					.map(d -> "" + d.getVal()).collect(Collectors.toList())));
		}
		if (!filters.getLengths().isEmpty()) {
			params.put("len", String.join(",", filters.getLengths().stream()
					.map(d -> "" + d.ordinal()).collect(Collectors.toList())));
		}
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
	GDList<GDLevel> parseResponse0(String response) throws GDClientException {
		GDList<GDLevel> result = new GDList<>();
		String[] split1 = response.split("#");
		String levels = split1[0];
		String creators = split1[1];
		String songs = split1[2];
		Map<Long, String> structuredCreatorsInfo = ParseUtils.structureCreatorsInfo(creators);
		Map<Long, GDSong> structuredSongsInfo = ParseUtils.structureSongsInfo(songs);
		String[] levelArray = levels.split("\\|");
		for (int i = 0; i < levelArray.length; i++) {
			String l = levelArray[i];
			Map<Integer, String> lmap = ParseUtils.splitToMap(l, ":");
			long songID = Long.parseLong(Utils.defaultStringIfEmptyOrNull(lmap.get(Indexes.LEVEL_SONG_ID), "0"));
			GDSong song = songID <= 0 ?
					Utils.getAudioTrack(Integer.parseInt(Utils.defaultStringIfEmptyOrNull(lmap.get(Indexes.LEVEL_AUDIO_TRACK), "0"))):
					structuredSongsInfo.getOrDefault(songID, GDSong.unknownSong(songID));
			String creatorName = structuredCreatorsInfo.getOrDefault(Long.parseLong(
					Utils.defaultStringIfEmptyOrNull(lmap.get(Indexes.LEVEL_CREATOR_ID), "0")), "-");
			long levelId = Long.parseLong(Utils.defaultStringIfEmptyOrNull(lmap.get(Indexes.LEVEL_ID), "0"));
			result.add(new GDLevel(
					levelId,
					Utils.defaultStringIfEmptyOrNull(lmap.get(Indexes.LEVEL_NAME), "-"),
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
					() -> Mono.just(song),
					Integer.parseInt(Utils.defaultStringIfEmptyOrNull(lmap.get(Indexes.LEVEL_COIN_COUNT), "0")),
					Utils.defaultStringIfEmptyOrNull(lmap.get(Indexes.LEVEL_COIN_VERIFIED), "0").equals("1"),
					Integer.parseInt(Utils.defaultStringIfEmptyOrNull(lmap.get(Indexes.LEVEL_VERSION), "0")),
					Integer.parseInt(Utils.defaultStringIfEmptyOrNull(lmap.get(Indexes.LEVEL_GAME_VERSION), "0")),
					Integer.parseInt(Utils.defaultStringIfEmptyOrNull(lmap.get(Indexes.LEVEL_OBJECT_COUNT), "0")),
					Utils.defaultStringIfEmptyOrNull(lmap.get(Indexes.LEVEL_IS_DEMON), "0").equals("1"),
					Utils.defaultStringIfEmptyOrNull(lmap.get(Indexes.LEVEL_IS_AUTO), "0").equals("1"),
					Long.parseLong(Utils.defaultStringIfEmptyOrNull(lmap.get(Indexes.LEVEL_ORIGINAL), "0")),
					Integer.parseInt(Utils.defaultStringIfEmptyOrNull(lmap.get(Indexes.LEVEL_REQUESTED_STARS), "0")),
					creatorName,
					() -> client.fetch(new GDLevelDataRequest(levelId))
			));
		}
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof GDLevelRequest)) {
			return false;
		}
		GDLevelRequest r = (GDLevelRequest) obj;
		return r.page == page && r.query.equalsIgnoreCase(query) && r.filters.equals(filters) && r.strategy == strategy;
	}
	
	@Override
	public int hashCode() {
		return page ^ query.hashCode() ^ filters.hashCode() ^ strategy.hashCode();
	}
}
