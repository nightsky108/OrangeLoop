package com.threeamstudios.theorangeloop;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class MyCustomTextView extends TextView {

    public MyCustomTextView(Context context, AttributeSet attrs) {

        super(context, attrs);
        this.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/Quicksand-Regular.otf"));

    }
}
