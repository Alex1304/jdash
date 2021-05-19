package jdash.graphics;

import jdash.common.AccessPolicy;
import jdash.common.IconType;
import jdash.common.Role;
import jdash.common.entity.ImmutableGDUserProfile;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public final class GDUserIconSetTest {

    public static void main(String[] args) throws IOException {
        var user = ImmutableGDUserProfile.builder()
                .commentHistoryPolicy(AccessPolicy.ALL)
                .privateMessagePolicy(AccessPolicy.ALL)
                .hasFriendRequestsEnabled(true)
                .role(Role.MODERATOR)
                .twitch("gd_alex1304")
                .twitter("gd_alex1304")
                .youtube("UC0hFAVN-GAbZYuf_Hfk1Iog")
                .hasGlowOutline(true)
                .spiderIconId(15)
                .robotIconId(21)
                .waveIconId(24)
                .ballIconId(30)
                .accountId(98006)
                .ufoIconId(3)
                .shipIconId(7)
                .cubeIconId(29)
                .globalRank(33266)
                .diamonds(19336)
                .demons(46)
                .creatorPoints(21)
                .stars(5658)
                .userCoins(818)
                .color2Id(9)
                .color1Id(12)
                .secretCoins(100)
                .name("Alex1304")
                .playerId(4063664)
                .build();
        var iconSet = GDUserIconSet.create(user, SpriteFactory.create());
        ImageIO.write(iconSet.generateIcon(IconType.BALL), "png",
                new File(System.getProperty("java.io.tmpdir") + File.separator + "icon.png"));
    }
}
