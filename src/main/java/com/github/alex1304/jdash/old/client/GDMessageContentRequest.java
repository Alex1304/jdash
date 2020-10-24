package com.github.alex1304.jdash.old.client;

import java.util.Map;

import com.github.alex1304.jdash.old.exception.GDClientException;
import com.github.alex1304.jdash.old.util.Indexes;
import com.github.alex1304.jdash.old.util.ParseUtils;
import com.github.alex1304.jdash.old.util.Routes;
import com.github.alex1304.jdash.old.util.Utils;
import com.github.alex1304.jdash.old.util.robtopsweakcrypto.RobTopsWeakCrypto;

class GDMessageContentRequest extends AbstractAuthenticatedGDRequest<String> {
	
	private final long messageId;
	
	GDMessageContentRequest(AuthenticatedGDClient client, long messageId) {
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
	String parseResponse0(String response) throws GDClientException {
		Map<Integer, String> data = ParseUtils.splitToMap(response, ":");
		return RobTopsWeakCrypto.decodeGDMessageBody(Utils.defaultStringIfEmptyOrNull(data.get(Indexes.MESSAGE_BODY), "0"));
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj instanceof GDMessageContentRequest && ((GDMessageContentRequest) obj).messageId == messageId;
	}
	
	@Override
	public int hashCode() {
		return Long.hashCode(messageId);
	}
}
