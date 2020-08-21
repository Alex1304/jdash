package com.github.alex1304.jdash.client;

import com.github.alex1304.jdash.entity.GDUserSearchData;
import com.github.alex1304.jdash.entity.IconType;
import com.github.alex1304.jdash.exception.GDClientException;
import com.github.alex1304.jdash.util.Indexes;
import com.github.alex1304.jdash.util.ParseUtils;
import com.github.alex1304.jdash.util.Routes;
import com.github.alex1304.jdash.util.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class GDBlockedUsersRequest extends AbstractAuthenticatedGDRequest<List<GDUserSearchData>> {

    GDBlockedUsersRequest(AuthenticatedGDClient client) {
        super(client);
    }

    @Override
    public String getPath() {
        return Routes.GET_USER_LIST;
    }

    @Override
    void putParams(Map<String, String> params) {
        params.put("type", "1");
    }

    @Override
    List<GDUserSearchData> parseResponse0(String response) throws GDClientException {
        List<GDUserSearchData> blockedList = new ArrayList<>();
        String[] users = response.split("\\|");
        for (String u : users) {
            Map<Integer, String> data = ParseUtils.splitToMap(u, ":");
            String strPlayerID = Utils.defaultStringIfEmptyOrNull(data.get(Indexes.USER_PLAYER_ID), "0");
            String strAccountID = Utils.defaultStringIfEmptyOrNull(data.get(Indexes.USER_ACCOUNT_ID), "0");
            String strColor1 = Utils.defaultStringIfEmptyOrNull(data.get(Indexes.USER_COLOR_1), "0");
            String strColor2 = Utils.defaultStringIfEmptyOrNull(data.get(Indexes.USER_COLOR_2), "0");
            String strHasGlowOutline = Utils.defaultStringIfEmptyOrNull(data.get(Indexes.USER_GLOW_OUTLINE), "0");
            String strMainIconId = Utils.defaultStringIfEmptyOrNull(data.get(Indexes.USER_ICON), "0");
            String strName = Utils.defaultStringIfEmptyOrNull(data.get(Indexes.USER_NAME), "-");
            String strIconType = Utils.defaultStringIfEmptyOrNull(data.get(Indexes.USER_ICON_TYPE), "0");
            int iconTypeIndex = Integer.parseInt(strIconType);
            blockedList.add(new GDUserSearchData(
                    Long.parseLong(strPlayerID),
                    strName,
                    0,
                    0,
                    Integer.parseInt(strColor1),
                    Integer.parseInt(strColor2),
                    Long.parseLong(strAccountID),
                    0,
                    0,
                    0,
                    !strHasGlowOutline.equals("0"),
                    Integer.parseInt(strMainIconId),
                    IconType.values()[iconTypeIndex >= IconType.values().length ? 0 : iconTypeIndex]));
        }
        return blockedList;
    }
}
