package jdash.graphics.internal;

import java.awt.*;

public record PlayerColor(int r, int g, int b) {

    public Color toColor() {
        return new Color(r, g, b);
    }
}
