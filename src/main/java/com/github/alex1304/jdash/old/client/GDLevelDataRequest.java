package com.github.alex1304.jdash.old.client;

import java.util.Map;

import com.github.alex1304.jdash.old.entity.GDLevelData;
import com.github.alex1304.jdash.old.exception.GDClientException;
import com.github.alex1304.jdash.old.util.Indexes;
import com.github.alex1304.jdash.old.util.ParseUtils;
import com.github.alex1304.jdash.old.util.Routes;
import com.github.alex1304.jdash.old.util.Utils;
import com.github.alex1304.jdash.old.util.robtopsweakcrypto.RobTopsWeakCrypto;

class GDLevelDataRequest extends AbstractGDRequest<GDLevelData> {
	
	private final long levelId;
	
	GDLevelDataRequest(AbstractGDClient client, long levelId) {
		super(client);
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
	GDLevelData parseResponse0(String response) throws GDClientException {
		String[] split1 = response.split("#");
		String levelData = split1[0];
		Map<Integer, String> dataMap = ParseUtils.splitToMap(levelData, ":");
		int pass;
		String strPass = Utils.defaultStringIfEmptyOrNull(dataMap.get(Indexes.LEVEL_PASS), "");
		if (strPass.equals("Aw==")) {
			pass = -2; // free to copy
		} else if (strPass.length() < 5) {
			pass = -1; // not copyable
		} else {
			pass = Integer.parseInt(RobTopsWeakCrypto.decodeLevelPass(strPass).substring(1));
		}
		return new GDLevelData(
				Long.parseLong(Utils.defaultStringIfEmptyOrNull(dataMap.get(Indexes.LEVEL_ID), "0")),
				pass,
				Utils.defaultStringIfEmptyOrNull(dataMap.get(Indexes.LEVEL_UPLOADED_TIMESTAMP), "NA"),
				Utils.defaultStringIfEmptyOrNull(dataMap.get(Indexes.LEVEL_LAST_UPDATED_TIMESTAMP), "NA"),
				Utils.b64DecodeToBytes(Utils.defaultStringIfEmptyOrNull(dataMap.get(Indexes.LEVEL_DATA), "")));
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj instanceof GDLevelDataRequest && ((GDLevelDataRequest) obj).levelId == levelId;
	}
	
	@Override
	public int hashCode() {
		return Long.hashCode(levelId);
	}
}
