package com.dreamsofpines.mcunost.ui.adapters.pager;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.dreamsofpines.mcunost.R;
import com.dreamsofpines.mcunost.data.storage.models.InformExcModel;
import com.dreamsofpines.mcunost.ui.adapters.recyclerDescription.ViewDescAdapter;

/**
 * Created by ThePupsick on 06.08.17.
 */

public class ExcurPagerAdapter extends PagerAdapter {
    private Context mContext;
    private TextView txt,txtService;
    private ImageView canv;
    private RecyclerView excur;
    private String shDesc,day,fullText,inTour,addService;
    private Button mButton;
    private ViewDescAdapter mAdapter;

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

    public void setShDesc(String shDesc) {
        this.shDesc = shDesc;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public void setFullText(String fullText) {
        this.fullText = fullText;
    }

    public void setInTour(String inTour) {
        this.inTour = inTour;
    }

    public void setAddService(String addService) {
        this.addService = addService;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        InformExcModel resourcesModel = InformExcModel.values()[position];
        LayoutInflater inflater = LayoutInflater.from(mContext);
        ViewGroup layout = (ViewGroup) inflater.inflate(resourcesModel.getLayoutResourceId(), container, false);
        /*
            Это плохо, но нет другого решения
         */
        if(position == 0) {
//            mButton = (Button) layout.findViewById(R.id.butt_order_tour);
//
//            mButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    mButton.setVisibility(View.INVISIBLE);
//                    mListener.onClicked();
//                }
//            });
            txt = (TextView) layout.findViewById(R.id.dfdd);
            txt.setText(shDesc);
        }else {
            if (position == 1) {
                txt = (TextView) layout.findViewById(R.id.name_exc_inf);
                txtService = (TextView) layout.findViewById(R.id.service);
                txt.setText(inTour);
                txtService.setText(addService);
            }
            else{
                    excur = (RecyclerView) layout.findViewById(R.id.full_inf_recycler);
                    mAdapter = new ViewDescAdapter(fullText,mContext);
                    excur.setLayoutManager(new LinearLayoutManager(mContext));
                    excur.setAdapter(mAdapter);
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
