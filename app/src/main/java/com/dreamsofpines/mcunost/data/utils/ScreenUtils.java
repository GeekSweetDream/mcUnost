package com.dreamsofpines.mcunost.data.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.Window;

public class ScreenUtils {

    public static int getDpFromPx(int px, Context context){
        return (int)(px * context.getResources().getDisplayMetrics().density);
    }

    public static DisplayMetrics getDisplayMetrics(Context context){
        return context.getResources().getDisplayMetrics();
    }

    public static int getStatusBarSize(Activity activity){
        Rect rectangle = new Rect();
        Window window = activity.getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(rectangle);
        int statusBarHeight = rectangle.top;
        int contentViewTop = window.findViewById(Window.ID_ANDROID_CONTENT).getTop();
        return contentViewTop - statusBarHeight;
    }

}
