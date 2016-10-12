package com.github.diwakar1988.colormemory.game;

import com.github.diwakar1988.colormemory.Configs;

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
    public void markTileOpened(int row,int col){
        board.markOpen(row,col);
    }
    public Board.Card getCard(int row, int col){
        return board.getCard(row,col);
    }


}
