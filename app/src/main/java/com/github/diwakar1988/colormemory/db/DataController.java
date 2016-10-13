package com.github.diwakar1988.colormemory.db;

import com.github.diwakar1988.colormemory.game.ScoreList;

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
    private ScoreList scoreList;

    private DataController(){
        scoreList = GamePreferences.getInstance().getScoreList();
    }

    public ScoreList getScoreList() {
        return scoreList;
    }
    public void saveScoreList(){
        GamePreferences.getInstance().saveScoreList(scoreList);
    }
}
