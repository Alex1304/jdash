package com.github.alex1304.jdash.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.github.alex1304.jdash.entity.GDMessage;
import com.github.alex1304.jdash.exception.GDClientException;
import com.github.alex1304.jdash.util.GDPaginator;
import com.github.alex1304.jdash.util.Indexes;
import com.github.alex1304.jdash.util.ParseUtils;
import com.github.alex1304.jdash.util.Routes;
import com.github.alex1304.jdash.util.Utils;

public class GDMessageInboxRequest extends AbstractAuthenticatedGDRequest<GDPaginator<GDMessage>> {
	
	private final int page;

	GDMessageInboxRequest(AuthenticatedGDClient client, int page) {
		super(client);
		this.page = page;
	}

	@Override
	public String getPath() {
		return Routes.GET_PRIVATE_MESSAGES;
	}

	@Override
	void putParams(Map<String, String> params) {
		params.put("page", "" + page);
	}

	@Override
	GDPaginator<GDMessage> parseResponse0(String response) throws GDClientException {
		List<GDMessage> messageList = new ArrayList<>();
		String[] split1 = response.split("#");
		String[] messages = split1[0].split("\\|");
		for (String m : messages) {
			Map<Integer, String> data = ParseUtils.splitToMap(m, ":");
			long messageId = Long.parseLong(Utils.defaultStringIfEmptyOrNull(data.get(Indexes.MESSAGE_ID), "0"));
			messageList.add(new GDMessage(
					messageId,
					Long.parseLong(Utils.defaultStringIfEmptyOrNull(data.get(Indexes.MESSAGE_SENDER_ID), "0")),
					Utils.defaultStringIfEmptyOrNull(data.get(Indexes.MESSAGE_SENDER_NAME), "0"),
					Utils.b64Decode(Utils.defaultStringIfEmptyOrNull(data.get(Indexes.MESSAGE_SUBJECT), "0")),
					() -> client.fetch(new GDMessageContentRequest(client, messageId)),
					Utils.defaultStringIfEmptyOrNull(data.get(Indexes.MESSAGE_IS_READ), "0").equals("1"),
					Utils.defaultStringIfEmptyOrNull(data.get(Indexes.MESSAGE_TIMESTAMP), "0")));
		}
		int[] pageInfo = ParseUtils.extractTriplet(split1[1]);
		return new GDPaginator<>(messageList, page, pageInfo[2], pageInfo[0], newPage ->
				client.fetch(new GDMessageInboxRequest(client, newPage)));
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof GDMessageInboxRequest)) {
			return false;
		}
		GDMessageInboxRequest r = (GDMessageInboxRequest) obj;
		return r.client.getAccountID() == client.getAccountID() && r.page == page;
	}
	
	@Override
	public int hashCode() {
		return Long.hashCode(client.getAccountID()) ^ page;
	}
}
