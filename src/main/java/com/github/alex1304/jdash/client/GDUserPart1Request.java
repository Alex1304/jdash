package com.github.alex1304.jdash.client;

import java.util.Map;

import com.github.alex1304.jdash.entity.GDUserPart1;
import com.github.alex1304.jdash.entity.PrivacySetting;
import com.github.alex1304.jdash.entity.Role;
import com.github.alex1304.jdash.exception.GDClientException;
import com.github.alex1304.jdash.util.Indexes;
import com.github.alex1304.jdash.util.ParseUtils;
import com.github.alex1304.jdash.util.Routes;
import com.github.alex1304.jdash.util.Utils;

class GDUserPart1Request extends AbstractGDRequest<GDUserPart1> {
	
	private final long targetAccountID;

	GDUserPart1Request(AbstractGDClient client, long targetAccountID) {
		super(client);
		this.targetAccountID = targetAccountID;
	}

	@Override
	public String getPath() {
		return Routes.GET_USER_INFO;
	}

	@Override
	void putParams(Map<String, String> params) {
		params.put("targetAccountID", "" + targetAccountID);
	}

	@Override
	GDUserPart1 parseResponse0(String response) throws GDClientException {
		Map<Integer, String> data = ParseUtils.splitToMap(response, ":");
		String strPlayerID = Utils.defaultStringIfEmptyOrNull(data.get(Indexes.USER_PLAYER_ID), "0");
		String strAccountID = Utils.defaultStringIfEmptyOrNull(data.get(Indexes.USER_ACCOUNT_ID), "0");
		String strStars = Utils.defaultStringIfEmptyOrNull(data.get(Indexes.USER_STARS), "0");
		String strDemons = Utils.defaultStringIfEmptyOrNull(data.get(Indexes.USER_DEMONS), "0");
		String strDiamonds = Utils.defaultStringIfEmptyOrNull(data.get(Indexes.USER_DIAMONDS), "0");
		String strSecretCoins = Utils.defaultStringIfEmptyOrNull(data.get(Indexes.USER_SECRET_COINS), "0");
		String strUserCoins = Utils.defaultStringIfEmptyOrNull(data.get(Indexes.USER_USER_COINS), "0");
		String strCreatorPoints = Utils.defaultStringIfEmptyOrNull(data.get(Indexes.USER_CREATOR_POINTS), "0");
		String strGlobalRank = Utils.defaultStringIfEmptyOrNull(data.get(Indexes.USER_GLOBAL_RANK), "0");
		String strCube = Utils.defaultStringIfEmptyOrNull(data.get(Indexes.USER_ICON_CUBE), "0");
		String strShip = Utils.defaultStringIfEmptyOrNull(data.get(Indexes.USER_ICON_SHIP), "0");
		String strUfo = Utils.defaultStringIfEmptyOrNull(data.get(Indexes.USER_ICON_UFO), "0");
		String strBall = Utils.defaultStringIfEmptyOrNull(data.get(Indexes.USER_ICON_BALL), "0");
		String strWave = Utils.defaultStringIfEmptyOrNull(data.get(Indexes.USER_ICON_WAVE), "0");
		String strRobot = Utils.defaultStringIfEmptyOrNull(data.get(Indexes.USER_ICON_ROBOT), "0");
		String strSpider = Utils.defaultStringIfEmptyOrNull(data.get(Indexes.USER_ICON_SPIDER), "0");
		String strTrail = Utils.defaultStringIfEmptyOrNull(data.get(Indexes.USER_TRAIL), "0");
		String strDeathEffect = Utils.defaultStringIfEmptyOrNull(data.get(Indexes.USER_DEATH_EFFECT), "0");
		String strColor1 = Utils.defaultStringIfEmptyOrNull(data.get(Indexes.USER_COLOR_1), "0");
		String strColor2 = Utils.defaultStringIfEmptyOrNull(data.get(Indexes.USER_COLOR_2), "0");
		String strRole = Utils.defaultStringIfEmptyOrNull(data.get(Indexes.USER_ROLE), "0");
		String strYoutube = Utils.defaultStringIfEmptyOrNull(data.get(Indexes.USER_YOUTUBE), "");
		String strTwitter = Utils.defaultStringIfEmptyOrNull(data.get(Indexes.USER_TWITTER), "");
		String strTwitch = Utils.defaultStringIfEmptyOrNull(data.get(Indexes.USER_TWITCH), "0");
		String strFriendRequest = Utils.defaultStringIfEmptyOrNull(data.get(Indexes.USER_FRIEND_REQUEST_POLICY), "0");
		String strPrivateMessage = Utils.defaultStringIfEmptyOrNull(data.get(Indexes.USER_PRIVATE_MESSAGE_POLICY), "0");
		String strCommentHistory = Utils.defaultStringIfEmptyOrNull(data.get(Indexes.USER_COMMENT_HISTORY_POLICY), "0");
		return new GDUserPart1(
				Long.parseLong(strPlayerID),
				Integer.parseInt(strSecretCoins),
				Integer.parseInt(strUserCoins),
				Integer.parseInt(strColor1),
				Integer.parseInt(strColor2),
				Long.parseLong(strAccountID),
				Integer.parseInt(strStars),
				Integer.parseInt(strCreatorPoints),
				Integer.parseInt(strDemons),
				Integer.parseInt(strDiamonds),
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
				strYoutube,
				strTwitter,
				strTwitch,
				Role.values()[Integer.parseInt(strRole)],
				strFriendRequest.equals("0"),
				PrivacySetting.values()[Integer.parseInt(strPrivateMessage)],
				PrivacySetting.values()[Integer.parseInt(strCommentHistory)]);
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj instanceof GDUserPart1Request && ((GDUserPart1Request) obj).targetAccountID == targetAccountID;
	}
	
	@Override
	public int hashCode() {
		return Long.hashCode(targetAccountID);
	}
}
