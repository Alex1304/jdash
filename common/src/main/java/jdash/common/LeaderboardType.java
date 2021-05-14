package jdash.common;

public enum LeaderboardType {
    TOP100("top"),
    FRIENDS("friends"),
    GLOBAL("relative"),
    CREATORS("creators");

    private final String value;

    LeaderboardType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}