package com.github.alex1304.jdash.client;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import com.github.alex1304.jdash.entity.GDLevel;
import com.github.alex1304.jdash.entity.GDSong;
import com.github.alex1304.jdash.entity.GDUser;
import com.github.alex1304.jdash.entity.Length;
import com.github.alex1304.jdash.exception.GDClientException;
import com.github.alex1304.jdash.util.GDPaginator;
import com.github.alex1304.jdash.util.Indexes;
import com.github.alex1304.jdash.util.LevelSearchFilters;
import com.github.alex1304.jdash.util.LevelSearchFilters.Toggle;
import com.github.alex1304.jdash.util.LevelSearchStrategy;
import com.github.alex1304.jdash.util.ParseUtils;
import com.github.alex1304.jdash.util.Routes;
import com.github.alex1304.jdash.util.Utils;

import reactor.core.publisher.Mono;
import reactor.util.function.Tuple3;

class GDLevelSearchRequest extends AbstractGDRequest<GDPaginator<GDLevel>> {
	
	private final int page;
	private final String query;
	private final LevelSearchFilters filters;
	private final LevelSearchStrategy strategy;
	private Collection<? extends GDUser> followed;
	
	private GDLevelSearchRequest(AbstractGDClient client, int page, String query, LevelSearchFilters filters,
			LevelSearchStrategy strategy, Collection<? extends GDUser> followed) {
		super(client);
		this.page = page;
		this.query = Objects.requireNonNull(query);
		this.filters = Objects.requireNonNull(filters);
		this.strategy = Objects.requireNonNull(strategy);
		this.followed = Objects.requireNonNull(followed);
	}

	GDLevelSearchRequest(AbstractGDClient client, String query, LevelSearchFilters filters, int page) {
		this(client, page, query, filters, LevelSearchStrategy.REGULAR, Collections.emptySet());
	}
	
	GDLevelSearchRequest(AbstractGDClient client, GDUser byUser, int page) {
		this(client, page, "" + byUser.getId(), LevelSearchFilters.create(), LevelSearchStrategy.BY_USER, Collections.emptySet());
	}
	
	GDLevelSearchRequest(AbstractGDClient client, LevelSearchStrategy strategy, LevelSearchFilters filters, int page) {
		this(client, page, "", filters, strategy, Collections.emptySet());
	}
	
	GDLevelSearchRequest(AbstractGDClient client, LevelSearchFilters filters, Collection<? extends GDUser> followed, int page) {
		this(client, page, "", filters, LevelSearchStrategy.FOLLOWED, followed);
	}

	@Override
	public String getPath() {
		return Routes.LEVEL_SEARCH;
	}

	@Override
	void putParams(Map<String, String> params) {
		params.put("type", "" + strategy.getVal());
		params.put("str", Utils.urlEncode(query));
		if (!filters.getDifficulties().isEmpty()) {
			params.put("diff", String.join(",", filters.getDifficulties().stream()
					.map(d -> "" + d.getVal()).collect(Collectors.toList())));
		}
		if (!filters.getLengths().isEmpty()) {
			params.put("len", String.join(",", filters.getLengths().stream()
					.map(d -> "" + d.ordinal()).collect(Collectors.toList())));
		}
		params.put("page", "" + page);
		params.put("uncompleted", filters.hasToggle(Toggle.UNCOMPLETED) ? "1" : "0");
		params.put("onlyCompleted", filters.hasToggle(Toggle.ONLY_COMPLETED) ? "1" : "0");
		params.put("featured", filters.hasToggle(Toggle.FEATURED) ? "1" : "0");
		params.put("original", filters.hasToggle(Toggle.ORIGINAL) ? "1" : "0");
		params.put("twoPlayer", filters.hasToggle(Toggle.TWO_PLAYER) ? "1" : "0");
		params.put("coins", filters.hasToggle(Toggle.COINS) ? "1" : "0");
		params.put("epic", filters.hasToggle(Toggle.EPIC) ? "1" : "0");
		params.put("star", filters.hasToggle(Toggle.STAR) ? "1" : "0");
		if (filters.hasToggle(Toggle.NO_STAR)) {
			params.put("noStar", "1");
		}
		if (filters.getSongFilter().isPresent()) {
			params.put("song", "" + filters.getSongFilter().map(GDSong::getId).get());
			params.put("customSong", filters.getSongFilter().map(GDSong::isCustom).get() ? "1" : "0");
		}
		if (filters.getDemon().isPresent()) {
			params.put("demonFilter", "" + (filters.getDemon().get().ordinal() + 1));
		}
		if ((filters.hasToggle(Toggle.ONLY_COMPLETED) || filters.hasToggle(Toggle.UNCOMPLETED)) && !filters.getCompletedLevels().isEmpty()) {
			params.put("completedLevels", "(" + String.join(",", filters.getCompletedLevels().stream()
					.map(GDLevel::getId).map(String::valueOf).collect(Collectors.toSet())) + ")");
		}
		if (!followed.isEmpty()) {
			params.put("followed", String.join(",", followed.stream()
					.map(GDUser::getAccountId).map(String::valueOf).collect(Collectors.toSet())));
		}
	}

	@Override
	GDPaginator<GDLevel> parseResponse0(String response) throws GDClientException {
		ArrayList<GDLevel> list = new ArrayList<>();
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
			list.add(new GDLevel(
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
					() -> client.fetch(new GDLevelDataRequest(client, levelId)),
					() -> client.fetch(new GDLevelSearchRequest(client, "" + levelId, LevelSearchFilters.create(), 0))
							.map(paginator -> paginator.asList().get(0))
			));
		}
		Tuple3<Integer, Integer, Integer> pageInfo = ParseUtils.extractPageInfo(split1[3]);
		return new GDPaginator<>(list, page, pageInfo.getT3(), pageInfo.getT1(), newPage ->
				client.fetch(new GDLevelSearchRequest(client, newPage, query, filters, strategy, followed)));
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof GDLevelSearchRequest)) {
			return false;
		}
		GDLevelSearchRequest r = (GDLevelSearchRequest) obj;
		return r.page == page && r.query.equalsIgnoreCase(query) && r.filters.equals(filters) && r.strategy == strategy && r.followed.equals(followed);
	}
	
	@Override
	public int hashCode() {
		return page ^ query.hashCode() ^ filters.hashCode() ^ strategy.hashCode() ^ followed.hashCode();
	}
	
	@Override
	public boolean cacheable() {
		return false;
	}
}
