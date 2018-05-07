package com.dreamsofpines.mcunost.ui.animation;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

import com.dreamsofpines.mcunost.R;
import com.dreamsofpines.mcunost.ui.customView.MyImageView;

/**
 * Created by ThePupsick on 15.03.2018.
 */

public class ResizableWidthAnimation extends Animation {
    private int mWidth;
    private int mStartWidth;
    private int idDr;
    private int idColor;
    private View mView;
    private Context context;

    public ResizableWidthAnimation(View view, int width, Context context) {
        mView = view;
        mWidth = width;
        mStartWidth = view.getWidth();
        idDr = ((MyImageView) mView).isFull()? R.drawable.rect:R.drawable.rect2;
        idColor = ((MyImageView) mView).isFull()?R.color.colorBlueToolbar:R.color.md_orange_400;
        this.context = context;
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        int newWidth = mStartWidth + (int) ((mWidth - mStartWidth) * interpolatedTime);
        ((MyImageView) mView).setImageResource(idColor);
        ((MyImageView) mView).setShape(context.getDrawable(idDr));
        mView.getLayoutParams().width = newWidth;
        mView.requestLayout();
    }


    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
    }

    @Override
    public boolean willChangeBounds() {
        return true;
    }
}




