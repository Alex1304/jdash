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

class GDLevelCommentRequest extends AbstractGDRequest<GDPaginator<GDComment>> {

    private final long levelId;
    private final boolean isRecent;
    private final int page;

    GDLevelCommentRequest(AbstractGDClient client, long levelId, boolean isRecent, int page) {
        super(client);
        this.levelId = levelId;
        this.isRecent = isRecent;
        this.page = page;
    }

    @Override
    public String getPath() {
        return Routes.GET_LEVEL_COMMENT;
    }

    @Override
    void putParams(Map<String, String> params) {
        params.put("levelID", "" + levelId);
        params.put("total", "" + 0);
        params.put("page", "" + page);
        if(isRecent){
            params.put("mode", "0");
        } else {
            params.put("mode", "1");
        }
    }

    @Override
    GDPaginator<GDComment> parseResponse0(String response) throws GDClientException {
        List<GDComment> commentList = new ArrayList<>();
        String[] split1 = response.split("#");
        String[] comments = split1[0].split("\\|");
        for (String c : comments) {
            String[] split2 = c.split(":");
            Map<Integer, String> data = ParseUtils.splitToMap(split2[0], "~");
            Map<Integer, String> u = ParseUtils.splitToMap(split2[1], "~");
            long commentId = Long.parseLong(Utils.defaultStringIfEmptyOrNull(data.get(Indexes.COMMENT_ID), "0"));
            long accountId = Long.parseLong(Utils.defaultStringIfEmptyOrNull(u.get(Indexes.USER_ACCOUNT_ID), "0"));
            commentList.add(new GDComment(
                    commentId,
                    Long.parseLong(Utils.defaultStringIfEmptyOrNull(data.get(Indexes.COMMENT_SENDER_ID), "0")),
                    Utils.defaultStringIfEmptyOrNull(u.get(Indexes.USER_NAME), "-"),
                    Utils.b64Decode(Utils.defaultStringIfEmptyOrNull(data.get(Indexes.COMMENT_BODY), "0")),
                    Integer.parseInt(Utils.defaultStringIfEmptyOrNull(data.get(Indexes.COMMENT_LIKES), "0")),
                    Utils.defaultStringIfEmptyOrNull(data.get(Indexes.COMMENT_UPLOADED_TIMESTAMP), "NA"),
                    Integer.parseInt(Utils.defaultStringIfEmptyOrNull(data.get(Indexes.COMMENT_PERCENTAGE), "0")),
                    Integer.parseInt(Utils.defaultStringIfEmptyOrNull(data.get(Indexes.COMMENT_SENDER_ROLE), "0")),
                    () -> client.fetch(new GDUserProfileDataRequest(client, accountId))));
        }
        Tuple3<Integer, Integer, Integer>  pageInfo = ParseUtils.extractPageInfo(split1[1]);
        return new GDPaginator<>(commentList, page, pageInfo.getT3(), pageInfo.getT1(), newPage ->
                client.fetch(new GDLevelCommentRequest(client, levelId, isRecent, newPage)));
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof GDLevelCommentRequest)) {
            return false;
        }
        GDLevelCommentRequest r = (GDLevelCommentRequest) obj;
        return r.levelId == levelId && r.isRecent == isRecent && r.page == page;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(levelId) ^ (isRecent ? 1 : 0) ^ page;
    }
}

