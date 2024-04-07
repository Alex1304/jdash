package jdash.graphics.internal;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.FilteredImageSource;
import java.awt.image.RGBImageFilter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.HashMap;
import java.util.Map;

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
        final var tuple = Tuple.parse(tupleStr, Double::parseDouble);
        return new Point2D.Double(tuple.getA(), tuple.getB());
    }

    public static Rectangle parseRectangle(String tupleStr) {
        String[] split = tupleStr.substring(2, tupleStr.length() - 2).split("}?,\\{?");
        return new Rectangle((int) Double.parseDouble(split[0]), (int) Double.parseDouble(split[1]),
                (int) Double.parseDouble(split[2]), (int) Double.parseDouble(split[3]));
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
}
