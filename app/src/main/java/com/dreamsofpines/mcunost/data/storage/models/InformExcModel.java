package com.dreamsofpines.mcunost.data.storage.models;

import com.dreamsofpines.mcunost.R;

/**
 * Created by ThePupsick on 06.08.17.
 */

public enum InformExcModel {

    FIRST_SCREEN(R.string.short_info, R.layout.short_inf_exc_screen),
    SECOND_SCREEN(R.string.excursion, R.layout.excursion_list_inf_screen),
    THIRD_SCREEN(R.string.full_info, R.layout.full_inf_exc_screen);
    private int mLayoutResourceId;
    private int mTitleResourceId;

    InformExcModel(int titleResId,int layoutResId){
        mTitleResourceId = titleResId;
        mLayoutResourceId = layoutResId;
    }

    public int getLayoutResourceId() {
        return mLayoutResourceId;
    }

}
