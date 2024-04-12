package jdash.graphics.internal;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.RGBImageFilter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static jdash.graphics.internal.IconRenderer.*;

public final class GraphicsUtils {

    public static Map<Integer, PlayerColor> loadColors() {
        try {
            final var colorsFile = GraphicsUtils.class.getResource("/colors.json");
            if (colorsFile == null) {
                throw new AssertionError("/colors.json not found");
            }
            final var objectMapper = new ObjectMapper();
            final var object = objectMapper.readTree(colorsFile);
            final var fields = object.fields();
            final var colorMap = new HashMap<Integer, PlayerColor>();
            while (fields.hasNext()) {
                final var field = fields.next();
                final var name = field.getKey();
                final var element = field.getValue();
                final var colorValue = objectMapper.treeToValue(element, PlayerColor.class);
                colorMap.put(Integer.parseInt(name), colorValue);
            }
            return colorMap;
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public static Point2D.Double parsePoint(String tupleStr) {
        return Tuple.parse(tupleStr, Double::parseDouble).as(Point2D.Double::new);
    }

    public static Dimension parseDimension(String tupleStr) {
        return Tuple.parse(tupleStr, s -> (int) Math.ceil(Double.parseDouble(s))).as(Dimension::new);
    }

    public static Rectangle2D.Double parseRectangle(String tupleStr) {
        String[] split = tupleStr.substring(2, tupleStr.length() - 2).split("}?,\\{?");
        return new Rectangle2D.Double(Double.parseDouble(split[0]), Double.parseDouble(split[1]),
                Double.parseDouble(split[2]), Double.parseDouble(split[3]));
    }

    public static Image applyColor(Image img, Color color) {
        if (color == null) return img;
        return Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(img.getSource(), new RGBImageFilter() {
            @Override
            public int filterRGB(int x, int y, int rgb) {
                return rgb & color.getRGB();
            }
        }));
    }

    public static Image reduceBrightness(Image img) {
        return Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(img.getSource(), new RGBImageFilter() {
            @Override
            public int filterRGB(int x, int y, int rgb) {
                return rgb & 0xFF808080;
            }
        }));
    }

    public static BufferedImage renderLayers(List<? extends Drawable> layers, GameResourceContainer resources,
                                             ColorSelection colorSelection) {
        final var image = new BufferedImage(ICON_WIDTH, ICON_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        final var g = image.createGraphics();
        layers.forEach(layer -> g.drawImage(layer.render(resources, colorSelection),
                (ICON_WIDTH - layer.getWidth()) / 2, (ICON_HEIGHT - layer.getHeight()) / 2, null));
        g.dispose();
        return image;
    }
}
