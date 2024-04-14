package jdash.graphics.internal;

import jdash.graphics.ColorSelection;
import jdash.graphics.GameResourceContainer;

import java.awt.image.BufferedImage;

public interface Renderable extends Comparable<Renderable> {

    int getWidth();

    int getHeight();

    BufferedImage render(GameResourceContainer resources, ColorSelection colorSelection);

    int getZIndex();

    @Override
    default int compareTo(Renderable o) {
        return getZIndex() - o.getZIndex();
    }
}
