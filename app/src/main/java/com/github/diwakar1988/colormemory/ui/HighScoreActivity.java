package com.github.diwakar1988.colormemory.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.github.diwakar1988.colormemory.R;
import com.github.diwakar1988.colormemory.db.DataController;
import com.github.diwakar1988.colormemory.db.GamePreferences;

public class HighScoreActivity extends GameBaseActivity {

    public static Intent createIntent(Context context){
        Intent i = new Intent(context,HighScoreActivity.class);
        return  i;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);
        setUpTitleBar(getString(R.string.high_score),true);

        createScoreTable();
    }

    private void createScoreTable() {

        GamePreferences.ScoreList list = DataController.getInstance().getScoreList();

        TableLayout tl = (TableLayout) findViewById(R.id.table_layout);

        for (int i = 0; i < list.size(); i++) {
            GamePreferences.ScoreList.Score s = list.getScore(i);
            tl.addView(createTableRow(s));
        }


    }

    private TableRow createTableRow(GamePreferences.ScoreList.Score s) {

        TableRow tr = new TableRow(this);
        tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
        tr.setPadding(dpToPx(5),dpToPx(10),dpToPx(5),dpToPx(10));
//        tr.setBackgroundColor(0X);

        TableRow.LayoutParams param = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT);
        param.weight=1;

        TextView tv = new TextView(this);
        tv.setLayoutParams(param);
        tv.setGravity(Gravity.CENTER);
        tv.setTextColor(getResources().getColor(R.color.black));
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP,15);
        tv.setText(String.valueOf(s.rank));
        tr.addView(tv);

        tv = new TextView(this);
        tv.setLayoutParams(param);
        tv.setGravity(Gravity.CENTER);
        tv.setTextColor(getResources().getColor(R.color.black));
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP,15);
        tv.setText(String.valueOf(s.name));
        tr.addView(tv);

        tv = new TextView(this);
        tv.setLayoutParams(param);
        tv.setGravity(Gravity.CENTER);
        tv.setTextColor(getResources().getColor(R.color.black));
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP,15);
        tv.setText(String.valueOf(s.score));
        tr.addView(tv);


        return tr;
    }

    private GamePreferences.ScoreList createDummyScores(int max) {
        int highScore=1000;
        GamePreferences.ScoreList list =new GamePreferences.ScoreList();
        for (int i=1;i<=max;i++){
            GamePreferences.ScoreList.Score s = new GamePreferences.ScoreList.Score();
            s.rank=i;
            s.name="Player "+(i);
            s.score=highScore-(100*i);
            list.add(s);
        }
        return list;
    }

    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }
}
