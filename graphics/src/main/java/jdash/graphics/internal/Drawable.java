package jdash.graphics.internal;

import java.awt.image.BufferedImage;

public interface Drawable extends Comparable<Drawable> {

    int getWidth();

    int getHeight();

    BufferedImage render(GameResourceContainer resources, ColorSelection colorSelection);

    int drawOrder();

    @Override
    default int compareTo(Drawable o) {
        return drawOrder() - o.drawOrder();
    }
}
