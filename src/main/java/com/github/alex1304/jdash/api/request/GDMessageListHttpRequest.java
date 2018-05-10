package com.github.alex1304.jdash.api.request;

import java.util.Base64;
import java.util.Map;

import com.github.alex1304.jdash.api.GDHttpRequest;
import com.github.alex1304.jdash.api.GDHttpResponseBuilder;
import com.github.alex1304.jdash.component.GDComponentList;
import com.github.alex1304.jdash.component.GDMessage;
import com.github.alex1304.jdash.util.Constants;
import com.github.alex1304.jdash.util.Utils;

/**
 * HTTP request to fetch the list of messages in the authenticated user's inbox
 * 
 * @author Alex1304
 */
public class GDMessageListHttpRequest extends GDHttpRequest<GDComponentList<GDMessage>> {

	public GDMessageListHttpRequest(int page) {
		super("/getGJMessages20.php", true);
		this.getParams().put("page", "" + page);
	}

	@Override
	public GDHttpResponseBuilder<GDComponentList<GDMessage>> responseBuilderInstance() {
		return response -> {
			if (response.equals("-1"))
				return new GDComponentList<>();
			
			GDComponentList<GDMessage> messageList = new GDComponentList<>();

			String[] split1 = response.split("#");
			String[] messages = split1[0].split("\\|");

			for (String m : messages) {
				Map<Integer, String> data = Utils.splitToMap(m, ":");
				GDMessage message = new GDMessage(
					Long.parseLong(data.get(Constants.INDEX_MESSAGE_ID)),
					Long.parseLong(data.get(Constants.INDEX_MESSAGE_SENDER_ID)),
					data.get(Constants.INDEX_MESSAGE_SENDER_NAME),
					new String(Base64.getUrlDecoder().decode(data.get(Constants.INDEX_MESSAGE_SUBJECT))),
					"",
					data.get(Constants.INDEX_MESSAGE_IS_READ).equals("1"),
					data.get(Constants.INDEX_MESSAGE_TIMESTAMP)
				);
				messageList.add(message);
			}

			return messageList;
		};
	}
}
