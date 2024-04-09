package jdash.graphics.internal;

import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.configuration2.plist.XMLPropertyListConfiguration;

import java.util.*;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.*;

public final class AnimationParser {

    private AnimationParser() {
        throw new AssertionError();
    }

    public static List<AnimationFrame> parseFrames(String plistName, String prefix,
                                                   List<SpriteElement> animatedElements) {
        try {
            final var plist = new Configurations()
                    .fileBased(XMLPropertyListConfiguration.class, GameSheetParser.class
                            .getResource(plistName));
            final var stream = StreamSupport.stream(Spliterators.spliteratorUnknownSize(plist.getKeys(prefix),
                    Spliterator.ORDERED), false);
            final var mappings = stream
                    .map(key -> Map.entry(key.substring(prefix.length() + 1).split("\\."), plist.getString(key)))
                    .collect(groupingBy(entry -> entry.getKey()[0], toUnmodifiableMap(entry -> entry.getKey()[1],
                            Map.Entry::getValue)));
            return mappings.values().stream()
                    .flatMap(fields -> animatedElements.stream()
                            .filter(el -> Objects.equals(
                                    el.getName().split("_")[2],
                                    fields.get("texture").split("_")[2]))
                            .map(el -> AnimationFrame.from(el, fields)))
                    .collect(toUnmodifiableList());
        } catch (ConfigurationException e) {
            throw new RuntimeException(e);
        }
    }
}
