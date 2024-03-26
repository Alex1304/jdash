package jdash.graphics.internal;

import jdash.common.IconType;

import java.util.Objects;

public final class IconIdentifier {

    private final IconType type;
    private final int id;
    private final String part;

    private IconIdentifier(IconType type, int id, String part) {
        this.type = type;
        this.id = id;
        this.part = part;
    }

    public static IconIdentifier of(IconType type, int id) {
        return new IconIdentifier(type, id, "");
    }

    public String toSpriteResourceName() {
        return "/icons/" + type.getInternalName() + "_" + String.format("%02d", id) + "-uhd.png";
    }
    public String toPlistResourceName() {
        return "/icons/" + type.getInternalName() + "_" + String.format("%02d", id) + "-uhd.plist";
    }

    public IconType getType() {
        return type;
    }

    public int getId() {
        return id;
    }

    public String getPart() {
        return part;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IconIdentifier that = (IconIdentifier) o;
        return id == that.id && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, id);
    }

    @Override
    public String toString() {
        return "IconIdentifier{" +
                "type=" + type +
                ", id=" + id +
                ", part='" + part + '\'' +
                '}';
    }
}
