package com.dreamsofpines.mcunost.ui.customView;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

import com.dreamsofpines.mcunost.R;
import com.wang.avi.AVLoadingIndicatorView;

import static com.yandex.metrica.impl.q.a.i;
import static com.yandex.metrica.impl.q.a.v;

/**
 * Created by ThePupsick on 11.03.2018.
 */

public class SuccsesBookView extends RelativeLayout {

    private View view;
    private AVLoadingIndicatorView avl;
    private RelativeLayout rl;
    private Context context;

    public SuccsesBookView(Context context) {
        super(context);
        init(context);
    }

    public SuccsesBookView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SuccsesBookView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public SuccsesBookView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context){
        view = (View) inflate(context, R.layout.view_succsesful_book,this);
        avl  = (AVLoadingIndicatorView) view.findViewById(R.id.success_indicator);
        rl   = (RelativeLayout) view.findViewById(R.id.rl_anim_succsesful);
        this.context = context;
        view.setVisibility(GONE);
    }

    public void show(){
        view.setVisibility(VISIBLE);
        avl.show();
    }

    public void stopping(){
        avl.hide();
        rl.setAnimation(AnimationUtils.loadAnimation(context,R.anim.jump_from_down));
        rl.animate();
        rl.setVisibility(VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                hide();
            }
        },2000);

    }

    public void errorStoping(){
        avl.hide();
        hide();
    }

    public void hide(){
        view.setVisibility(GONE);
    }


}
