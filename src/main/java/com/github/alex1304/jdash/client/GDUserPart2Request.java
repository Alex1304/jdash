package com.github.alex1304.jdash.client;

import java.util.ArrayList;
import java.util.Map;

import com.github.alex1304.jdash.entity.GDUserPart2;
import com.github.alex1304.jdash.entity.IconType;
import com.github.alex1304.jdash.exception.GDClientException;
import com.github.alex1304.jdash.util.GDPaginator;
import com.github.alex1304.jdash.util.Indexes;
import com.github.alex1304.jdash.util.ParseUtils;
import com.github.alex1304.jdash.util.Routes;
import com.github.alex1304.jdash.util.Utils;

class GDUserPart2Request extends AbstractGDRequest<GDPaginator<GDUserPart2>> {

	private final String query;
	private final int page;

	GDUserPart2Request(GeometryDashClient client, String query, int page) {
		super(client);
		this.query = query;
		this.page = page;
	}

	@Override
	public String getPath() {
		return Routes.USER_SEARCH;
	}

	@Override
	void putParams(Map<String, String> params) {
		params.put("str", Utils.urlEncode(query));
		params.put("page", "" + page);
	}

	@Override
	GDPaginator<GDUserPart2> parseResponse0(String response) throws GDClientException {
		ArrayList<GDUserPart2> list = new ArrayList<>();
		String[] split1 = response.split("#");
		String[] split2 = split1[0].split("\\|");
		for (String u : split2) {
			Map<Integer, String> data = ParseUtils.splitToMap(u, ":");
			String strPlayerID = Utils.defaultStringIfEmptyOrNull(data.get(Indexes.USER_PLAYER_ID), "0");
			String strAccountID = Utils.defaultStringIfEmptyOrNull(data.get(Indexes.USER_ACCOUNT_ID), "0");
			String strStars = Utils.defaultStringIfEmptyOrNull(data.get(Indexes.USER_STARS), "0");
			String strDemons = Utils.defaultStringIfEmptyOrNull(data.get(Indexes.USER_DEMONS), "0");
			String strSecretCoins = Utils.defaultStringIfEmptyOrNull(data.get(Indexes.USER_SECRET_COINS), "0");
			String strUserCoins = Utils.defaultStringIfEmptyOrNull(data.get(Indexes.USER_USER_COINS), "0");
			String strCreatorPoints = Utils.defaultStringIfEmptyOrNull(data.get(Indexes.USER_CREATOR_POINTS), "0");
			String strColor1 = Utils.defaultStringIfEmptyOrNull(data.get(Indexes.USER_COLOR_1), "0");
			String strColor2 = Utils.defaultStringIfEmptyOrNull(data.get(Indexes.USER_COLOR_2), "0");
			String strHasGlowOutline = Utils.defaultStringIfEmptyOrNull(data.get(Indexes.USER_GLOW_OUTLINE), "0");
			String strMainIconId = Utils.defaultStringIfEmptyOrNull(data.get(Indexes.USER_ICON), "0");
			String strName = Utils.defaultStringIfEmptyOrNull(data.get(Indexes.USER_NAME), "-");
			String strIconType = Utils.defaultStringIfEmptyOrNull(data.get(Indexes.USER_ICON_TYPE), "0");
			list.add(new GDUserPart2(
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
					!strHasGlowOutline.equals("0"),
					Integer.parseInt(strMainIconId),
					IconType.values()[Integer.parseInt(strIconType)]));
		}
		int[] pageInfo = ParseUtils.extractTriplet(split1[1]);
		return new GDPaginator<>(list, page, pageInfo[2], pageInfo[0], newPage ->
				client.fetch(new GDUserPart2Request(client, query, newPage)));
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
