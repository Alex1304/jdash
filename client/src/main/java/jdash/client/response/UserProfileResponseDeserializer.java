package jdash.client.response;

import jdash.common.PrivacySetting;
import jdash.common.Role;
import jdash.common.entity.GDUser;
import jdash.common.internal.InternalUtils;

import java.util.Map;
import java.util.function.Function;

import static jdash.common.internal.Indexes.*;

public class UserProfileResponseDeserializer implements Function<String, GDUser> {

    @Override
    public GDUser apply(String response) {
        Map<Integer, String> data = InternalUtils.splitToMap(response, ":");
        int strRoleIndex = Integer.parseInt(data.getOrDefault(USER_ROLE, "0"));
        int privateMessageIndex = Integer.parseInt(data.getOrDefault(USER_PRIVATE_MESSAGE_POLICY, "0"));
        int commentHistoryIndex = Integer.parseInt(data.getOrDefault(USER_COMMENT_HISTORY_POLICY, "0"));
        return InternalUtils.initUserBuilder(data)
                .diamonds(Integer.parseInt(data.getOrDefault(USER_DIAMONDS, "0")))
                .globalRank(Integer.parseInt(data.getOrDefault(USER_GLOBAL_RANK, "0")))
                .cubeIconId(Integer.parseInt(data.getOrDefault(USER_ICON_CUBE, "0")))
                .shipIconId(Integer.parseInt(data.getOrDefault(USER_ICON_SHIP, "0")))
                .ufoIconId(Integer.parseInt(data.getOrDefault(USER_ICON_UFO, "0")))
                .ballIconId(Integer.parseInt(data.getOrDefault(USER_ICON_BALL, "0")))
                .waveIconId(Integer.parseInt(data.getOrDefault(USER_ICON_WAVE, "0")))
                .robotIconId(Integer.parseInt(data.getOrDefault(USER_ICON_ROBOT, "0")))
                .spiderIconId(Integer.parseInt(data.getOrDefault(USER_ICON_SPIDER, "0")))
                .hasGlowOutline(data.getOrDefault(USER_GLOW_OUTLINE_2, "0").equals("1"))
                .deathEffectId(Integer.parseInt(data.getOrDefault(USER_DEATH_EFFECT, "0")))
                .youtube(data.getOrDefault(USER_YOUTUBE, ""))
                .twitter(data.getOrDefault(USER_TWITTER, ""))
                .twitch(data.getOrDefault(USER_TWITCH, ""))
                .role(Role.values()[strRoleIndex >= Role.values().length ? 0 : strRoleIndex])
                .hasFriendRequestsEnabled(data.getOrDefault(USER_FRIEND_REQUEST_POLICY, "0").equals("0"))
                .privateMessagePolicy(PrivacySetting.values()[
                        privateMessageIndex >= PrivacySetting.values().length ? 0 : privateMessageIndex])
                .commentHistoryPolicy(PrivacySetting.values()[
                        commentHistoryIndex >= PrivacySetting.values().length ? 0 : commentHistoryIndex])
                .build();
    }
}
