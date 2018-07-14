package com.github.alex1304.jdash.api.request;

import java.util.Map;

import com.github.alex1304.jdash.api.GDHttpRequest;
import com.github.alex1304.jdash.api.GDHttpResponseBuilder;
import com.github.alex1304.jdash.component.GDComponentList;
import com.github.alex1304.jdash.component.GDUserPreview;
import com.github.alex1304.jdash.util.Constants;
import com.github.alex1304.jdash.util.Utils;

/**
 * HTTP request to search for users
 * 
 * @author Alex1304
 *
 */
public class GDUserSearchHttpRequest extends GDHttpRequest<GDComponentList<GDUserPreview>> {

	public GDUserSearchHttpRequest(String keywords, int page) {
		super("/getGJUsers20.php", false);
		this.getParams().put("str", keywords);
		this.getParams().put("page", "" + page);
	}

	@Override
	public GDHttpResponseBuilder<GDComponentList<GDUserPreview>> responseBuilderInstance() {
		return response -> {
			if (response.equals("-1"))
				return new GDComponentList<>();
			
			GDComponentList<GDUserPreview> result = new GDComponentList<>();
			String[] split1 = response.split("#");
			String[] split2 = split1[0].split("\\|");
			
			for (String u : split2) {
				Map<Integer, String> data = Utils.splitToMap(u, ":");
				
				String strPlayerID = data.get(Constants.INDEX_USER_PLAYER_ID);
				String strSecretCoins = data.get(Constants.INDEX_USER_SECRET_COINS);
				String strUserCoins = data.get(Constants.INDEX_USER_USER_COINS);
				String strStars = data.get(Constants.INDEX_USER_STARS);
				String strDemons = data.get(Constants.INDEX_USER_DEMONS);
				String strCreatorPoints = data.get(Constants.INDEX_USER_CREATOR_POINTS);
				String strAccountID = data.get(Constants.INDEX_USER_ACCOUNT_ID);
				
				GDUserPreview up = new GDUserPreview(
					data.get(Constants.INDEX_USER_NAME),
					strPlayerID == null || strPlayerID.isEmpty() ? 0 : Long.parseLong(strPlayerID),
					strSecretCoins == null || strSecretCoins.isEmpty() ? 0 : Integer.parseInt(strSecretCoins),
					strUserCoins == null || strUserCoins.isEmpty() ? 0 : Integer.parseInt(strUserCoins),
					strStars == null || strStars.isEmpty() ? 0 : Integer.parseInt(strStars),
					strDemons == null || strDemons.isEmpty() ? 0 : Integer.parseInt(strDemons),
					strCreatorPoints == null || strCreatorPoints.isEmpty() ? 0 : Integer.parseInt(strCreatorPoints),
					strAccountID == null || strAccountID.isEmpty() ? 0 : Long.parseLong(strAccountID)
				);
				result.add(up);
			}
			
			return result;
		};
	}

}
