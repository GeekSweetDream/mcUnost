package com.dreamsofpines.mcunost.ui.customView;

import android.animation.LayoutTransition;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dreamsofpines.mcunost.R;


/**
 * Created by ThePupsick on 28.02.2018.
 */

public class SimpleCounter extends RelativeLayout  {
    private View rootView;
    private View plus;
    private View minus;
    private TextView valueTxt;
    private int minValue = Integer.MIN_VALUE;
    private int maxValue = Integer.MAX_VALUE;
    private onClickListener listener;

    public interface onClickListener{
        void onButtonClick(boolean plus);
    }

    public void setOnClickListener(onClickListener listener){
        this.listener = listener;
    }

    public SimpleCounter(Context context) {
        super(context);
        init(context);
    }

    public SimpleCounter(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SimpleCounter(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public SimpleCounter(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context){
        rootView = inflate(context, R.layout.simple_counter, this);
        minus = rootView.findViewById(R.id.minusButton);
        plus = rootView.findViewById(R.id.plusButton);
        valueTxt = (TextView) rootView.findViewById(R.id.valueLabel);
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decrementValue();

            }
        });

        plus.setOnClickListener((v)-> {
                incrementValue();
        });

        plus.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:{
                        plus.setAlpha((float)0.5);
                        break;
                    }
                    case MotionEvent.ACTION_UP:{
                        plus.setAlpha((float)1);
                        break;
                    }
                }
                return false;
            }
        });

        minus.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:{
                        minus.setAlpha((float)0.5);
                        break;
                    }
                    case MotionEvent.ACTION_UP:{
                        minus.setAlpha((float)1);
                        break;
                    }
                }
                return false;
            }
        });

    }

    private void incrementValue() {
        int currentVal = Integer.valueOf(valueTxt.getText().toString());
        if(currentVal < maxValue) {
            valueTxt.setText(String.valueOf(currentVal + 1));
            if(listener != null) {
                listener.onButtonClick(true);
            }
        }
    }

    private void decrementValue() {
        int currentVal = Integer.valueOf(valueTxt.getText().toString());
        if(currentVal > minValue) {
            valueTxt.setText(String.valueOf(currentVal - 1));
            if(listener != null) {
                listener.onButtonClick(false);
            }
        }
    }

    public int getValue() {
        return Integer.parseInt(valueTxt.getText().toString());
    }

    public void setValue(int newValue) {
        int value = newValue;
        if(newValue < minValue) {
            value = minValue;
        } else if (newValue > maxValue) {
            value = maxValue;
        }
        valueTxt.setText(String.valueOf(value));
    }


    public int getMinValue() {
        return minValue;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }
}
