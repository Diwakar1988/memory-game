package com.github.diwakar1988.colormemory.db;

/**
 * Created by diwakar.mishra on 12/10/16.
 */

public class DataController {

    private static DataController instance;

    public static DataController init() {
        instance=new DataController();
        return instance;
    }
    public static DataController getInstance() {
        return instance;
    }
    private GamePreferences.ScoreList scoreList;

    private DataController(){
        scoreList = GamePreferences.getInstance().getScoreList();
    }

    public GamePreferences.ScoreList getScoreList() {
        return scoreList;
    }
}
