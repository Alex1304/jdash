package jdash.common;

public enum Difficulty {
    NA(-1),
    AUTO(-2),
    EASY(1),
    NORMAL(2),
    HARD(3),
    HARDER(4),
    INSANE(5),
    DEMON(-3);

    private final int val;

    Difficulty(int val) {
        this.val = val;
    }

    public int getVal() {
        return val;
    }

    public static Difficulty parse(String str) {
        switch (str) {
            case "10":
                return Difficulty.EASY;
            case "20":
                return Difficulty.NORMAL;
            case "30":
                return Difficulty.HARD;
            case "40":
                return Difficulty.HARDER;
            case "50":
                return Difficulty.INSANE;
            default:
                return Difficulty.NA;
        }
    }
}