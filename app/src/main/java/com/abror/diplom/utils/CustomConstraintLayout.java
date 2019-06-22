package com.abror.diplom.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.WindowInsets;

import androidx.constraintlayout.widget.ConstraintLayout;

public class CustomConstraintLayout extends ConstraintLayout {

    private int[] mInsets = new int[4];

    public CustomConstraintLayout(Context context) {
        super(context);
    }

    public CustomConstraintLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomConstraintLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public final WindowInsets onApplyWindowInsets(WindowInsets insets) {
        mInsets[0] = insets.getSystemWindowInsetLeft();
        mInsets[1] = insets.getSystemWindowInsetTop();
        mInsets[2] = insets.getSystemWindowInsetRight();
        return super.onApplyWindowInsets(insets.replaceSystemWindowInsets(0, 0, 0,
                insets.getSystemWindowInsetBottom()));
    }
}
