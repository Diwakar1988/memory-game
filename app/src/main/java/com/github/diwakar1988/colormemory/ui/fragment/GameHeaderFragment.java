package com.github.diwakar1988.colormemory.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.diwakar1988.colormemory.R;


public class GameHeaderFragment extends Fragment {


    private View root;
    private  View.OnClickListener mClickListener;

    public static GameHeaderFragment newInstance(View.OnClickListener clickListener) {
        GameHeaderFragment fragment = new GameHeaderFragment();
        fragment.mClickListener=clickListener;
        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_game_header, container, false);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        root.findViewById(R.id.btn_high_score).setOnClickListener(mClickListener);

    }
    public void setScore(int score){
        ((TextView)root.findViewById(R.id.tv_score)).setText("Score = "+String.valueOf(score));
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mClickListener = null;
    }

}
