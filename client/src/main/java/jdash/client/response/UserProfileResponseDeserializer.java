package jdash.client.response;

import jdash.common.PrivacySetting;
import jdash.common.Role;
import jdash.common.entity.GDUser;
import jdash.common.entity.ImmutableGDUser;
import jdash.common.internal.Indexes;
import jdash.common.internal.InternalUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.function.Function;

public class UserProfileResponseDeserializer implements Function<String, GDUser> {

    @Override
    public GDUser apply(String response) {
        Map<Integer, String> data = InternalUtils.splitToMap(response, ":");
        String strPlayerID = StringUtils.defaultIfEmpty(data.get(Indexes.USER_PLAYER_ID), "0");
        String strAccountID = StringUtils.defaultIfEmpty(data.get(Indexes.USER_ACCOUNT_ID), "0");
        String strName = StringUtils.defaultIfEmpty(data.get(Indexes.USER_NAME), "");
        String strStars = StringUtils.defaultIfEmpty(data.get(Indexes.USER_STARS), "0");
        String strDemons = StringUtils.defaultIfEmpty(data.get(Indexes.USER_DEMONS), "0");
        String strDiamonds = StringUtils.defaultIfEmpty(data.get(Indexes.USER_DIAMONDS), "0");
        String strSecretCoins = StringUtils.defaultIfEmpty(data.get(Indexes.USER_SECRET_COINS), "0");
        String strUserCoins = StringUtils.defaultIfEmpty(data.get(Indexes.USER_USER_COINS), "0");
        String strCreatorPoints = StringUtils.defaultIfEmpty(data.get(Indexes.USER_CREATOR_POINTS), "0");
        String strGlobalRank = StringUtils.defaultIfEmpty(data.get(Indexes.USER_GLOBAL_RANK), "0");
        String strCube = StringUtils.defaultIfEmpty(data.get(Indexes.USER_ICON_CUBE), "0");
        String strShip = StringUtils.defaultIfEmpty(data.get(Indexes.USER_ICON_SHIP), "0");
        String strUfo = StringUtils.defaultIfEmpty(data.get(Indexes.USER_ICON_UFO), "0");
        String strBall = StringUtils.defaultIfEmpty(data.get(Indexes.USER_ICON_BALL), "0");
        String strWave = StringUtils.defaultIfEmpty(data.get(Indexes.USER_ICON_WAVE), "0");
        String strRobot = StringUtils.defaultIfEmpty(data.get(Indexes.USER_ICON_ROBOT), "0");
        String strSpider = StringUtils.defaultIfEmpty(data.get(Indexes.USER_ICON_SPIDER), "0");
        String strGlowOutline = StringUtils.defaultIfEmpty(data.get(Indexes.USER_GLOW_OUTLINE_2), "0");
        String strDeathEffect = StringUtils.defaultIfEmpty(data.get(Indexes.USER_DEATH_EFFECT), "0");
        String strColor1 = StringUtils.defaultIfEmpty(data.get(Indexes.USER_COLOR_1), "0");
        String strColor2 = StringUtils.defaultIfEmpty(data.get(Indexes.USER_COLOR_2), "0");
        String strRole = StringUtils.defaultIfEmpty(data.get(Indexes.USER_ROLE), "0");
        String strYoutube = StringUtils.defaultIfEmpty(data.get(Indexes.USER_YOUTUBE), "");
        String strTwitter = StringUtils.defaultIfEmpty(data.get(Indexes.USER_TWITTER), "");
        String strTwitch = StringUtils.defaultIfEmpty(data.get(Indexes.USER_TWITCH), "");
        String strFriendRequest = StringUtils.defaultIfEmpty(data.get(Indexes.USER_FRIEND_REQUEST_POLICY), "0");
        String strPrivateMessage = StringUtils.defaultIfEmpty(data.get(Indexes.USER_PRIVATE_MESSAGE_POLICY), "0");
        String strCommentHistory = StringUtils.defaultIfEmpty(data.get(Indexes.USER_COMMENT_HISTORY_POLICY), "0");
        int strRoleIndex = Integer.parseInt(strRole);
        int privateMessageIndex = Integer.parseInt(strPrivateMessage);
        int commentHistoryIndex = Integer.parseInt(strCommentHistory);
        return ImmutableGDUser.builder()
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
                .diamonds(Integer.parseInt(strDiamonds))
                .globalRank(Integer.parseInt(strGlobalRank))
                .cubeIconId(Integer.parseInt(strCube))
                .shipIconId(Integer.parseInt(strShip))
                .ufoIconId(Integer.parseInt(strUfo))
                .ballIconId(Integer.parseInt(strBall))
                .waveIconId(Integer.parseInt(strWave))
                .robotIconId(Integer.parseInt(strRobot))
                .spiderIconId(Integer.parseInt(strSpider))
                .hasGlowOutline(strGlowOutline.equals("1"))
                .deathEffectId(Integer.parseInt(strDeathEffect))
                .youtube(strYoutube)
                .twitter(strTwitter)
                .twitch(strTwitch)
                .role(Role.values()[strRoleIndex >= Role.values().length ? 0 : strRoleIndex])
                .hasFriendRequestsEnabled(strFriendRequest.equals("0"))
                .privateMessagePolicy(PrivacySetting.values()[
                        privateMessageIndex >= PrivacySetting.values().length ? 0 : privateMessageIndex])
                .commentHistoryPolicy(PrivacySetting.values()[
                        commentHistoryIndex >= PrivacySetting.values().length ? 0 : commentHistoryIndex])
                .build();
    }
}
