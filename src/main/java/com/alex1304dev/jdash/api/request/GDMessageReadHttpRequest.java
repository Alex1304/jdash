package com.alex1304dev.jdash.api.request;

import java.util.Base64;
import java.util.Map;

import com.alex1304dev.jdash.api.GDHttpRequest;
import com.alex1304dev.jdash.api.GDHttpResponseBuilder;
import com.alex1304dev.jdash.component.GDMessage;
import com.alex1304dev.jdash.util.Constants;
import com.alex1304dev.jdash.util.Utils;
import com.alex1304dev.jdash.util.robtopsweakcrypto.RobTopsWeakCrypto;

/**
 * HTTP request to fetch private message contents
 * 
 * @author Alex1304
 */
public class GDMessageReadHttpRequest extends GDHttpRequest<GDMessage> {

	public GDMessageReadHttpRequest(long messageID) {
		super("/downloadGJMessage20.php", true);
		this.getParams().put("messageID", "" + messageID);
	}
	
	@Override
	public GDHttpResponseBuilder<GDMessage> responseBuilderInstance() {
		return response -> {
			if (response.equals("-1"))
				return null;
			
			Map<Integer, String> data = Utils.splitToMap(response, ":");
			
			return new GDMessage(Long.parseLong(data.get(Constants.INDEX_MESSAGE_ID)),
				Long.parseLong(data.get(Constants.INDEX_MESSAGE_SENDER_ID)),
				data.get(Constants.INDEX_MESSAGE_SENDER_NAME),
				new String(Base64.getUrlDecoder().decode(data.get(Constants.INDEX_MESSAGE_SUBJECT))),
				RobTopsWeakCrypto.decodeGDMessageBody(data.get(Constants.INDEX_MESSAGE_BODY)),
				data.get(Constants.INDEX_MESSAGE_IS_READ).equals("1"),
				data.get(Constants.INDEX_MESSAGE_TIMESTAMP)
			);
		};
	}

}
