package jdash.graphics.internal;

import ij.IJ;
import ij.ImagePlus;
import jdash.common.IconType;
import jdash.graphics.LegacySpriteFactory;
import jdash.graphics.SpriteFactory;
import jdash.graphics.exception.SpriteLoadException;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.configuration2.plist.XMLPropertyListConfiguration;

import java.awt.image.BufferedImage;
import java.util.Map;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public final class SpriteFactoryImpl22 implements SpriteFactory {

    private final ImagePlus imp;
    private final XMLPropertyListConfiguration spritePlist;

    private SpriteFactoryImpl22(ImagePlus imp, XMLPropertyListConfiguration spritePlist) {
        this.imp = imp;
        this.spritePlist = spritePlist;
    }

    /**
     * Creates a new sprite factory.
     *
     * @return the sprite factory
     * @throws SpriteLoadException if something goes wrong when loading the sprites.
     */
    public static SpriteFactory create() {
        try {
            var url = LegacySpriteFactory.class.getResource("/GJ_GameSheetIcons-hd.png");
            if (url == null) {
                throw new AssertionError("Game sheet URL is null");
            }
            final var imp = IJ.openImage(url.toString());
            var spritePlist = new Configurations()
                    .fileBased(XMLPropertyListConfiguration.class, SpriteFactoryImpl22.class
                            .getResource("/GJ_GameSheetIcons-hd.plist"));

            return new SpriteFactoryImpl22(imp, spritePlist);
        } catch (ConfigurationException e) {
            throw new SpriteLoadException(e);
        }
    }

    @Override
    public BufferedImage makeSprite(IconType type, int id, int color1Id, int color2Id, boolean withGlowOutline) {
        return null;
    }

    void test() {
        final var stream = StreamSupport.stream(Spliterators.spliteratorUnknownSize(spritePlist.getKeys(),
                Spliterator.ORDERED), false);
        final var mappings = stream.filter(key -> key.startsWith("frames."))
                .map(key -> key.substring(7).split("\\.\\" +".png\\.", -1))
                .collect(Collectors.groupingBy(s -> s[0]));
        final var values = mappings.entrySet().stream()
                .map(entry -> IconElement.from(entry.getKey(), entry.getValue().stream()
                        .filter(field -> field.length >= 2)
                        .map(field -> Map.entry(field[1], spritePlist
                                .getString("frames." + field[0] + "..png." + field[1], "null")))
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))))
                .collect(Collectors.toUnmodifiableList());
        System.out.println(values);
    }
}
