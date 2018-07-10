package com.github.alex1304.jdash.api.request;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Base64;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.github.alex1304.jdash.api.GDHttpRequest;
import com.github.alex1304.jdash.api.GDHttpResponseBuilder;
import com.github.alex1304.jdash.component.GDComponentList;
import com.github.alex1304.jdash.component.GDLevelPreview;
import com.github.alex1304.jdash.component.GDSong;
import com.github.alex1304.jdash.component.property.GDLevelLength;
import com.github.alex1304.jdash.util.Constants;
import com.github.alex1304.jdash.util.Utils;

/**
 * HTTP request to search for levels
 * 
 * @author Alex1304
 */
public class GDLevelSearchHttpRequest extends GDHttpRequest<GDComponentList<GDLevelPreview>> {
	
	public GDLevelSearchHttpRequest(String keywords, int page) {
		this(
			Constants.LEVEL_SEARCH_TYPE_REGULAR,
			keywords,
			new HashSet<>(),
			new HashSet<>(),
			page,
			false,
			false,
			false,
			false,
			false,
			false,
			false,
			false,
			0
		);
	}

	public GDLevelSearchHttpRequest(int type, String keywords, Set<Integer> difficulties, Set<Integer> lengths,
			int page, boolean uncompleted, boolean onlyCompleted, boolean featured, boolean original,
			boolean twoPlayer, boolean coins, boolean epic, boolean star, int demonFilter) {
		super("/getGJLevels21.php", false);
		this.getParams().put("type", "" + type);
		try {
			this.getParams().put("str", URLEncoder.encode(keywords, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			this.getParams().put("str", keywords);
			System.err.println("Unable to URLEncode search terms '" + keywords + "'");
		}
		this.getParams().put("diff", difficulties.isEmpty() ? "-" : Utils.setOfIntToString(difficulties));
		this.getParams().put("len", lengths.isEmpty() ? "-" : Utils.setOfIntToString(lengths));
		this.getParams().put("page", "" + page);
		this.getParams().put("total", "0");
		this.getParams().put("uncompleted", uncompleted ? "1" : "0");
		this.getParams().put("onlyCompleted", onlyCompleted ? "1" : "0");
		this.getParams().put("featured", featured ? "1" : "0");
		this.getParams().put("original", original ? "1" : "0");
		this.getParams().put("twoPlayer", twoPlayer ? "1" : "0");
		this.getParams().put("coins", coins ? "1" : "0");
		this.getParams().put("epic", epic ? "1" : "0");
		this.getParams().put("star", star ? "1" : "0");
		this.getParams().put("demonFilter", "" + demonFilter);
	}

	@Override
	public GDHttpResponseBuilder<GDComponentList<GDLevelPreview>> responseBuilderInstance() {
		return response -> {
			if (response.equals("-1"))
				return new GDComponentList<>();
			
			GDComponentList<GDLevelPreview> lvlPrevList = new GDComponentList<>();

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
				
				GDSong song = Long.parseLong(lmap.get(Constants.INDEX_LEVEL_SONG_ID)) <= 0 ?
						Utils.getAudioTrack(Integer.parseInt(lmap.get(Constants.INDEX_LEVEL_AUDIO_TRACK))):
						structuredSongsInfo.get(Long.parseLong(lmap.get(Constants.INDEX_LEVEL_SONG_ID)));
						
				String creatorName = structuredCreatorsInfo.get(Long.parseLong(
						lmap.get(Constants.INDEX_LEVEL_CREATOR_ID)));

				lvlPrevList.add(new GDLevelPreview(Long.parseLong(lmap.get(Constants.INDEX_LEVEL_ID)),
						lmap.get(Constants.INDEX_LEVEL_NAME),
						creatorName != null ? creatorName : "-",
						Long.parseLong(lmap.get(Constants.INDEX_LEVEL_CREATOR_ID)),
						new String(Base64.getUrlDecoder().decode(lmap.get(Constants.INDEX_LEVEL_DESCRIPTION))),
						Constants.VALUE_TO_DIFFICULTY.apply(Integer.parseInt(lmap.get(Constants.INDEX_LEVEL_DIFFICULTY))),
						Constants.VALUE_TO_DEMON_DIFFICULTY.apply(Integer.parseInt(lmap.get(Constants.INDEX_LEVEL_DEMON_DIFFICULTY))),
						Integer.parseInt(lmap.get(Constants.INDEX_LEVEL_STARS)),
						Integer.parseInt(lmap.get(Constants.INDEX_LEVEL_FEATURED_SCORE)),
						lmap.get(Constants.INDEX_LEVEL_IS_EPIC).equals("1"),
						Integer.parseInt(lmap.get(Constants.INDEX_LEVEL_DOWNLOADS)),
						Integer.parseInt(lmap.get(Constants.INDEX_LEVEL_LIKES)),
						GDLevelLength.values()[Integer.parseInt(lmap.get(Constants.INDEX_LEVEL_LENGTH))],
						song,
						Integer.parseInt(lmap.get(Constants.INDEX_LEVEL_COIN_COUNT)),
						lmap.get(Constants.INDEX_LEVEL_COIN_VERIFIED).equals("1"),
						Integer.parseInt(lmap.get(Constants.INDEX_LEVEL_VERSION)),
						Integer.parseInt(lmap.get(Constants.INDEX_LEVEL_GAME_VERSION)),
						Integer.parseInt(lmap.get(Constants.INDEX_LEVEL_OBJECT_COUNT)),
						lmap.get(Constants.INDEX_LEVEL_IS_DEMON).equals("1"),
						lmap.get(Constants.INDEX_LEVEL_IS_AUTO).equals("1"),
						Long.parseLong(lmap.get(Constants.INDEX_LEVEL_ORIGINAL)),
						Integer.parseInt(lmap.get(Constants.INDEX_LEVEL_REQUESTED_STARS))));
			}

			return lvlPrevList;
		};
	}
}
