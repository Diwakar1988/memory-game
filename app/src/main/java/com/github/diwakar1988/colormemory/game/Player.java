package com.github.diwakar1988.colormemory.game;

import android.text.TextUtils;

/**
 * Created by diwakar.mishra on 12/10/16.
 */

public class Player {
    public static final String UNKNOWN="unknown";
    private String name;

    private int score;

    public Player(){
        name=UNKNOWN;
    }
    public Player(String name) {
        this.name = name;
    }
    public boolean hasName(){
        return (!TextUtils.isEmpty(name) && !name.equals(UNKNOWN));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }
}
