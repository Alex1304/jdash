package jdash.graphics;

import jdash.common.AccessPolicy;
import jdash.common.Role;
import jdash.common.entity.ImmutableGDUserProfile;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static jdash.graphics.test.ImageTestUtils.assertImageEquals;
import static jdash.graphics.test.ImageTestUtils.loadTestImage;

class IconSetFactoryTest {

    @Test
    void shouldGenerateIconSetForAlex1304() throws IOException {
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
                .swingIconId(22)
                .jetpackIconId(1)
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
        final var factory = IconSetFactory.forUser(user);
        final var output = factory.createIconSet();
        assertImageEquals(loadTestImage("/tests/iconSet.png"), output);
    }
}
