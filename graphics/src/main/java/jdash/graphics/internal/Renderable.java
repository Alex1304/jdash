package jdash.graphics.internal;

import java.awt.image.BufferedImage;

public interface Renderable extends Comparable<Renderable> {

    String getName();

    int getWidth();

    int getHeight();

    BufferedImage render(BufferedImage spriteSheet, RenderFilter filter);

    default BufferedImage render(BufferedImage spriteSheet) {
        return render(spriteSheet, RenderFilter.NONE);
    }

    int getZIndex();

    @Override
    default int compareTo(Renderable o) {
        return getZIndex() - o.getZIndex();
    }
}
