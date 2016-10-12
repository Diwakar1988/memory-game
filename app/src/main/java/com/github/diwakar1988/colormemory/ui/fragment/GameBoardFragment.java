package com.github.diwakar1988.colormemory.ui.fragment;

import android.animation.Animator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import com.github.diwakar1988.colormemory.Configs;
import com.github.diwakar1988.colormemory.R;
import com.github.diwakar1988.colormemory.game.Board;
import com.github.diwakar1988.colormemory.game.GameController;
import com.github.diwakar1988.colormemory.ui.view.CardImageView;

import java.lang.reflect.Field;

public class GameBoardFragment extends Fragment implements View.OnClickListener, Animation.AnimationListener {
    private static final String TAG = GameBoardFragment.class.getSimpleName();

    public static GameBoardFragment newInstance() {
        GameBoardFragment fragment = new GameBoardFragment();
        return fragment;
    }


    private TableLayout tableLayout;
    private LayoutInflater inflater;
    private Animation animation1;
    private Animation animation2;
    private boolean isBackOfCardShowing = true;

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
        GameController gameController = GameController.getInstance();


        for (int row = 0; row < Configs.BOARD_ROW_COL; row++) {
            TableRow tr = new TableRow(getContext());
            tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));

            for (int col = 0; col < Configs.BOARD_ROW_COL; col++) {
                Board.Card card =  gameController.getCard(row,col);
                CardImageView tiv = (CardImageView) inflater.inflate(R.layout.tile_view,null);
                tiv.setImageResource(R.drawable.card_bg);
                tr.addView(tiv);
                tiv.row=row;
                tiv.col=col;
                tiv.setOnClickListener(this);
            }

            tableLayout.addView(tr);
        }

    }
    private CardImageView civ ;
    @Override
    public void onClick(View v) {

        if (v instanceof CardImageView){
            civ = (CardImageView) v;
//            Board.Card card = GameController.getInstance().getCard(civ.row,civ.col);

            civ.clearAnimation();
//            civ.setAnimation(animation1);
            civ.startAnimation(animation1);


//            Toast.makeText(getContext(), "Clicked: "+card.getColorResource(), Toast.LENGTH_SHORT).show();
        }
    }

    private int getDrableId(String name){
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
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        if (animation==animation1) {
            if (isBackOfCardShowing) {
                Board.Card card = GameController.getInstance().getCard(civ.row,civ.col);
                civ.setImageResource(getDrableId(card.getColorResource()));
            } else {
                civ.setImageResource(R.drawable.card_bg);
            }
            civ.clearAnimation();
//            civ.setAnimation(animation2);
            civ.startAnimation(animation2);

        } else {
            isBackOfCardShowing=!isBackOfCardShowing;
        }

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
