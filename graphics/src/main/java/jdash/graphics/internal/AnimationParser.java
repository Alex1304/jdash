package jdash.graphics.internal;

import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.configuration2.plist.XMLPropertyListConfiguration;

import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.StreamSupport;

public final class AnimationParser {

    private AnimationParser() {
        throw new AssertionError();
    }

    public static List<Drawable> parseFrames(String plistName, String prefix, List<SpriteElement> animatedElements) {
        try {
            final var plist = new Configurations()
                    .fileBased(XMLPropertyListConfiguration.class, GameSheetParser.class
                            .getResource(plistName));
            final var stream = StreamSupport.stream(Spliterators.spliteratorUnknownSize(plist.getKeys(prefix),
                    Spliterator.ORDERED), false);
            stream.forEach(System.out::println);
            return List.of();
        } catch (ConfigurationException e) {
            throw new RuntimeException(e);
        }
    }
}
