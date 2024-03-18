package jdash.graphics.internal;

import ij.IJ;
import ij.ImagePlus;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.configuration2.plist.XMLPropertyListConfiguration;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public final class GameSheetParser {

    private final ImagePlus image;
    private final XMLPropertyListConfiguration plist;
    private final List<SpriteElement> spriteElements;

    private GameSheetParser(ImagePlus image, XMLPropertyListConfiguration plist, List<SpriteElement> spriteElements) {
        this.image = image;
        this.plist = plist;
        this.spriteElements = spriteElements;
    }

    public static GameSheetParser parse(String pngName, String plistName) {
        Objects.requireNonNull(pngName);
        Objects.requireNonNull(plistName);
        try {
            final var url = GameSheetParser.class.getResource(pngName);
            if (url == null) {
                throw new MissingResourceException("PNG resource not found", GameSheetParser.class.getName(), pngName);
            }
            final var image = IJ.openImage(url.toString());
            final var plist = new Configurations()
                    .fileBased(XMLPropertyListConfiguration.class, GameSheetParser.class
                            .getResource("/GJ_GameSheetIcons-hd.plist"));
            final var stream = StreamSupport.stream(Spliterators.spliteratorUnknownSize(plist.getKeys(),
                    Spliterator.ORDERED), false);
            final var mappings = stream.filter(key -> key.startsWith("frames."))
                    .map(key -> key.substring(7).split("\\.\\" +".png\\.", -1))
                    .collect(Collectors.groupingBy(s -> s[0]));
            final var spriteElements = mappings.entrySet().stream()
                    .map(entry -> SpriteElement.from(entry.getKey(), entry.getValue().stream()
                            .filter(field -> field.length >= 2)
                            .map(field -> Map.entry(field[1], plist
                                    .getString("frames." + field[0] + "..png." + field[1], "null")))
                            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))))
                    .collect(Collectors.toUnmodifiableList());
            return new GameSheetParser(image, plist, spriteElements);
        } catch (ConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    public ImagePlus getImage() {
        return image;
    }

    public XMLPropertyListConfiguration getPlist() {
        return plist;
    }

    public List<SpriteElement> getSpriteElements() {
        return spriteElements;
    }
}
