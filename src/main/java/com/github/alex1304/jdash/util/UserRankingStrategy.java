package com.github.alex1304.jdash.util;

public enum UserRankingStrategy {
    TOP100("top"),
    FRIENDS("friends"),
    GLOBAL("relative"),
    CREATORS("creators");

    private final String val;

    UserRankingStrategy(String val) {
        this.val = val;
    }

    public String getVal() {
        return val;
    }
}
