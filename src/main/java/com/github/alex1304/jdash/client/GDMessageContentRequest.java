package com.github.alex1304.jdash.client;

import java.util.Map;

import com.github.alex1304.jdash.entity.GDMessage.Content;
import com.github.alex1304.jdash.exception.GDClientException;
import com.github.alex1304.jdash.util.Indexes;
import com.github.alex1304.jdash.util.ParseUtils;
import com.github.alex1304.jdash.util.Routes;
import com.github.alex1304.jdash.util.Utils;
import com.github.alex1304.jdash.util.robtopsweakcrypto.RobTopsWeakCrypto;

public class GDMessageContentRequest extends AbstractGDRequest<Content> {
	
	private final long messageId;
	
	GDMessageContentRequest(GeometryDashClient client, long messageId) {
		super(client);
		this.messageId = messageId;
	}

	@Override
	public String getPath() {
		return Routes.READ_PRIVATE_MESSAGE;
	}

	@Override
	void putParams(Map<String, String> params) {
		params.put("messageID", "" + messageId);
	}

	@Override
	Content parseResponse0(String response) throws GDClientException {
		Map<Integer, String> data = ParseUtils.splitToMap(response, ":");
		return new Content(messageId, RobTopsWeakCrypto.decodeGDMessageBody(Utils.defaultStringIfEmptyOrNull(data.get(Indexes.MESSAGE_BODY), "0")));
	}

}
