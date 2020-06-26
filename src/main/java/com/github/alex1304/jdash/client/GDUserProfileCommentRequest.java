package com.github.alex1304.jdash.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.github.alex1304.jdash.entity.GDComment;
import com.github.alex1304.jdash.exception.GDClientException;
import com.github.alex1304.jdash.util.GDPaginator;
import com.github.alex1304.jdash.util.Indexes;
import com.github.alex1304.jdash.util.ParseUtils;
import com.github.alex1304.jdash.util.Routes;
import com.github.alex1304.jdash.util.Utils;

import reactor.util.function.Tuple3;

class GDUserProfileCommentRequest extends AbstractGDRequest<GDPaginator<GDComment>> {

    private final long accountId;
    private final int page;

    GDUserProfileCommentRequest(AbstractGDClient client, long accountId, int page) {
        super(client);
        this.accountId = accountId;
        this.page = page;
    }

    @Override
    public String getPath() {
        return Routes.GET_PRIVATE_MESSAGES;
    }

    @Override
    void putParams(Map<String, String> params) {
        params.put("accountID", "" + accountId);
        params.put("total", "" + 0);
        params.put("page", "" + page);
    }

    @Override
    GDPaginator<GDComment> parseResponse0(String response) throws GDClientException {
        List<GDComment> commentList = new ArrayList<>();
        String[] split1 = response.split("#");
        String[] comments = split1[0].split("\\|");
        for (String c : comments) {
            Map<Integer, String> data = ParseUtils.splitToMap(c, "~");
            long commentId = Long.parseLong(Utils.defaultStringIfEmptyOrNull(data.get(Indexes.COMMENT_ID), "0"));
            commentList.add(new GDComment(
                    commentId,
                    Long.parseLong(Utils.defaultStringIfEmptyOrNull(data.get(Indexes.COMMENT_SENDER_ID), "0")),
                    "-",
                    Utils.b64Decode(Utils.defaultStringIfEmptyOrNull(data.get(Indexes.COMMENT_BODY), "0")),
                    Integer.parseInt(Utils.defaultStringIfEmptyOrNull(data.get(Indexes.COMMENT_LIKES), "0")),
                    Utils.defaultStringIfEmptyOrNull(data.get(Indexes.COMMENT_UPLOADED_TIMESTAMP), "NA"),
                    Integer.parseInt(Utils.defaultStringIfEmptyOrNull(data.get(Indexes.COMMENT_PERCENTAGE), "0")),
                    Integer.parseInt(Utils.defaultStringIfEmptyOrNull(data.get(Indexes.COMMENT_SENDER_ROLE), "0")),
                    () -> client.fetch(new GDUserProfileDataRequest(client, accountId))));
        }
        Tuple3<Integer, Integer, Integer>  pageInfo = ParseUtils.extractPageInfo(split1[1]);
        return new GDPaginator<>(commentList, page, pageInfo.getT3(), pageInfo.getT1(), newPage ->
                client.fetch(new GDUserProfileCommentRequest(client, accountId, newPage)));
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof GDUserProfileCommentRequest)) {
            return false;
        }
        GDUserProfileCommentRequest r = (GDUserProfileCommentRequest) obj;
        return r.accountId == accountId && r.page == page;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(accountId) ^ page;
    }
}

