package com.github.diwakar1988.colormemory.db;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import com.github.diwakar1988.colormemory.MemoryGame;
import com.google.gson.Gson;

import java.util.LinkedList;

/**
 * Created by diwakar.mishra on 12/10/16.
 */
public class GamePreferences {
    private static final String TAG = GamePreferences.class.getSimpleName();
    private static final String NAME = "pref_colour_memory";
    private static GamePreferences _instance;

    protected static final String KEY_SCORE = "key_score";


    private SharedPreferences preferences;

    private GamePreferences() {
        preferences = MemoryGame.getInstance().getSharedPreferences(
                NAME, Context.MODE_PRIVATE);
    }

    static GamePreferences getInstance() {
        if (_instance == null) {
            _instance = new GamePreferences();
        }
        return _instance;
    }


    /**
     * This Method Clear shared preference.
     */
    protected void clear() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
    }

    protected void commit() {
        preferences.edit().commit();
    }

    private void setString(String key, String value) {
        if (key != null && value != null) {
            try {
                if (preferences != null) {
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString(key, value);
                    editor.commit();
                }
            } catch (Exception e) {
                Log.e(TAG, "Unable to set " + key + "= " + value
                        + "in shared preference", e);
            }
        }
    }


    private String getString(String key, String defaultValue) {
        if (preferences != null && key != null && preferences.contains(key)) {
            return preferences.getString(key, defaultValue);
        }
        return defaultValue;
    }

    void saveScoreList(ScoreList list){
        if (list==null){
            return;
        }
        String str = new Gson().toJson(list, ScoreList.class);
        setString(KEY_SCORE,str);
    }
    ScoreList getScoreList(){
        String str = getString(KEY_SCORE,"");
        ScoreList list=null;
        if (TextUtils.isEmpty(str)){
            list=new ScoreList();
        }else{
            list = new Gson().fromJson(str,ScoreList.class);
        }
        return list;
    }

    /**
     * Created by diwakar.mishra on 12/10/16.
     */

    public static class ScoreList {
        private LinkedList<Score> list=new LinkedList<>();

        public void add(Score score){
            if (!list.contains(score)){
                list.add(score);
            }
        }
        public int size(){
            return list.size();
        }
        public Score getScore(int position){
            return list.get(position);
        }
        public static class Score{
            public int rank;
            public String name;
            public int score;

            @Override
            public int hashCode() {
                return name.hashCode();
            }

            @Override
            public boolean equals(Object obj) {
                if (this==obj){
                    return true;
                }if (!(obj instanceof Score)){
                    return false;
                }

                return ((Score)obj).name.equals(name);
            }
        }
    }
}
