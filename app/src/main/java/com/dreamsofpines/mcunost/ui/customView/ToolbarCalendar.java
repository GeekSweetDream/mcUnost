package com.dreamsofpines.mcunost.ui.customView;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dreamsofpines.mcunost.R;
import com.dreamsofpines.mcunost.data.utils.CalendarUtils;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;

public class ToolbarCalendar extends Toolbar {
    public ToolbarCalendar(Context context) {
        super(context);
        init(context);
    }

    public ToolbarCalendar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ToolbarCalendar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private HorizontalScrollView mScrollView;
    private TextView monthTitle;
    private ImageView btnLeft;
    private ImageView btnRight;
    private int layout[] = new int[2];
    private Calendar currentDate = Calendar.getInstance();
    private Calendar userChangerDate = (Calendar) currentDate.clone();
    private HashSet<Calendar> list;

    private void init(Context context){
        inflate(context, R.layout.view_toolbar_calendar,this);
        bindView();
        settingView(context);
        mScrollView.addView(createLentaDate(context));
    }

    private void bindView(){
        monthTitle = (TextView) findViewById(R.id.month_title);
        mScrollView = (HorizontalScrollView) findViewById(R.id.horizontal_scroll);
        btnLeft = (ImageView) findViewById(R.id.btn_left);
        btnRight = (ImageView) findViewById(R.id.btn_right);
    }

    private void settingView(Context context){
        btnLeft.setOnClickListener((view)->{
            updateView(context,-1);
        });
        btnRight.setOnClickListener((view)->{
            updateView(context,1);
        });
    }

    private void updateView(Context context,int i){
        userChangerDate.add(Calendar.MONTH,i);
        mScrollView.removeAllViews();
        mScrollView.addView(createLentaDate(context));
        updateEvent();
    }

    private LinearLayout createLentaDate(Context context){

        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));

        monthTitle.setText(CalendarUtils.getMonth(userChangerDate.get(Calendar.MONTH)));
        int daysInMonth = currentDate.getActualMaximum(Calendar.DAY_OF_MONTH);

        Calendar month = (Calendar) userChangerDate.clone();


        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity=Gravity.CENTER_VERTICAL;

        month.set(Calendar.DAY_OF_MONTH,0);
        for(int i = 0; i < daysInMonth; ++i){
            linearLayout.addView(createDayView(context,i,month),i,params);
            month.add(Calendar.DAY_OF_MONTH,1);
        }
        return linearLayout;
    }

    private void updateEvent(){
      LinearLayout linearLayout = (LinearLayout) mScrollView.getChildAt(0);
      for(int i = 0; i < linearLayout.getChildCount();++i){
          DayView dayView = (DayView) linearLayout.getChildAt(i);
          if(compareTwoDate(dayView)) dayView.showDot();
      }
      invalidate();
    }

    private DayView createDayView(Context context,int number,Calendar month){
        DayView dayView = new DayView(context);
        dayView.setDayName(CalendarUtils.getDayName(month.get(Calendar.DAY_OF_WEEK)-1));
        dayView.setDayNumber(number+1);
        if(isCurentDate(number,month)) dayView.setCurrentDay(true);
        return dayView;
    }

    private boolean isCurentDate(int number,Calendar month){
        return (number == currentDate.get(Calendar.DAY_OF_MONTH)-1) &&
                (currentDate.get(Calendar.MONTH)==month.get(Calendar.MONTH));
    }

    public void setDateOrders(HashSet<Calendar> list){
        this.list = list;
        updateEvent();
    }

    public boolean compareTwoDate(DayView dayView){
        if(list==null) return false;
        return list.contains(new GregorianCalendar(userChangerDate.get(Calendar.YEAR),
                userChangerDate.get(Calendar.MONTH),dayView.getDayNumber()));
    }

    public void scrollToCurrentDate(){
//        ((LinearLayout)mScrollView.getChildAt(0)).getChildAt(currentDate.get(Calendar.DAY_OF_MONTH)-1).getLocationOnScreen(layout);
//        ((LinearLayout)mScrollView.getChildAt(0)).getChildAt(currentDate.get(Calendar.DAY_OF_MONTH)-1).getLocationOnScreen(layout);
//        Log.i("position","x: " + layout[0]+" y: "+layout[1]);
//        mScrollView.scrollTo(layout[0],layout[1]);
    }
}
