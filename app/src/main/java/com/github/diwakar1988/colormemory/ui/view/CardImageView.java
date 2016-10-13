package com.github.diwakar1988.colormemory.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by diwakar.mishra on 12/10/16.
 */

public class CardImageView extends ImageView {
    public int row;
    public int col;
    public boolean isBackOfCardShowing = true;
    public CardImageView(Context context) {
        super(context);
    }

    public CardImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CardImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


}
