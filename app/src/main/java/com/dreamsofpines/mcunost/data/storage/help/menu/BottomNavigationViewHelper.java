package com.dreamsofpines.mcunost.data.storage.help.menu;

import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.lang.reflect.Field;

public class BottomNavigationViewHelper {
    public static void disableShiftMode(BottomNavigationViewEx view) {
        view.setTextVisibility(false);
        view.enableShiftingMode(false);
        view.enableItemShiftingMode(false);
        view.enableAnimation(false);
    }
}
