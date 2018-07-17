package com.dreamsofpines.mcunost.data.utils;

import com.dreamsofpines.mcunost.data.storage.preference.GlobalPreferences;
import com.dreamsofpines.mcunost.ui.fragments.QuantityGroupFragment;

public class ImageUtils {

    public static final int QUANTITY_AVATARS = 5;
    private static String[] avatars = {"coffee.png","dog.png","cat.png","cabbage.png","forest.png"};
    private static String[] imagesCity = {"ord_spb","ord_msc","ord_kazan"};

    public static String getNameAvatars(int position){
        return avatars[position%QUANTITY_AVATARS];
    }


    public static String getNameImageCity(String city){
        switch (city){
            case "Санкт-Петербург": return imagesCity[0];
            case "Москва": return imagesCity[1];
            case "Казань": return imagesCity[2];
            default: return imagesCity[2];
        }
    }

}
