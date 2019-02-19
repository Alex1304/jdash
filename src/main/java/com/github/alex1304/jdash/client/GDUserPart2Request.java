package com.github.alex1304.jdash.client;

import java.util.Map;

import com.github.alex1304.jdash.entity.GDList;
import com.github.alex1304.jdash.entity.GDUserPart2;
import com.github.alex1304.jdash.util.Constants;
import com.github.alex1304.jdash.util.Routes;
import com.github.alex1304.jdash.util.Utils;

class GDUserPart2Request extends AbstractGDRequest<GDList<GDUserPart2>> {
	
	private final String query;
	private final int page;

	GDUserPart2Request(String query, int page) {
		this.query = query;
		this.page = page;
	}

	@Override
	public String getPath() {
		return Routes.USER_SEARCH;
	}

	@Override
	void putParams(Map<String, String> params) {
		params.put("str", query);
		params.put("page", "" + page);
	}

	@Override
	GDList<GDUserPart2> parseResponse0(String response) throws GDClientException {
		GDList<GDUserPart2> result = new GDList<>();
		String[] split1 = response.split("#");
		String[] split2 = split1[0].split("\\|");
		for (String u : split2) {
			Map<Integer, String> data = Utils.splitToMap(u, ":");
			String strPlayerID = Utils.defaultStringIfEmptyOrNull(data.get(Constants.INDEX_USER_PLAYER_ID), "0");
			String strAccountID = Utils.defaultStringIfEmptyOrNull(data.get(Constants.INDEX_USER_ACCOUNT_ID), "0");
			String strStars = Utils.defaultStringIfEmptyOrNull(data.get(Constants.INDEX_USER_STARS), "0");
			String strDemons = Utils.defaultStringIfEmptyOrNull(data.get(Constants.INDEX_USER_DEMONS), "0");
			String strSecretCoins = Utils.defaultStringIfEmptyOrNull(data.get(Constants.INDEX_USER_SECRET_COINS), "0");
			String strUserCoins = Utils.defaultStringIfEmptyOrNull(data.get(Constants.INDEX_USER_USER_COINS), "0");
			String strCreatorPoints = Utils.defaultStringIfEmptyOrNull(data.get(Constants.INDEX_USER_CREATOR_POINTS), "0");
			String strColor1 = Utils.defaultStringIfEmptyOrNull(data.get(Constants.INDEX_USER_COLOR_1), "0");
			String strColor2 = Utils.defaultStringIfEmptyOrNull(data.get(Constants.INDEX_USER_COLOR_2), "0");
			boolean hasGlowOutline = !Utils.defaultStringIfEmptyOrNull(data.get(Constants.INDEX_USER_GLOW_OUTLINE), "0").equals("0");
			String strMainIconId = Utils.defaultStringIfEmptyOrNull(data.get(Constants.INDEX_USER_ICON), "0");
			String strName = Utils.defaultStringIfEmptyOrNull(data.get(Constants.INDEX_USER_NAME), "-");
			result.add(new GDUserPart2(
					Long.parseLong(strPlayerID),
					Integer.parseInt(strSecretCoins),
					Integer.parseInt(strUserCoins),
					Integer.parseInt(strColor1),
					Integer.parseInt(strColor2),
					Long.parseLong(strAccountID),
					Integer.parseInt(strStars),
					Integer.parseInt(strCreatorPoints),
					Integer.parseInt(strDemons),
					strName,
					hasGlowOutline,
					Integer.parseInt(strMainIconId)));
		}
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof GDUserPart2Request)) {
			return false;
		}
		GDUserPart2Request o = (GDUserPart2Request) obj;
		return o.page == page && o.query.equals(query);
	}
	
	@Override
	public int hashCode() {
		return page ^ query.hashCode();
	}
}
