package com.dreamsofpines.mcunost.data.storage.preference;

import android.content.Context;
import android.preference.PreferenceManager;

/**
 * Created by ThePupsick on 31.07.17.
 */

public class GlobalPreferences {

    private static final String PREF_ADD_USER = "addUser";
    private static final String PREF_USER_NAME = "userName";
    private static final String PREF_USER_NUMBER = "userNumber";
    private static final String PREF_USER_CITY = "userCity";

    public static String getPrefUserCity(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(PREF_USER_CITY,"Не задан");
    }

    public static void setPrefUserCity(Context context,String city){
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(PREF_USER_CITY,city)
                .apply();
    }

    public static int getPrefAddUser(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getInt(PREF_ADD_USER,0);
    }

    public static void setPrefAddUser(Context context){
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putInt(PREF_ADD_USER,1)
                .apply();
    }

    public static String getPrefUserName(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(PREF_USER_NAME,"Пользователь");
    }

    public static void setPrefUserName(Context context, String userName){
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(PREF_USER_NAME,userName)
                .apply();
    }

    public static String getPrefUserNumber(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(PREF_USER_NUMBER,"Телефон не задан");
    }

    public static void setPrefUserNumber(Context context, String userNumber){
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(PREF_USER_NUMBER,userNumber)
                .apply();
    }



}
