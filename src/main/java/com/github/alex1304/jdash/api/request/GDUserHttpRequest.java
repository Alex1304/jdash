package com.github.alex1304.jdash.api.request;

import java.util.Map;

import com.github.alex1304.jdash.api.GDHttpRequest;
import com.github.alex1304.jdash.api.GDHttpResponseBuilder;
import com.github.alex1304.jdash.component.GDUser;
import com.github.alex1304.jdash.component.property.GDUserRole;
import com.github.alex1304.jdash.util.Constants;
import com.github.alex1304.jdash.util.Utils;

/**
 * HTTP request to fetch a GD user.
 * 
 * @author Alex1304
 */
public class GDUserHttpRequest extends GDHttpRequest<GDUser> {

	
	public GDUserHttpRequest(long targetAccountID) {
		super("/getGJUserInfo20.php", false);
		this.getParams().put("targetAccountID", "" + targetAccountID);
	}

	@Override
	public GDHttpResponseBuilder<GDUser> responseBuilderInstance() {
		return response -> {
			if (response.equals("-1"))
				return null;
			
			Map<Integer, String> data = Utils.splitToMap(response, ":");
			

			
			String strPlayerID = data.get(Constants.INDEX_USER_PLAYER_ID);
			String strSecretCoins = data.get(Constants.INDEX_USER_SECRET_COINS);
			String strUserCoins = data.get(Constants.INDEX_USER_USER_COINS);
			String strStars = data.get(Constants.INDEX_USER_STARS);
			String strDiamonds = data.get(Constants.INDEX_USER_DIAMONDS);
			String strDemons = data.get(Constants.INDEX_USER_DEMONS);
			String strCreatorPoints = data.get(Constants.INDEX_USER_CREATOR_POINTS);
			String strAccountID = data.get(Constants.INDEX_USER_ACCOUNT_ID);
			String strGlobalRank = data.get(Constants.INDEX_USER_GLOBAL_RANK);
			String strRole = data.get(Constants.INDEX_USER_ROLE);
			
			return new GDUser(
				data.get(Constants.INDEX_USER_NAME),
				strPlayerID == null || strPlayerID.isEmpty() ? 0 : Long.parseLong(strPlayerID),
				strSecretCoins == null || strSecretCoins.isEmpty() ? 0 : Integer.parseInt(strSecretCoins),
				strUserCoins == null || strUserCoins.isEmpty() ? 0 : Integer.parseInt(strUserCoins),
				strStars == null || strStars.isEmpty() ? 0 : Integer.parseInt(strStars),
				strDiamonds == null || strDiamonds.isEmpty() ? 0 : Integer.parseInt(strDiamonds),
				strDemons == null || strDemons.isEmpty() ? 0 : Integer.parseInt(strDemons),
				strCreatorPoints == null || strCreatorPoints.isEmpty() ? 0 : Integer.parseInt(strCreatorPoints),
				data.get(Constants.INDEX_USER_YOUTUBE),
				strGlobalRank == null || strGlobalRank.isEmpty() ? 0 : Long.parseLong(strGlobalRank),
				strAccountID == null || strAccountID.isEmpty() ? 0 : Long.parseLong(strAccountID),
				strRole == null || strRole.isEmpty() ? GDUserRole.USER : GDUserRole.values()[Integer.parseInt(data.get(Constants.INDEX_USER_ROLE))],
				data.get(Constants.INDEX_USER_TWITTER),
				data.get(Constants.INDEX_USER_TWITCH)
			);
		};
	}

}
