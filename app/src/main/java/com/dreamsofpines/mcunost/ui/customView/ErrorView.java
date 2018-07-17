package com.dreamsofpines.mcunost.ui.customView;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.dreamsofpines.mcunost.R;

public class ErrorView extends LinearLayout {
    public ErrorView(Context context) {
        super(context);
        init(context);
    }

    public ErrorView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ErrorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private Button resend;
    private OnResendButtonClickListener listener;

    public interface OnResendButtonClickListener{
        void onClick();
    }

    public void setOnResendButtonClickListener(OnResendButtonClickListener listener){
        this.listener = listener;
    }

    private void init(Context context){
        resend = (Button) findViewById(R.id.category_resend_butt);
        resend.setOnClickListener((view1 -> {
            if(listener!=null) listener.onClick();
        }));
    }

}
