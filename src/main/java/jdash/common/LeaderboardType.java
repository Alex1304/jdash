package jdash.common;

public enum LeaderboardType {
    TOP100("top"),
    FRIENDS("friends"),
    GLOBAL("relative"),
    CREATORS("creators");

    private final String val;

    LeaderboardType(String val) {
        this.val = val;
    }

    public String getVal() {
        return val;
    }
}