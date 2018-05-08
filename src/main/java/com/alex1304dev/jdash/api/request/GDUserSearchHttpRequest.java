package com.alex1304dev.jdash.api.request;

import java.util.Map;

import com.alex1304dev.jdash.api.GDHttpRequest;
import com.alex1304dev.jdash.api.GDHttpResponseBuilder;
import com.alex1304dev.jdash.component.GDComponentList;
import com.alex1304dev.jdash.component.GDUserPreview;
import com.alex1304dev.jdash.util.Constants;
import com.alex1304dev.jdash.util.Utils;

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
				GDUserPreview up = new GDUserPreview(
					data.get(Constants.INDEX_USER_NAME),
					Long.parseLong(data.get(Constants.INDEX_USER_PLAYER_ID)),
					Integer.parseInt(data.get(Constants.INDEX_USER_SECRET_COINS)),
					Integer.parseInt(data.get(Constants.INDEX_USER_USER_COINS)),
					Integer.parseInt(data.get(Constants.INDEX_USER_STARS)),
					Integer.parseInt(data.get(Constants.INDEX_USER_DEMONS)),
					Integer.parseInt(data.get(Constants.INDEX_USER_CREATOR_POINTS)),
					Long.parseLong(data.get(Constants.INDEX_USER_ACCOUNT_ID))
				);
				result.add(up);
			}
			
			return result;
		};
	}

}
