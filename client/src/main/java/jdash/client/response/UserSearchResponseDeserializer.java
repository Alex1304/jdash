package jdash.client.response;

import jdash.common.IconType;
import jdash.common.entity.GDUser;
import jdash.common.entity.ImmutableGDUser;
import jdash.common.internal.Indexes;
import jdash.common.internal.InternalUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class UserSearchResponseDeserializer implements Function<String, List<GDUser>> {

    @Override
    public List<GDUser> apply(String response) {
        var list = new ArrayList<GDUser>();
        var split1 = response.split("#");
        var split2 = split1[0].split("\\|");
        for (String u : split2) {
            Map<Integer, String> data = InternalUtils.splitToMap(u, ":");
            String strPlayerID = StringUtils.defaultIfEmpty(data.get(Indexes.USER_PLAYER_ID), "0");
            String strAccountID = StringUtils.defaultIfEmpty(data.get(Indexes.USER_ACCOUNT_ID), "0");
            String strStars = StringUtils.defaultIfEmpty(data.get(Indexes.USER_STARS), "0");
            String strDemons = StringUtils.defaultIfEmpty(data.get(Indexes.USER_DEMONS), "0");
            String strSecretCoins = StringUtils.defaultIfEmpty(data.get(Indexes.USER_SECRET_COINS), "0");
            String strUserCoins = StringUtils.defaultIfEmpty(data.get(Indexes.USER_USER_COINS), "0");
            String strCreatorPoints = StringUtils.defaultIfEmpty(data.get(Indexes.USER_CREATOR_POINTS), "0");
            String strColor1 = StringUtils.defaultIfEmpty(data.get(Indexes.USER_COLOR_1), "0");
            String strColor2 = StringUtils.defaultIfEmpty(data.get(Indexes.USER_COLOR_2), "0");
            String strHasGlowOutline = StringUtils.defaultIfEmpty(data.get(Indexes.USER_GLOW_OUTLINE), "0");
            String strMainIconId = StringUtils.defaultIfEmpty(data.get(Indexes.USER_ICON), "0");
            String strName = StringUtils.defaultIfEmpty(data.get(Indexes.USER_NAME), "-");
            String strIconType = StringUtils.defaultIfEmpty(data.get(Indexes.USER_ICON_TYPE), "0");
            int iconTypeIndex = Integer.parseInt(strIconType);
            list.add(ImmutableGDUser.builder()
                    .playerId(Long.parseLong(strPlayerID))
                    .name(strName)
                    .secretCoins(Integer.parseInt(strSecretCoins))
                    .userCoins(Integer.parseInt(strUserCoins))
                    .color1Id(Integer.parseInt(strColor1))
                    .color2Id(Integer.parseInt(strColor2))
                    .accountId(Long.parseLong(strAccountID))
                    .stars(Integer.parseInt(strStars))
                    .creatorPoints(Integer.parseInt(strCreatorPoints))
                    .demons(Integer.parseInt(strDemons))
                    .hasGlowOutline(!strHasGlowOutline.equals("0"))
                    .mainIconId(Integer.parseInt(strMainIconId))
                    .mainIconType(IconType.values()[iconTypeIndex >= IconType.values().length ? 0 : iconTypeIndex])
                    .build());
        }
        return list;
    }
}
