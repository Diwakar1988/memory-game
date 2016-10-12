package com.github.diwakar1988.colormemory;

import android.app.Application;

import com.github.diwakar1988.colormemory.db.DataController;
import com.github.diwakar1988.colormemory.game.GameController;

/**
 * Created by diwakar.mishra on 12/10/16.
 */

public class MemoryGame extends Application {
    private static MemoryGame instance;
    public static MemoryGame getInstance(){
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
        DataController.init();
        GameController.init();
    }
}
