package com.dreamsofpines.mcunost.ui.adapters;

import android.content.Context;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dreamsofpines.mcunost.data.storage.models.ResourcesModel;

/**
 * Created by ThePupsick on 14.07.17.
 */

public class SimplePagerAdapter extends PagerAdapter {

    private Context mContext;

    public SimplePagerAdapter(Context contex){
        mContext = contex;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ResourcesModel resourcesModel = ResourcesModel.values()[position];
        LayoutInflater inflater = LayoutInflater.from(mContext);
        ViewGroup layout = (ViewGroup) inflater.inflate(resourcesModel.getLayoutResourceId(), container, false);
        container.addView(layout);
        return layout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }

    @Override
    public int getCount() {
        return ResourcesModel.values().length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

//    @Override
//    public CharSequence getPageTitle(int position) {
//        ResourcesModel customPagerEnum = ResourcesModel.values()[position];
//        return mContext.getString(customPagerEnum.getTitleResourceId());
//    }
}
