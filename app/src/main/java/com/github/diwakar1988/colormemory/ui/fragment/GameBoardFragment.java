package com.github.diwakar1988.colormemory.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.github.diwakar1988.colormemory.Configs;
import com.github.diwakar1988.colormemory.R;
import com.github.diwakar1988.colormemory.game.Board;
import com.github.diwakar1988.colormemory.game.GameController;
import com.github.diwakar1988.colormemory.ui.view.CardImageView;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class GameBoardFragment extends Fragment implements View.OnClickListener, Animation.AnimationListener {
    private static final String TAG = GameBoardFragment.class.getSimpleName();

    public static GameBoardFragment newInstance(GameUpdatesListener listener) {
        GameBoardFragment fragment = new GameBoardFragment();
        fragment.listener=listener;
        return fragment;
    }

    private GameUpdatesListener listener;
    private TableLayout tableLayout;
    private LayoutInflater inflater;
    private Animation animation1;
    private Animation animation2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.inflater=inflater;
        tableLayout= (TableLayout) inflater.inflate(R.layout.fragment_game_board, container, false);
        return tableLayout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setUp();

        animation1 = AnimationUtils.loadAnimation(getContext(), R.anim.to_mid);
        animation1.setAnimationListener(this);
        animation2 = AnimationUtils.loadAnimation(getContext(), R.anim.from_mid);
        animation2.setAnimationListener(this);

    }

    public void setUp(){
        civPair.clear();
        tableLayout.removeAllViews();
        GameController gameController = GameController.getInstance();


        for (int row = 0; row < Configs.BOARD_ROW_COL; row++) {
            TableRow tr = new TableRow(getContext());
            tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));

            for (int col = 0; col < Configs.BOARD_ROW_COL; col++) {
                Board.Card card =  gameController.getCard(row,col);
                CardImageView tiv = (CardImageView) inflater.inflate(R.layout.tile_view,null);
                tiv.setImageResource(R.drawable.card_bg);

                //below line is for testing only
//                tiv.setImageResource(getDrawableId(card.getColorResource()));

                tr.addView(tiv);
                tiv.row=row;
                tiv.col=col;
                tiv.setOnClickListener(this);
            }

            tableLayout.addView(tr);
        }

    }
    private ArrayList<CardImageView> civPair=new ArrayList<>(2);

    private CardImageView getCurrentCardImageView(){
        if (civPair.size()==1){
            return civPair.get(0);
        }
        if (civPair.size()==2){
            return civPair.get(1);
        }
        return null;
    }



    private boolean isAdded(CardImageView civ) {
        for (CardImageView c :
                civPair) {

            if (c.row==civ.row && c.col==civ.col){
                return true;
            }
        }
        return false;
    }

    private int getDrawableId(String name){
        int drawableId =-1;
        try {
            Class res = R.drawable.class;
            Field field = res.getField(name);
            drawableId = field.getInt(null);

        }
        catch (Exception e) {
            Log.e(TAG, "****** Failure to get drawable id.", e);
        }
        return drawableId;
    }

    @Override
    public void onClick(View v) {

        if (!isAnimating && !updatingScore && v instanceof CardImageView){

            if (civPair.size()==2){
                civPair.clear();
            }
            CardImageView civ = (CardImageView) v;
            if (isAdded(civ) || GameController.getInstance().isCardOpened(civ.row,civ.col)){
                return;
            }
            civPair.add(civ);


            isAnimating=true;
            civ.clearAnimation();
            civ.startAnimation(animation1);


        }
    }
    @Override
    public void onAnimationStart(Animation animation) {
        isAnimating=true;
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        CardImageView civ = getCurrentCardImageView();
        if (animation==animation1) {
            if (civ.isBackOfCardShowing) {
                Board.Card card = GameController.getInstance().getCard(civ.row,civ.col);
                civ.setImageResource(getDrawableId(card.getColorResource()));
                lookupForPlayerMove();
            } else {
                civ.setImageResource(R.drawable.card_bg);
            }
            civ.clearAnimation();
            civ.startAnimation(animation2);

        } else {
            civ.isBackOfCardShowing=!civ.isBackOfCardShowing;
        }
        isAnimating=false;
    }

    private void lookupForPlayerMove() {
        if (civPair.size()==2){
            GameController gameController=GameController.getInstance();
            Board.Card card1 = gameController.getCard(civPair.get(0).row, civPair.get(0).col);
            Board.Card card2 = gameController.getCard(civPair.get(1).row, civPair.get(1).col);

            boolean increaseScore=false;
            if (card1.getColorResource().equalsIgnoreCase(card2.getColorResource())){
                gameController.markCardOpened(card1.getRow(),card1.getCol());
                gameController.markCardOpened(card2.getRow(),card2.getCol());
                increaseScore=true;
            }

            //After each round, a brief one (1) second pause should be implemented before scoring to allow the
            //player to see what the second selected card is.
            updatingScore =true;
            final  boolean score = increaseScore;
            tableLayout.postDelayed(new Runnable() {
                @Override
                public void run() {
                   updateScore(score);
                    updatingScore =false;
                }
            },1000);
        }
    }

    private boolean updatingScore;
    private boolean isAnimating;
    private void updateScore(boolean increase) {
        GameController gameController=GameController.getInstance();
        if (increase){
            gameController.increaseGameScore();
            civPair.get(0).setVisibility(View.INVISIBLE);
            civPair.get(1).setVisibility(View.INVISIBLE);

        }else{
            gameController.decreaseGameScore();
            civPair.get(0).isBackOfCardShowing=true;
            civPair.get(1).isBackOfCardShowing=true;
            civPair.get(0).setImageResource(R.drawable.card_bg);
            civPair.get(1).setImageResource(R.drawable.card_bg);
        }

        listener.onGameScoreChanged();

        //look for game finish
        if (increase && gameController.isGameFinished()){
            listener.onGameFinished();
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    @Override
    public void onDetach() {
        super.onDetach();
        civPair.clear();
        civPair=null;
        animation1=null;
        animation2=null;
        listener=null;
    }
    public static interface GameUpdatesListener {
        public void onGameScoreChanged();
        public void onGameFinished();

    }

}
