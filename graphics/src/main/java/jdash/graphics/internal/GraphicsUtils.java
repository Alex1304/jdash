package jdash.graphics.internal;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
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
}
