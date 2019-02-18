package com.github.alex1304.jdash.request;

import java.util.HashMap;
import java.util.Map;

import com.github.alex1304.jdash.client.GDClientException;
import com.github.alex1304.jdash.entity.GDUser;
import com.github.alex1304.jdash.entity.Role;
import com.github.alex1304.jdash.util.Constants;
import com.github.alex1304.jdash.util.Routes;
import com.github.alex1304.jdash.util.Utils;

public class GDUserRequest implements GDRequest<GDUser> {
	
	private final long targetAccountID;

	public GDUserRequest(long targetAccountID) {
		this.targetAccountID = targetAccountID;
	}

	@Override
	public String getPath() {
		return Routes.GET_USER_INFO;
	}

	@Override
	public Map<String, String> getParams() {
		Map<String, String> map = new HashMap<>();
		map.put("targetAccountID", "" + targetAccountID);
		return map;
	}

	@Override
	public GDUser parseResponse(String response) throws GDClientException {
		if (response.equals("-1"))
			return null;
		
		Map<Integer, String> data = Utils.splitToMap(response, ":");
		
		String strPlayerID = data.getOrDefault(Constants.INDEX_USER_PLAYER_ID, "0");
		String strAccountID = data.getOrDefault(Constants.INDEX_USER_ACCOUNT_ID, "0");
		String strStars = data.getOrDefault(Constants.INDEX_USER_STARS, "0");
		String strDemons = data.getOrDefault(Constants.INDEX_USER_DEMONS, "0");
		String strDiamonds = data.getOrDefault(Constants.INDEX_USER_DIAMONDS, "0");
		String strSecretCoins = data.getOrDefault(Constants.INDEX_USER_SECRET_COINS, "0");
		String strUserCoins = data.getOrDefault(Constants.INDEX_USER_USER_COINS, "0");
		String strCreatorPoints = data.getOrDefault(Constants.INDEX_USER_CREATOR_POINTS, "0");
		String strGlobalRank = data.getOrDefault(Constants.INDEX_USER_GLOBAL_RANK, "0");
		String strCube = data.getOrDefault(Constants.INDEX_USER_ICON_CUBE, "0");
		String strShip = data.getOrDefault(Constants.INDEX_USER_ICON_SHIP, "0");
		String strUfo = data.getOrDefault(Constants.INDEX_USER_ICON_UFO, "0");
		String strBall = data.getOrDefault(Constants.INDEX_USER_ICON_BALL, "0");
		String strWave = data.getOrDefault(Constants.INDEX_USER_ICON_WAVE, "0");
		String strRobot = data.getOrDefault(Constants.INDEX_USER_ICON_ROBOT, "0");
		String strSpider = data.getOrDefault(Constants.INDEX_USER_ICON_SPIDER, "0");
		String strTrail = data.getOrDefault(Constants.INDEX_USER_GLOW, "0");
		String strDeathEffect = data.getOrDefault(Constants.INDEX_USER_DEATH_EFFECT, "0");
		String strColor1 = data.getOrDefault(Constants.INDEX_USER_COLOR_1, "0");
		String strColor2 = data.getOrDefault(Constants.INDEX_USER_COLOR_2, "0");
		String strRole = data.getOrDefault(Constants.INDEX_USER_ROLE, "0");
		String strYoutube = data.getOrDefault(Constants.INDEX_USER_YOUTUBE, "");
		String strTwitter = data.getOrDefault(Constants.INDEX_USER_TWITTER, "");
		String strTwitch = data.getOrDefault(Constants.INDEX_USER_TWITCH, "");
		
		return new GDUser(
				Long.parseLong(strAccountID),
				Long.parseLong(strPlayerID),
				null,
				Integer.parseInt(strStars),
				Integer.parseInt(strDemons),
				Integer.parseInt(strDiamonds),
				Integer.parseInt(strSecretCoins),
				Integer.parseInt(strUserCoins),
				Integer.parseInt(strCreatorPoints),
				Integer.parseInt(strGlobalRank),
				Integer.parseInt(strCube),
				Integer.parseInt(strShip),
				Integer.parseInt(strUfo),
				Integer.parseInt(strBall),
				Integer.parseInt(strWave),
				Integer.parseInt(strRobot),
				Integer.parseInt(strSpider),
				Integer.parseInt(strTrail),
				Integer.parseInt(strDeathEffect),
				false,
				Integer.parseInt(strColor1),
				Integer.parseInt(strColor2),
				0,
				strYoutube,
				strTwitter,
				strTwitch,
				Role.values()[Integer.parseInt(strRole)]);
	}

}
