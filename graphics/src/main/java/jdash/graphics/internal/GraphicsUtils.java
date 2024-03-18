package jdash.graphics.internal;

import com.fasterxml.jackson.databind.ObjectMapper;
import ij.IJ;
import jdash.graphics.LegacySpriteFactory;
import jdash.graphics.exception.SpriteLoadException;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.configuration2.plist.XMLPropertyListConfiguration;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public final class GraphicsUtils {

    public static Map<Integer, PlayerColor> loadColors() throws IOException {
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
    }

    public static void testIJ() {
        var url = GraphicsUtils.class.getResource("/GJ_GameSheetIcons-hd.png");
        if (url == null) {
            throw new AssertionError("Game sheet URL is null");
        }
        final var imp = IJ.openImage(url.toString());
        final var processor = imp.getProcessor();
        processor.flipHorizontal();
        imp.show();
    }

    public static void addGlow(BufferedImage img, int color1Id, int color2Id) {
        // White glow if both colors are black. If color2 is black, use color1 instead.
        final Color color = LegacySpriteFactory.getGlowColor(color1Id, color2Id);
        final int w = img.getWidth(), h = img.getHeight();
        final int threshold = 120;
        final int glowWidth = 4;
        // create an array of distances (1 per pixel) and fill it with -1's
        int[][] distances = new int[h][w];
        for (int i = 0; i < h; i++) {
            Arrays.fill(distances[i], -1);
        }
        // for each pixel, mark the black ones as distance 0, and add them to a deque
        ArrayDeque<Point> deque = new ArrayDeque<>();
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                int data = img.getRGB(x, y);
                int alpha = (data & 0xff000000) >> 24;
                int red = (data & 0x00ff0000) >> 16;
                int green = (data & 0x0000ff00) >> 8;
                int blue = data & 0x000000ff;
                if (red < threshold && green < threshold && blue < threshold && alpha == -1) {
                    distances[y][x] = 0;
                    deque.add(new Point(x, y));
                }
            }
        }
        // for each pixel in deque, look at pixels immediately around that are -1,
        // change them to the value of distance + 1 and add them to deque
        while (!deque.isEmpty()) {
            Point pix = deque.remove();
            int pixDistance = distances[pix.y][pix.x];
            for (int y = Math.max(0, pix.y - 1); y <= Math.min(pix.y + 1, h - 1); y++) {
                for (int x = Math.max(0, pix.x - 1); x <= Math.min(pix.x + 1, w - 1); x++) {
                    int lookAtDistance = distances[y][x];
                    if (lookAtDistance == -1) {
                        distances[y][x] = pixDistance + 1;
                        deque.add(new Point(x, y));
                    }
                }
            }
        }
        // Colorize all pixels that have a distance value less than or equal to glowWidth
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                int alpha = (img.getRGB(x, y) & 0xff000000) >> 24;
                // Apply color only if transparent pixel
                if (alpha != -1 && alpha < threshold && distances[y][x] <= glowWidth) {
                    img.setRGB(x, y, color.getRGB());
                }
            }
        }
    }

    public static double[] parseSimpleTuple(String tupleStr) {
        String[] split = tupleStr.substring(1, tupleStr.length() - 1).split(",");
        return new double[]{Double.parseDouble(split[0]), Double.parseDouble(split[1])};
    }

    public static Point2D.Double parsePoint(String tupleStr) {
        String[] split = tupleStr.substring(1, tupleStr.length() - 1).split(",");
        return new Point2D.Double(Double.parseDouble(split[0]), Double.parseDouble(split[1]));
    }

    public static Point2D.Double[] parsePointPair(String tupleStr) {
        String[] split = tupleStr.substring(2, tupleStr.length() - 2).split("}?,\\{?");
        return new Point2D.Double[]{
                new Point2D.Double(Double.parseDouble(split[0]), Double.parseDouble(split[1])),
                new Point2D.Double(Double.parseDouble(split[2]), Double.parseDouble(split[3]))
        };
    }


    public static double[] parseDoubleTuple(String tupleStr) {
        String[] split = tupleStr.substring(2, tupleStr.length() - 2).split("}?,\\{?");
        return new double[]{Double.parseDouble(split[0]), Double.parseDouble(split[1]), Double.parseDouble(split[2]),
                Double.parseDouble(split[3])};
    }
}
