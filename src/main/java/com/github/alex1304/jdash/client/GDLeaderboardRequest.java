package com.github.alex1304.jdash.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;


import com.github.alex1304.jdash.entity.GDUserSearchData;
import com.github.alex1304.jdash.entity.IconType;
import com.github.alex1304.jdash.exception.GDClientException;
import com.github.alex1304.jdash.util.*;

class GDLeaderboardRequest extends AbstractAuthenticatedGDRequest<List<GDUserSearchData>> {

    private LeaderboardType type;
    private final int count;

    GDLeaderboardRequest(AuthenticatedGDClient client, LeaderboardType type, int count) {
        super(client);
        this.type = Objects.requireNonNull(type);
        this.count = count;
    }

    @Override
    public String getPath() {
        return Routes.USER_SCORES;
    }

    @Override
    void putParams(Map<String, String> params) {
        params.put("type", type.getVal());
        params.put("count", "" + count);
    }

    @Override
    List<GDUserSearchData> parseResponse0(String response) throws GDClientException {
        List<GDUserSearchData> ranking = new ArrayList<>();
        String[] users = response.split("\\|");
        for (String u : users) {
            Map<Integer, String> data = ParseUtils.splitToMap(u, ":");
            String strPlayerID = Utils.defaultStringIfEmptyOrNull(data.get(Indexes.USER_PLAYER_ID), "0");
            String strAccountID = Utils.defaultStringIfEmptyOrNull(data.get(Indexes.USER_ACCOUNT_ID), "0");
            String strStars = Utils.defaultStringIfEmptyOrNull(data.get(Indexes.USER_STARS), "0");
            String strDemons = Utils.defaultStringIfEmptyOrNull(data.get(Indexes.USER_DEMONS), "0");
            String strSecretCoins = Utils.defaultStringIfEmptyOrNull(data.get(Indexes.USER_SECRET_COINS), "0");
            String strUserCoins = Utils.defaultStringIfEmptyOrNull(data.get(Indexes.USER_USER_COINS), "0");
            String strCreatorPoints = Utils.defaultStringIfEmptyOrNull(data.get(Indexes.USER_CREATOR_POINTS), "0");
            String strColor1 = Utils.defaultStringIfEmptyOrNull(data.get(Indexes.USER_COLOR_1), "0");
            String strColor2 = Utils.defaultStringIfEmptyOrNull(data.get(Indexes.USER_COLOR_2), "0");
            String strHasGlowOutline = Utils.defaultStringIfEmptyOrNull(data.get(Indexes.USER_GLOW_OUTLINE), "0");
            String strMainIconId = Utils.defaultStringIfEmptyOrNull(data.get(Indexes.USER_ICON), "0");
            String strName = Utils.defaultStringIfEmptyOrNull(data.get(Indexes.USER_NAME), "-");
            String strIconType = Utils.defaultStringIfEmptyOrNull(data.get(Indexes.USER_ICON_TYPE), "0");
            int iconTypeIndex = Integer.parseInt(strIconType);

            ranking.add(new GDUserSearchData(
                    Long.parseLong(strPlayerID),
                    strName,
                    Integer.parseInt(strSecretCoins),
                    Integer.parseInt(strUserCoins),
                    Integer.parseInt(strColor1),
                    Integer.parseInt(strColor2),
                    Long.parseLong(strAccountID),
                    Integer.parseInt(strStars),
                    Integer.parseInt(strCreatorPoints),
                    Integer.parseInt(strDemons),
                    !strHasGlowOutline.equals("0"),
                    Integer.parseInt(strMainIconId),
                    IconType.values()[iconTypeIndex >= IconType.values().length ? 0 : iconTypeIndex]));
        }
        if(type.getVal().equals("friends")){
            ranking.sort(((o1, o2) -> o2.getStars() - o1.getStars()));
        }
        return ranking;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof GDLeaderboardRequest)) {
            return false;
        }
        GDLeaderboardRequest r = (GDLeaderboardRequest) obj;
        return r.type.getVal().equals(type.getVal()) && r.count == count;
    }

    @Override
    public int hashCode() {
        return type.getVal().hashCode() ^ count;
    }
}

