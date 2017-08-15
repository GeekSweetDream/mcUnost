package com.dreamsofpines.mcunost.ui.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.dreamsofpines.mcunost.R;
import com.dreamsofpines.mcunost.data.storage.models.InformExcModel;

/**
 * Created by ThePupsick on 06.08.17.
 */

public class ExcurPagerAdapter extends PagerAdapter {
    private Context mContext;
    private TextView txt;
    private String cost,day,fullText,listExc;
    private Button mButton;
    public static OnClickButton mListener;

    public interface OnClickButton{
        void onClicked();
    }

    public void setOnClickListenner(OnClickButton onClickButton){
        mListener = onClickButton;
    }

    public ExcurPagerAdapter(Context contex){
        mContext = contex;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public void setFullText(String fullText) {
        this.fullText = fullText;
    }

    public void setListExc(String listExc) {
        this.listExc = listExc;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        InformExcModel resourcesModel = InformExcModel.values()[position];
        LayoutInflater inflater = LayoutInflater.from(mContext);
        ViewGroup layout = (ViewGroup) inflater.inflate(resourcesModel.getLayoutResourceId(), container, false);
        if(position == 0) {
            mButton = (Button) layout.findViewById(R.id.butt_order_tour);
            mButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onClicked();
                }
            });
            txt = (TextView) layout.findViewById(R.id.dfdd);
            txt.setText(day);
//            txt = (TextView) layout.findViewById(R.id.dfd);
//            txt.setText("от "+cost+" \u20BD");
        }else {
            if (position == 1) {
                txt = (TextView) layout.findViewById(R.id.name_exc_inf);
                txt.setText(listExc);
            }else{
                    txt = (TextView) layout.findViewById(R.id.full_inf_screen);
                    txt.setText(fullText);
                }
            }
        container.addView(layout);
        return layout;
    }

    public void changeHeightText(float size){
        txt.setMaxHeight((int)size);
    }



    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager)container).removeView((View)object);
    }

    @Override
    public int getCount() {
        return InformExcModel.values().length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


}
