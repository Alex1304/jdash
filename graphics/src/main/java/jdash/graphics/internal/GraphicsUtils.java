package jdash.graphics.internal;

import com.fasterxml.jackson.databind.ObjectMapper;
import ij.IJ;
import jdash.graphics.LegacySpriteFactory;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.List;
import java.util.*;
import java.util.function.Predicate;

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
    public static List<SpriteElement> orderIconLayers(List<SpriteElement> elements) {
        final var result = new ArrayList<>(elements);
        Collections.reverse(result);
        pushSpriteToBackIf(result, sp -> sp.getName().contains("_2_"));
        pullSpriteToFrontIf(result, sp -> sp.getName().matches("(robot|spider)_[0-9]{2,3}_(02|03|04)_.*"));
        dupeSpriteIf(result, sp -> sp.getName().matches("robot_[0-9]{2,3}_(02|03|04)_.*"), 1);
        dupeSpriteIf(result, sp -> sp.getName().matches("spider_[0-9]{2,3}_02_.*") && !sp.getName().contains("extra")
                , 2);
        pullSpriteToFrontIf(result, sp -> sp.getName().matches("robot_[0-9]{2,3}_02_.*") && !sp.isDuplicate());
        pullSpriteToFrontIf(result, sp -> sp.getName().matches("robot_[0-9]{2,3}_04_.*") && !sp.isDuplicate());
        pushSpriteToBackIf(result, sp -> !sp.getName().contains("_2_") && sp.isDuplicate());
        pushSpriteToBackIf(result, sp -> sp.getName().contains("_2_") && sp.isDuplicate());
        pushSpriteToBackIf(result, sp -> sp.getName().matches("spider_[0-9]{2,3}_04_.*"));
        pullSpriteToFrontIf(result, sp -> sp.getName().contains("extra"));
        pushSpriteToBackIf(result, sp -> sp.getName().contains("_glow_"));
        return result;
    }


    private static void pullSpriteToFrontIf(List<SpriteElement> spList, Predicate<SpriteElement> cond) {
        int offset = 0;
        for (int i = 0; i < spList.size(); i++) {
            final var sp = spList.get(i - offset);
            if (cond.test(sp)) {
                spList.remove(i - offset);
                spList.add(sp);
                offset++;
            }
        }
    }

    private static void pushSpriteToBackIf(List<SpriteElement> spList, Predicate<SpriteElement> cond) {
        for (int i = 0; i < spList.size(); i++) {
            final var sp = spList.get(i);
            if (cond.test(sp)) {
                //noinspection SuspiciousListRemoveInLoop
                spList.remove(i);
                spList.add(0, sp);
            }
        }
    }

    private static void dupeSpriteIf(List<SpriteElement> spList, Predicate<SpriteElement> cond, int nbDup) {
        final int initialSize = spList.size();
        int offset = 0;
        for (int i = 0; i < initialSize; i++) {
            final var sp = spList.get(i + offset);
            if (cond.test(sp)) {
                var dupe = sp.duplicate();
                for (int d = 0; d < nbDup; d++) {
                    spList.add(0, dupe);
                    offset++;
                    dupe = dupe.duplicate();
                }
            }
        }
    }
}
