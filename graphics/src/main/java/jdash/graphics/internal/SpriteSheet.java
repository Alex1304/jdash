package jdash.graphics.internal;

import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.configuration2.plist.XMLPropertyListConfiguration;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public final class SpriteSheet {

    private final BufferedImage image;
    private final XMLPropertyListConfiguration plist;
    private final List<SpriteElement> spriteElements;

    private SpriteSheet(BufferedImage image, XMLPropertyListConfiguration plist,
                        List<SpriteElement> spriteElements) {
        this.image = image;
        this.plist = plist;
        this.spriteElements = spriteElements;
    }

    public static SpriteSheet parse(String pngName, String plistName) {
        Objects.requireNonNull(pngName);
        Objects.requireNonNull(plistName);
        try (final var resource = SpriteSheet.class.getResourceAsStream(pngName)) {
            if (resource == null) {
                throw new MissingResourceException("PNG resource not found", SpriteSheet.class.getName(), pngName);
            }
            final var image = ImageIO.read(resource);
            final var plist = new Configurations()
                    .fileBased(XMLPropertyListConfiguration.class, SpriteSheet.class
                            .getResource(plistName));
            final var stream = StreamSupport.stream(Spliterators.spliteratorUnknownSize(plist.getKeys(),
                    Spliterator.ORDERED), false);
            final var mappings = stream.filter(key -> key.startsWith("frames."))
                    .map(key -> key.substring(7).split("\\.\\" + ".png\\.", -1))
                    .collect(Collectors.groupingBy(s -> s[0]));
            final var spriteElements = mappings.entrySet().stream()
                    .map(entry -> SpriteElement.from(entry.getKey(), entry.getValue().stream()
                            .filter(field -> field.length >= 2)
                            .map(field -> Map.entry(field[1], plist
                                    .getString("frames." + field[0] + "..png." + field[1], "null")))
                            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))))
                    .toList();
            return new SpriteSheet(image, plist, spriteElements);
        } catch (ConfigurationException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public BufferedImage getImage() {
        return image;
    }

    public XMLPropertyListConfiguration getPlist() {
        return plist;
    }

    public List<SpriteElement> getSpriteElements() {
        return spriteElements;
    }
}
