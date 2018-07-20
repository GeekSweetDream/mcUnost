package com.dreamsofpines.mcunost.ui.customView;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.dreamsofpines.mcunost.R;
import com.wang.avi.AVLoadingIndicatorView;

public class LoadingView extends RelativeLayout {

    public LoadingView(Context context) {
        super(context);
        init(context);
    }

    public LoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public LoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private ImageView back;
    private AVLoadingIndicatorView avl;

    private void init(Context context){
        inflate(context, R.layout.loading_view,this);
        bindView();
        setClickable(false);
        setVisibility(GONE);
    }

    private void bindView() {
        back = (ImageView) findViewById(R.id.img_black);
        avl = (AVLoadingIndicatorView) findViewById(R.id.avl);
    }


    public void show(){
        changeStat(true);
        avl.show();
    }

    public void hide(){
        changeStat(false);
        avl.hide();
    }


    private void changeStat(boolean visible){
        setVisibility(visible?VISIBLE:GONE);
        setClickable(visible);
    }


}


