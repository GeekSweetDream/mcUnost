package com.dreamsofpines.mcunost.data.storage.models;

import com.dreamsofpines.mcunost.R;

/**
 * Created by ThePupsick on 14.07.17.
 */

public enum ResourcesModel {

    FIRST_SCREEN(R.string.hello_page,R.layout.hello_screen),
    SECOND_SCREEN(R.string.moscow_page,R.layout.moscow_screen);

    private int mLayoutResourceId;
    private int mTitleResourceId;

    ResourcesModel(int titleResId,int layoutResId){
        mTitleResourceId = titleResId;
        mLayoutResourceId = layoutResId;
    }

    public int getLayoutResourceId() {
        return mLayoutResourceId;
    }

    public int getTitleResourceId() {
        return mTitleResourceId;
    }
}
