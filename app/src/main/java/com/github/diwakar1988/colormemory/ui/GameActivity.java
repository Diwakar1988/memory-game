package com.github.diwakar1988.colormemory.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.WindowManager;

import com.github.diwakar1988.colormemory.R;

import com.github.diwakar1988.colormemory.ui.fragment.GameBoardFragment;
import com.github.diwakar1988.colormemory.ui.fragment.GameHeaderFragment;

public class GameActivity extends GameBaseActivity implements View.OnClickListener{

    private GameHeaderFragment mHeaderFragment;
    private GameBoardFragment mGameBoardFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);


        mHeaderFragment = GameHeaderFragment.newInstance(this);
        mGameBoardFragment=GameBoardFragment.newInstance();


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
}
