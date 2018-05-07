package com.dreamsofpines.mcunost.data.storage.preference;

import android.content.Context;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.widget.ProgressBar;


/**
 * Created by ThePupsick on 31.07.17.
 */

public class GlobalPreferences {

    private static final String PREF_HELP_MAIN = "helpMain";
    private static final String PREF_HELP_DIN = "helpDin";
    private static final String PREF_HELP_BUS = "helpBus";
    private static final String PREF_ADD_USER = "addUser";
    private static final String PREF_USER_NAME = "userName";
    private static final String PREF_USER_NUMBER = "userNumber";
    private static final String PREF_USER_EMAIL = "userEmail";
    private static final String PREF_USER_CITY = "userCity";
    private static final String PREF_ID_USER = "idUser";
    private static final String PREF_QUANTITY_NEW_MESS = "messNew";
    private static final String PREF_QUANTITY_NEW = "quantityNew";


    public static void setPrefIdUser(Context context,String id) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(PREF_ID_USER, id)
                .apply();
    }

    public static String getPrefIdUser(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(PREF_ID_USER,"0");
    }

    public static void setPrefUserEmail(Context context, String email){
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(PREF_USER_EMAIL,email)
                .apply();
    }


    public static  String getPrefUserEmail(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(PREF_USER_EMAIL,"Не задан");
    }

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

    public static void setPrefAddUser(Context context,int isAuth){
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putInt(PREF_ADD_USER,isAuth)
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

    public static void setPrefQuantityNew(Context context){
        int count = getPrefQuantityNew(context);
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putInt(PREF_QUANTITY_NEW,++count)
                .apply();
    }

    public static int getPrefQuantityNew(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context)
                    .getInt(PREF_QUANTITY_NEW,0);
    }

    public static void setZeroPrefQuantityNew(Context context){
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putInt(PREF_QUANTITY_NEW,0)
                .apply();
    }

    public static int getPrefQuantityNewMess(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context).getInt(PREF_QUANTITY_NEW_MESS,0);
    }

    public static void setPrefQuantityNewMess(Context context){
        int count = getPrefQuantityNewMess(context);
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putInt(PREF_QUANTITY_NEW_MESS,++count)
                .apply();
    }

    public static void setMinusOneQuantityNewMess(Context context){
        int count = getPrefQuantityNewMess(context);
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putInt(PREF_QUANTITY_NEW_MESS,--count)
                .apply();
    }

    public static int getPrefHelpMain(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getInt(PREF_HELP_MAIN,0);
    }

    public static int getPrefHelpDin(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getInt(PREF_HELP_DIN,0);
    }

    public static int getPrefHelpBus(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getInt(PREF_HELP_BUS,0);
    }

    public static void setPrefHelpMain(Context context,int a){
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putInt(PREF_HELP_MAIN,a)
                .apply();
    }

    public static void setPrefHelpDin(Context context,int a){
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putInt(PREF_HELP_DIN,a)
                .apply();
    }

    public static void setPrefHelpBus(Context context,int a){
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putInt(PREF_HELP_BUS,a)
                .apply();
    }



}
