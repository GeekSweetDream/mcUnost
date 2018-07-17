package com.dreamsofpines.mcunost.ui.customView;

import android.content.Context;
import android.os.Handler;
import android.transition.ChangeBounds;
import android.transition.Fade;
import android.transition.TransitionManager;
import android.transition.TransitionSet;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.dreamsofpines.mcunost.R;
import com.dreamsofpines.mcunost.data.utils.ScreenUtils;


public class ViewFragmentPattern extends RelativeLayout {
    public ViewFragmentPattern(Context context) {
        super(context);
        init(context);
    }

    public ViewFragmentPattern(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ViewFragmentPattern(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private ViewItemConstructor item;
    private LinearLayout linearLayout;
    private RelativeLayout scene;
    private RelativeLayout actor;
    private View back;
    private float beginX = 0,beginY = 0;
    private boolean isShow = false;
    private OnBackgroundClickListener listener;

    public void setOnBackgroundClickListener(OnBackgroundClickListener listener){
        this.listener = listener;
    }

    public interface OnBackgroundClickListener{
        void OnClick();
    }

    private void init(Context context){
        inflate(context, R.layout.view_fragment_pattern,this);
        item = (ViewItemConstructor) findViewById(R.id.toolbar);
        linearLayout = (LinearLayout) findViewById(R.id.field);
        scene = (RelativeLayout) findViewById(R.id.scene);
        actor = (RelativeLayout) findViewById(R.id.rel);
        back = (View) findViewById(R.id.back);
        back.setOnClickListener((view -> {
            if(listener!=null)listener.OnClick();
        }));
    }

    public void setTextInToolbar(String text){
        item.setText(text);
    }

    public void setTitleInToolbar(String title){
        item.setTitle(title);
    }

    public void cleanTextInToolbar(){
        item.cleanText();
    }

    public void setIconInToolbar(String path){
        item.setIcon(path);
    }

    public void setMargin(int dp){
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)actor.getLayoutParams();
        params.topMargin = ScreenUtils.getDpFromPx(dp,getContext());
        actor.setLayoutParams(params);
    }

    public void setViewInLayout(View view){
        view.setVisibility(GONE);
        linearLayout.addView(view,linearLayout.getChildCount());
    }

    public void setVisibleIcon(boolean visibleIcon){
        item.setVisibleIcon(visibleIcon);
    }

    public void setPositionX(float x){
        beginX = x;
        actor.setX(x);
    }

    public void setPositionY(float y){
        beginY = y;
        setMarginActorTop(y);
//        actor.setY(y);
    }

    public void showDown(){
        animatingDown();
        isShow = true;
    }

    public void hideDown(){
        animatingDown();
        isShow = false;
    }

    public void show(){
        animating();
        isShow = true;
    }

    public void hide(){
        animating();
        isShow = false;
    }

    private void animating(){
        TransitionSet set = new TransitionSet();
        set.setDuration(300);
        set.addTransition(new ChangeBounds());
        TransitionManager.beginDelayedTransition((ViewGroup) scene, set);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) actor.getLayoutParams();
        background();
        int margin = isShow? 0 : ScreenUtils.getDpFromPx(30,getContext());

        if(!isShow) {
            setMarginActorTop(0);
            params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        }else{
            setMarginActorTop(beginY);
            params.removeRule(CENTER_IN_PARENT);
        }
        item.setMargins(margin, 0, margin, 0);
        item.setUseCompatPadding(!isShow);
        actor.setLayoutParams(params);
        for(int i = 1; i<linearLayout.getChildCount();++i){
            linearLayout.getChildAt(i).setVisibility(isShow?GONE:VISIBLE);
        }
    }

    private void animatingDown(){
        TransitionSet set = new TransitionSet();
        set.setDuration(300);
        set.addTransition(new ChangeBounds());
        TransitionManager.beginDelayedTransition((ViewGroup) scene, set);
        background();
        int margin = isShow? 0 : ScreenUtils.getDpFromPx(30,getContext());
        item.setUseCompatPadding(!isShow);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) linearLayout.getLayoutParams();
        params.setMargins(margin, 0, margin, 0);
        linearLayout.setLayoutParams(params);
        for(int i = 1; i<linearLayout.getChildCount();++i){
            linearLayout.getChildAt(i).setVisibility(isShow?GONE:VISIBLE);
        }

    }


    private void setMarginActorTop(float y){
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) actor.getLayoutParams();
        params.topMargin = (int) y;
        actor.setLayoutParams(params);
    }

    private void background(){
        TransitionSet set = new TransitionSet();
        set.setDuration(300);
        set.addTransition(new Fade());
        back.setVisibility(isShow?GONE:VISIBLE);
    }

}
