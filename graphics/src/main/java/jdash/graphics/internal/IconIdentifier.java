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

    public static IconIdentifier fromName(String baseName) {
        final var split = baseName.replace("player_ball", "ball").split("_", 3);
        IconType type;
        switch (split[0]) {
            case "player":
                type = IconType.CUBE; break;
            case "ship":
                type = IconType.SHIP; break;
            case "bird":
                type = IconType.UFO; break;
            case "ball":
                type = IconType.BALL; break;
            case "dart":
                type = IconType.WAVE; break;
            case "robot":
                type = IconType.ROBOT; break;
            case "spider":
                type = IconType.SPIDER; break;
            case "swing":
                type = IconType.SWING; break;
            default:
                type = IconType.UNKNOWN;
        }
        return new IconIdentifier(type, Integer.parseInt(split[1]), split.length > 2 ? split[2] : "");
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
