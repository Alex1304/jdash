package com.github.alex1304.jdash.client;

import java.util.Map;

import com.github.alex1304.jdash.entity.GDLevelPart1;
import com.github.alex1304.jdash.entity.GDSong;
import com.github.alex1304.jdash.entity.Length;
import com.github.alex1304.jdash.exception.GDClientException;
import com.github.alex1304.jdash.util.Indexes;
import com.github.alex1304.jdash.util.Routes;
import com.github.alex1304.jdash.util.Utils;
import com.github.alex1304.jdash.util.robtopsweakcrypto.RobTopsWeakCrypto;

class GDLevelPart1Request extends AbstractGDRequest<GDLevelPart1> {
	
	private final long levelId;
	
	public GDLevelPart1Request(long levelId) {
		this.levelId = levelId;
	} 

	@Override
	public String getPath() {
		return Routes.DOWNLOAD_LEVEL;
	}

	@Override
	void putParams(Map<String, String> params) {
		params.put("levelID", "" + levelId);
	}

	@Override
	GDLevelPart1 parseResponse0(String response) throws GDClientException {
		String[] split1 = response.split("#");
		String levelData = split1[0];
		String creatorName = "-";
		if (split1.length > 3 && split1[3].split(":").length >= 2) {
			creatorName = split1[3].split(":")[1];
		}
		Map<Integer, String> dataMap = Utils.splitToMap(levelData, ":");
		int pass;
		String strPass = Utils.defaultStringIfEmptyOrNull(dataMap.get(Indexes.LEVEL_PASS), "");
		if (strPass.equals("Aw==")) {
			pass = -2; // free to copy
		} else if (strPass.length() < 5) {
			pass = -1; // not copyable
		} else {
			pass = Integer.parseInt(RobTopsWeakCrypto.decodeLevelPass(strPass).substring(1));
		}
		long songID = Long.parseLong(dataMap.get(Indexes.LEVEL_SONG_ID));
		GDSong song = songID > 0 ? GDSong.unknownSong(songID) : GDSong.unknownSong(0);
		return new GDLevelPart1(
				Long.parseLong(Utils.defaultStringIfEmptyOrNull(dataMap.get(Indexes.LEVEL_ID), "0")),
				Utils.defaultStringIfEmptyOrNull(dataMap.get(Indexes.LEVEL_NAME), "-"),
				creatorName,
				Long.parseLong(Utils.defaultStringIfEmptyOrNull(dataMap.get(Indexes.LEVEL_CREATOR_ID), "0")),
				Utils.b64Decode(Utils.defaultStringIfEmptyOrNull(dataMap.get(Indexes.LEVEL_DESCRIPTION), "")),
				Utils.valueToDifficulty(Integer.parseInt(Utils.defaultStringIfEmptyOrNull(dataMap.get(Indexes.LEVEL_DIFFICULTY), "0"))),
				Utils.valueToDemonDifficulty(Integer.parseInt(Utils.defaultStringIfEmptyOrNull(dataMap.get(Indexes.LEVEL_DEMON_DIFFICULTY), "0"))),
				Integer.parseInt(Utils.defaultStringIfEmptyOrNull(dataMap.get(Indexes.LEVEL_STARS), "0")),
				Integer.parseInt(Utils.defaultStringIfEmptyOrNull(dataMap.get(Indexes.LEVEL_FEATURED_SCORE), "0")),
				!Utils.defaultStringIfEmptyOrNull(dataMap.get(Indexes.LEVEL_IS_EPIC), "0").equals("0"),
				Integer.parseInt(Utils.defaultStringIfEmptyOrNull(dataMap.get(Indexes.LEVEL_DOWNLOADS), "0")),
				Integer.parseInt(Utils.defaultStringIfEmptyOrNull(dataMap.get(Indexes.LEVEL_LIKES), "0")),
				Length.values()[Integer.parseInt(Utils.defaultStringIfEmptyOrNull(dataMap.get(Indexes.LEVEL_LENGTH), "0"))],
				song,
				Integer.parseInt(Utils.defaultStringIfEmptyOrNull(dataMap.get(Indexes.LEVEL_COIN_COUNT), "0")),
				Utils.defaultStringIfEmptyOrNull(dataMap.get(Indexes.LEVEL_COIN_VERIFIED), "0").equals("1"),
				Integer.parseInt(Utils.defaultStringIfEmptyOrNull(dataMap.get(Indexes.LEVEL_VERSION), "0")),
				Integer.parseInt(Utils.defaultStringIfEmptyOrNull(dataMap.get(Indexes.LEVEL_GAME_VERSION), "0")),
				Integer.parseInt(Utils.defaultStringIfEmptyOrNull(dataMap.get(Indexes.LEVEL_OBJECT_COUNT), "0")),
				Utils.defaultStringIfEmptyOrNull(dataMap.get(Indexes.LEVEL_IS_DEMON), "0").equals("1"),
				Utils.defaultStringIfEmptyOrNull(dataMap.get(Indexes.LEVEL_IS_AUTO), "0").equals("1"),
				Long.parseLong(Utils.defaultStringIfEmptyOrNull(dataMap.get(Indexes.LEVEL_ORIGINAL), "0")),
				Integer.parseInt(Utils.defaultStringIfEmptyOrNull(dataMap.get(Indexes.LEVEL_REQUESTED_STARS), "0")),
				pass,
				Utils.defaultStringIfEmptyOrNull(dataMap.get(Indexes.LEVEL_UPLOADED_TIMESTAMP), "NA"),
				Utils.defaultStringIfEmptyOrNull(dataMap.get(Indexes.LEVEL_LAST_UPDATED_TIMESTAMP), "NA"),
				Utils.b64DecodeToBytes(Utils.defaultStringIfEmptyOrNull(dataMap.get(Indexes.LEVEL_DATA), "")));
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj instanceof GDLevelPart1Request && ((GDLevelPart1Request) obj).levelId == levelId;
	}
}
