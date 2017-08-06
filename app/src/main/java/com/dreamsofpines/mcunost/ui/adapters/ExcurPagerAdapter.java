package com.dreamsofpines.mcunost.ui.adapters;

import android.content.Context;
import android.graphics.pdf.PdfDocument;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dreamsofpines.mcunost.data.storage.help.menu.InformExcursion;
import com.dreamsofpines.mcunost.data.storage.models.InformExcModel;
import com.dreamsofpines.mcunost.data.storage.models.ResourcesModel;

/**
 * Created by ThePupsick on 06.08.17.
 */

public class ExcurPagerAdapter extends PagerAdapter {
    private Context mContext;

    public ExcurPagerAdapter(Context contex){
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


}
