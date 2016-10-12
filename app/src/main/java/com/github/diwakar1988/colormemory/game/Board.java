package com.github.diwakar1988.colormemory.game;

import com.github.diwakar1988.colormemory.Configs;

import java.util.Random;

/**
 * Created by diwakar.mishra on 12/10/16.
 */

public class Board {

    private static  final String COLOUR_PREFIX="colour";

    private Card[][] cards;

    public Board() {
        initialize();
    }

    public void reset(){
        initialize();
    }
    public void markOpen(int row,int col){
        Card card = cards[row][col];
        card.setState(Card.STATE_OPENED);
    }
    private void initialize(){
        cards =new Card[Configs.BOARD_ROW_COL][Configs.BOARD_ROW_COL];

        int totalColor = 2* Configs.BOARD_ROW_COL;
        int colorCount = 1;
        Random r=new Random();

        StringBuilder sb = new StringBuilder();
        int row;
        int col;
        while (colorCount<=totalColor){


            row=r.nextInt(Configs.BOARD_ROW_COL);
            col=r.nextInt(Configs.BOARD_ROW_COL);

            //check if color is assigned or not
            if(cards[row][col]==null){

                sb.append(COLOUR_PREFIX);
                sb.append(colorCount);
//                sb.append(".png");

                Card card =new Card(row,col,sb.toString());
                cards[row][col]= card;


                while (cards[row][col]!=null){
                    row=r.nextInt(Configs.BOARD_ROW_COL);
                    col=r.nextInt(Configs.BOARD_ROW_COL);
                }

                try {
                    cards[row][col]= (Card) card.clone();
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }


                colorCount++;
                sb.setLength(0);
            }

        }

        sb=null;
        r=null;
    }

    public boolean isFinished() {

        for (int r = 0; r< cards.length; r++){
            for (int c = 0; c < cards.length; c++) {
                if (cards[r][c].getState()!= Card.STATE_OPENED){
                    return false;
                }
            }
        }
        return true;
    }

    public Card getCard(int row, int col) {
        return cards[row][col];
    }

    public static class Card {

        public static final byte STATE_CLOSED=0;
        public static final byte STATE_OPENED=1;


        private int state;
        private int row;
        private int col;
        private String colorResource;

        Card(int row, int col, String value) {
            this.row = row;
            this.col = col;
            this.colorResource = value;
        }

        public String getColorResource() {
            return colorResource;
        }

        public int getRow() {
            return row;
        }

        public int getCol() {
            return col;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        @Override
        protected final Object clone() throws CloneNotSupportedException {
            Card t =  new Card(row,col, colorResource);
            t.state=this.state;
            return t;
        }
    }
}
