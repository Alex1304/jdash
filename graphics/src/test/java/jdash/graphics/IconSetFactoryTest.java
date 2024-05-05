package jdash.graphics;

import jdash.common.PrivacySetting;
import jdash.common.Role;
import jdash.common.entity.GDUser;
import jdash.common.entity.GDUserProfile;
import jdash.common.entity.GDUserStats;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Optional;

import static jdash.graphics.test.ImageTestUtils.assertImageEquals;
import static jdash.graphics.test.ImageTestUtils.loadTestImage;

public final class IconSetFactoryTest {

    @Test
    public void shouldGenerateIconSetForAlex1304() throws IOException {
        final var user = new GDUser(
                4063664,
                98006,
                "Alex1304",
                12,
                9,
                true,
                Optional.empty(),
                Optional.empty(),
                Optional.of(Role.MODERATOR)
        );
        final var expected = new GDUserProfile(
                user,
                new GDUserStats(
                        user,
                        5658,
                        0,
                        19336,
                        100,
                        818,
                        46,
                        21,
                        Optional.empty()
                ),
                33266,
                29,
                7,
                3,
                30,
                24,
                21,
                15,
                22,
                1,
                9,
                "UC0hFAVN-GAbZYuf_Hfk1Iog",
                "gd_alex1304",
                "gd_alex1304",
                true,
                PrivacySetting.ALL,
                PrivacySetting.ALL,
                Optional.empty(),
                Optional.empty(),
                Optional.empty()
        );

        final var factory = IconSetFactory.forUser(expected);
        final var output = factory.createIconSet();
        assertImageEquals(loadTestImage("/tests/iconSet.png"), output);
    }
}
