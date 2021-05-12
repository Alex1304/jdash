package jdash.common;

public enum DemonDifficulty {
    EASY,
    MEDIUM,
    HARD,
    INSANE,
    EXTREME;

    public static DemonDifficulty parse(String str) {
        switch (str) {
            case "3":
                return DemonDifficulty.EASY;
            case "4":
                return DemonDifficulty.MEDIUM;
            case "5":
                return DemonDifficulty.INSANE;
            case "6":
                return DemonDifficulty.EXTREME;
            default:
                return DemonDifficulty.HARD;
        }
    }
}