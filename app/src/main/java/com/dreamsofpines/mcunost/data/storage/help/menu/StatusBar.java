package com.dreamsofpines.mcunost.data.storage.help.menu;

import android.app.Activity;

/**
 * Created by ThePupsick on 31.07.17.
 */

public class StatusBar {
    public static int getHightStatusBar(Activity activity){
        int result = 0;
        int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0)
            result = activity.getResources().getDimensionPixelSize(resourceId);
        return result;
    }
}
