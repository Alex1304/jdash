package jdash.graphics.internal;

import java.awt.*;
import java.awt.geom.AffineTransform;

public interface Drawable extends Comparable<Drawable> {

    AffineTransform getTransform();

    void draw(Graphics2D g, GameResourceContainer resources, ColorSelection colorSelection);

    int drawOrder();

    @Override
    default int compareTo(Drawable o) {
        return drawOrder() - o.drawOrder();
    }
}
