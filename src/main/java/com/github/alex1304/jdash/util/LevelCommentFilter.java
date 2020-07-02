package com.github.alex1304.jdash.util;

public enum LevelCommentFilter {
    RECENT("0"),
    MOST_LIKED("1");

    private final String val;

    LevelCommentFilter(String val){
        this.val = val;
    }

    public String getVal(){
        return val;
    }
}
