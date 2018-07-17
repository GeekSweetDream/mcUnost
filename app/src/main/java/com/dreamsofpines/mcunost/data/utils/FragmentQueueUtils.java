package com.dreamsofpines.mcunost.data.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.util.ArrayDeque;
import java.util.Queue;

public class FragmentQueueUtils {

    private static ArrayDeque<Fragment> lenta = new ArrayDeque<>();
    private static ArrayDeque<Fragment> constructor = new ArrayDeque<>();
    private static ArrayDeque<Fragment> orders = new ArrayDeque<>();

    public static void addValueLenta(Fragment fragment){
        lenta.add(fragment);
    }

    public static void addValueConstructor(Fragment fragment){
        constructor.add(fragment);
    }

    public static void addValueOrders(Fragment fragment){
        orders.add(fragment);
    }

    public static Fragment getValueLenta(){
        return lenta.pollFirst();
    }

    public static Fragment getValueConstructor(){
        return constructor.pollFirst();
    }

    public static Fragment getValueOrders() {
        return orders.pollFirst();
    }


}
