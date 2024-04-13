package jdash.graphics;

import jdash.common.AccessPolicy;
import jdash.common.IconType;
import jdash.common.Role;
import jdash.common.entity.ImmutableGDUserProfile;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

class GDUserIconGeneratorTest {

    @Test
    void generateIcon() throws IOException {
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
                .swingIconId(1)
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
        final var iconGenerator = GDUserIconGenerator.create(user);
        final var output = iconGenerator.generateIconSet();
        ImageIO.write(output, "png",
                new File(System.getProperty("java.io.tmpdir") + File.separator + "icon.png"));
        showImage(output);
    }

    static void showImage(BufferedImage image) {
        final var panel = new JPanel();
        panel.setBackground(Color.DARK_GRAY);
        final var l = new JLabel(new ImageIcon(image));
        l.setBorder(BorderFactory.createLineBorder(Color.GREEN));
        panel.add(l);
        final var f = new JFrame();
        f.setSize(new Dimension(image.getWidth() + 50, image.getHeight() + 50));
        f.add(panel);
        f.setVisible(true);
        new Scanner(System.in).nextLine();
    }
}
