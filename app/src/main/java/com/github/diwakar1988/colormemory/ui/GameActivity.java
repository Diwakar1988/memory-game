package com.github.diwakar1988.colormemory.ui;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.diwakar1988.colormemory.R;

import com.github.diwakar1988.colormemory.game.GameController;
import com.github.diwakar1988.colormemory.game.ScoreList;
import com.github.diwakar1988.colormemory.ui.fragment.GameBoardFragment;
import com.github.diwakar1988.colormemory.ui.fragment.GameHeaderFragment;

public class GameActivity extends GameBaseActivity implements View.OnClickListener,GameBoardFragment.GameUpdatesListener {

    private GameHeaderFragment mHeaderFragment;
    private GameBoardFragment mGameBoardFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);


        mHeaderFragment = GameHeaderFragment.newInstance(this);
        mGameBoardFragment=GameBoardFragment.newInstance(this);


        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.header_container,mHeaderFragment);
        ft.replace(R.id.board_container,mGameBoardFragment);
        ft.commit();



    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btn_high_score:
                startActivity(HighScoreActivity.createIntent(this));break;
        }
    }

    @Override
    public void onGameScoreChanged() {
        mHeaderFragment.setScore(GameController.getInstance().getCurrentScore());
//        Log.d("Board",GameController.getInstance().printBoard());
    }

    @Override
    public void onGameFinished() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_input);
        dialog.findViewById(R.id.btnSubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText et = (EditText) dialog.findViewById(R.id.et_value);
                String name = et.getText().toString();
                if (TextUtils.isEmpty(name)){
                    Toast.makeText(GameActivity.this, "Invalid name!", Toast.LENGTH_SHORT).show();
                    return;
                }
                dialog.dismiss();

                savePlayerAndResetGame(name);
            }
        });
        dialog.show();
    }

    private void savePlayerAndResetGame(String name) {
        ScoreList.Score score = GameController.getInstance().save(name);

        StringBuilder sb = new StringBuilder();
        sb.append("Your High Score= "+score.getScore());
        sb.append('\n');
        sb.append("Your Current Rank= "+score.getRank());

        final Dialog dialog = new Dialog(this);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.dialog_message);

        ((TextView)dialog.findViewById(R.id.tv_message)).setText(sb.toString());

        dialog.findViewById(R.id.btn_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                GameController.getInstance().resetGame();
                mHeaderFragment.resetScore();
                mGameBoardFragment.setUp();

            }
        });
        dialog.show();

        sb.setLength(0);
        sb=null;



    }
}
