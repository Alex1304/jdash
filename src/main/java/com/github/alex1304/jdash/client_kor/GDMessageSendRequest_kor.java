package com.github.alex1304.jdash.client;

import java.util.Map;
import java.util.Objects;

import com.github.alex1304.jdash.exception.GDClientException;
import com.github.alex1304.jdash.util.Routes;
import com.github.alex1304.jdash.util.Utils;
import com.github.alex1304.jdash.util.robtopsweakcrypto.RobTopsWeakCrypto;

class GDMessageSendRequest extends AbstractAuthenticatedGDRequest<Void> {
	
	private final long recipientID;
	private final String subject;
	private final String body;
	
	GDMessageSendRequest(AuthenticatedGDClient client, long recipientID, String subject, String body) {
		super(client);
		this.recipientID = recipientID;
		this.subject = Objects.requireNonNull(subject);
		this.body = Objects.requireNonNull(body);
	}

	@Override
	public String getPath() {
		return Routes.SEND_PRIVATE_MESSAGE;
	}

	@Override
	void putParams(Map<String, String> params) {
		params.put("toAccountID", "" + recipientID);
		params.put("subject", Utils.b64Encode(subject));
		params.put("body", RobTopsWeakCrypto.encodeGDMessageBody(body));
	}

	@Override
	Void parseResponse0(String response) throws GDClientException {
		if (!response.equals("1")) {
			throw new IllegalArgumentException("Unknown response: " + response);
		}
		return null;
	}
	
	@Override
	public boolean cacheable() {
		return false;
	}
}
