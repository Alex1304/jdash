package com.alex1304dev.jdash.api.request;

import com.alex1304dev.jdash.api.GDHttpRequest;
import com.alex1304dev.jdash.api.GDHttpResponseFactory;
import com.alex1304dev.jdash.component.GDLevel;
import com.alex1304dev.jdash.util.Constants;

/**
 * HTTP Request to fetch a GD level
 * 
 * @author Alex1304
 *
 */
public class GDLevelHttpRequest extends GDHttpRequest<GDLevel> {

	/**
	 * @param levelID - the ID of the level to fetch
	 */
	public GDLevelHttpRequest(long levelID) {
		super("/downloadGJLevel22.php", Constants.globalHttpRequestParams());
		this.getParams().put("levelID", "" + levelID);
	}

	@Override
	public GDHttpResponseFactory<GDLevel> responseFactoryInstance() {
		return (response, statusCode) -> {
			return null;
		};
	}

}
