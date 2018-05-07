package com.alex1304dev.jdash.api.request;

import java.util.Map;

import com.alex1304dev.jdash.api.GDHttpRequest;
import com.alex1304dev.jdash.api.GDHttpResponse;
import com.alex1304dev.jdash.api.GDHttpResponseFactory;
import com.alex1304dev.jdash.component.GDUser;
import com.alex1304dev.jdash.component.property.GDUserRole;
import com.alex1304dev.jdash.util.Constants;
import com.alex1304dev.jdash.util.Utils;

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
	public GDHttpResponseFactory<GDUser> responseFactoryInstance() {
		return (response, statusCode) -> {
			if (statusCode != 200 || response.equals("-1"))
				return new GDHttpResponse<>(null, statusCode);
			
			try {
				Map<Integer, String> data = Utils.splitToMap(response, ":");
				
				return new GDHttpResponse<GDUser>(new GDUser(
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
				), statusCode);
			} catch (RuntimeException e) {
				return new GDHttpResponse<>(null, statusCode);
			}
		};
	}

}
