package com.dreamsofpines.mcunost.ui.customView;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dreamsofpines.mcunost.R;

public class DayView extends RelativeLayout {
    public DayView(Context context) {
        super(context);
        init(context);
    }

    public DayView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public DayView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public DayView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private TextView dayName;
    private TextView dayNumber;
    private View dot;
    private RelativeLayout rel;

    private void init(Context context){
        inflate(context, R.layout.view_day_calendar,this);
        dayName = (TextView) findViewById(R.id.day);
        dayNumber = (TextView) findViewById(R.id.quantity_day);
        dot = (View) findViewById(R.id.dot);
        rel = (RelativeLayout) findViewById(R.id.rel);
    }

    public void setDayName(String text){
        dayName.setText(text);
    }

    public void setDayNumber(int number){
        dayNumber.setText(String.valueOf(number));
    }

    public int getDayNumber(){
        return Integer.valueOf(dayNumber.getText().toString());
    }


    public void showDot(){
        dot.setVisibility(VISIBLE);
    }

    public void hideDot(){
        dot.setVisibility(GONE);
    }

    public void setCurrentDay(boolean fl){
        if(fl){
            rel.setBackground(getResources().getDrawable(R.drawable.border_blue_stroke));
        }else{
            rel.setBackground(null);
        }
    }

}
