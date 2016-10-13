package com.github.diwakar1988.colormemory.game;

import com.github.diwakar1988.colormemory.Configs;
import com.github.diwakar1988.colormemory.db.DataController;

/**
 * Created by diwakar.mishra on 12/10/16.
 */

public class GameController {


    private static GameController instance;
    private Board board;
    private Player player;

    public static void init(){
        instance=new GameController();
    }
    public static GameController getInstance() {
        return instance;
    }

    private GameController() {
        board=new Board();
        player=new Player();

    }

    public void resetGame(){
        board.reset();
        player=new Player();

    }
    public void increaseGameScore(){
        int score=player.getScore();
        player.setScore(score+ Configs.POINT_HIT);
    }
    public void decreaseGameScore(){
        int score=player.getScore();
        player.setScore(score-Configs.POINT_MISS);

    }
    public boolean isGameFinished(){
        return board.isFinished();
    }
    public void markCardOpened(int row, int col){
        board.markOpen(row,col);
    }
    public Board.Card getCard(int row, int col){
        return board.getCard(row,col);
    }


    public int getCurrentScore() {
        return player.getScore();
    }

    public boolean isCardOpened(int row, int col) {
        return board.getCard(row,col).getState()== Board.Card.STATE_OPENED;
    }
    public ScoreList.Score save(String playerName){
        ScoreList list = DataController.getInstance().getScoreList();
        ScoreList.Score score= list.getScore(playerName);
        if (score==null){
            score=new ScoreList.Score(playerName,player.getScore());
            list.add(score);
        }else if (player.getScore()>score.getScore()){
            //always store high score of a player
            score.setScore(player.getScore());
        }
        list.updateRanking();
        DataController.getInstance().saveScoreList();
        return score;
    }

//    public String printBoard() {
//        return board.toString();
//    }
}
