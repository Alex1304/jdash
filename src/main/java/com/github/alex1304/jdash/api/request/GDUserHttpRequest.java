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
			
			return new GDUser(
				data.get(Constants.INDEX_USER_NAME),
				Long.parseLong(data.get(Constants.INDEX_USER_PLAYER_ID)),
				Integer.parseInt(data.get(Constants.INDEX_USER_SECRET_COINS)),
				Integer.parseInt(data.get(Constants.INDEX_USER_USER_COINS)),
				Integer.parseInt(data.get(Constants.INDEX_USER_STARS)),
				Integer.parseInt(data.get(Constants.INDEX_USER_DIAMONDS)),
				Integer.parseInt(data.get(Constants.INDEX_USER_DEMONS)),
				Integer.parseInt(data.get(Constants.INDEX_USER_CREATOR_POINTS)),
				data.get(Constants.INDEX_USER_YOUTUBE),
				Long.parseLong(data.get(Constants.INDEX_USER_GLOBAL_RANK)),
				Long.parseLong(data.get(Constants.INDEX_USER_ACCOUNT_ID)),
				GDUserRole.values()[Integer.parseInt(data.get(Constants.INDEX_USER_ROLE))],
				data.get(Constants.INDEX_USER_TWITTER),
				data.get(Constants.INDEX_USER_TWITCH)
			);
		};
	}

}
