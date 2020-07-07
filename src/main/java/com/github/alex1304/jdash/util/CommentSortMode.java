package com.github.alex1304.jdash.util;

public enum CommentSortMode {
    RECENT("0"),
    MOST_LIKED("1");

    private final String val;

    CommentSortMode(String val){
        this.val = val;
    }

    public String getVal(){
        return val;
    }
}
