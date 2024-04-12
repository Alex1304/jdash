package jdash.graphics.internal;

import jdash.common.IconType;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Scanner;

class IconRendererTest {

    @Test
    void makeIcon() {
        final var renderer = IconRenderer.load(IconType.SPIDER, 7);
        final var output = renderer.render(ColorSelection.defaultColors(false));
        showImage(output);
    }

    static void showImage(BufferedImage image) {
        final var panel = new JPanel();
        panel.setBackground(Color.DARK_GRAY);
        panel.add(new JLabel(new ImageIcon(image)));
        final var f = new JFrame();
        f.setSize(new Dimension(image.getWidth(), image.getHeight()));
        f.add(panel);
        f.setVisible(true);
        new Scanner(System.in).nextLine();
    }
}