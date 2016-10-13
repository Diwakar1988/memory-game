package com.github.diwakar1988.colormemory.game;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

/**
 * Created by diwakar.mishra on 13/10/16.
 */

public class ScoreList {

    private LinkedList<Score> list=new LinkedList<>();

    public void add(Score score){
        if (!list.contains(score)){
            list.add(score);
        }
    }
    public int size(){
        return list.size();
    }
    public Score getScore(String playerName){
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getName().equalsIgnoreCase(playerName)){
                return list.get(i);
            }
        }
        return null;
    }
    public Score getScore(int position){
        return list.get(position);
    }

    public void updateRanking() {
        Collections.sort(list, new Comparator<Score>() {
            @Override
            public int compare(Score s1, Score s2) {
                if (s1.score<s2.score){
                    return 1;
                }
                if (s1.score>s2.score){
                    return -1;
                }
                return 0;
            }
        });
        for (int i = 0; i < list.size(); i++) {
            list.get(i).rank=i+1;
        }
    }

    public static class Score{
        private int rank;
        private String name;
        private int score;

        Score(String name, int score) {
            this.name = name;
            this.score = score;
        }

        public int getScore() {
            return score;
        }

        public String getName() {
            return name;
        }

        public int getRank() {
            return rank;
        }

        void setScore(int score) {
            this.score = score;
        }

        void setRank(int rank) {
            this.rank = rank;
        }

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

            return ((Score)obj).name.equalsIgnoreCase(name);
        }
    }
}
