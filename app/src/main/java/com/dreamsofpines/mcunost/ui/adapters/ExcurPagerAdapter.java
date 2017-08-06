package com.dreamsofpines.mcunost.ui.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dreamsofpines.mcunost.R;
import com.dreamsofpines.mcunost.data.storage.models.InformExcModel;

/**
 * Created by ThePupsick on 06.08.17.
 */

public class ExcurPagerAdapter extends PagerAdapter {
    private Context mContext;
    private View mCurrentView;

    public ExcurPagerAdapter(Context contex){
        mContext = contex;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        InformExcModel resourcesModel = InformExcModel.values()[position];
        LayoutInflater inflater = LayoutInflater.from(mContext);
      //  ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.short_inf_exc_screen, container, false);
        ViewGroup layout = (ViewGroup) inflater.inflate(resourcesModel.getLayoutResourceId(), container, false);
        container.addView(layout);
        return layout;
    }

    public View getCurrentView() {
        return mCurrentView;
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        mCurrentView = (View)object;
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
