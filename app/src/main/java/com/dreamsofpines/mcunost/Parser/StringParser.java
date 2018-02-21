package com.dreamsofpines.mcunost.Parser;

import android.util.Log;

import com.dreamsofpines.mcunost.data.network.api.Constans;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static android.R.attr.description;
import static android.R.attr.wallpaperCloseEnterAnimation;

/**
 * Created by ThePupsick on 07.08.17.
 */

public class StringParser {
    public StringParser() {
    }

    public static List<String> getArrayStringFromText(String text){
        String str[] = text.split(Pattern.quote("\n"));
        List<String> stringList = new ArrayList<>();
        for(int i = 0; i<str.length;++i){
            if(!str[i].trim().equalsIgnoreCase("")){
                stringList.add(str[i]);
            }
        }

        for(int i = 0; i < stringList.size();++i){
            if(stringList.get(i).trim().substring(0,3).equalsIgnoreCase("<t>")) {
                stringList.set(i, stringList.get(i) + "&" + stringList.get(i + 1));
                stringList.remove(i+1);
            }
        }
        return stringList;
    }


    private static boolean isDesc(String str){
        if(!isDinner(str) && !isFreeTime(str) && !isDayName(str)){
            return true;
        }
        return false;
    }

    public static int getNumbItemRecycler(String str){
        int numb = 3;
        if(isDayName(str)){
            numb = 1;
        }else if(isDinner(str) || isFreeTime(str) || isSmall(str)) {
            numb = 2;
        }
        return numb;
    }

    public static boolean isDayName(String str){
        if(str.trim().substring(0,3).equalsIgnoreCase("<d>")) {
            return true;
        }
        return false;
    }

    public static String getTitleDesc(String title){
        String s[] = title.split("&");
//        if(s[0].trim().substring(2)){
//            return s[0].substring(5);
//        }
        return s[0].trim().substring(3);
    }

    public static boolean isDinner(String str){
//        if(s.length>0 && (s[0].trim().equalsIgnoreCase("Обед") || s[0].trim().equalsIgnoreCase("Завтрак") ||s[0].trim().equalsIgnoreCase("Ужин"))){
        if(str.trim().substring(0,3).equalsIgnoreCase("<e>")){
            return true;
        }
        return false;
    }

    public static boolean isSmall(String str){
        if(str.trim().substring(0,3).equalsIgnoreCase("<s>")){
            return true;
        }
        return false;

    }

    public static  boolean isFreeTime(String str){
//        String s[] = str.split(" ");
//        if(s.length>0 && s[0].trim().equalsIgnoreCase("Свободное")){
        if(str.trim().substring(0,3).equalsIgnoreCase("<f>")){
            return true;
        }
        return false;
    }

    public static String getDescription(String str){
        Log.i("wer",str);
        String s[] = str.split("&");
        return s[1];
    }

    public static boolean isValidNumber(String str){
        Log.i("Myapp","number: "+Pattern.matches("^((8|\\+7)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{7,10}$",str));
        return Pattern.matches("^((8|\\+7)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{7,10}$",str);
    }

    public static boolean isValidEmail(String str){
        Log.i("Myapp","Email: "+Pattern.matches("^([a-z0-9_-]+.)*[a-z0-9_-]+@[a-z0-9_-]+(.[a-z0-9_-]+)*.[a-z]{2,6}$",str));
        return Pattern.matches("^([a-z0-9_-]+.)*[a-z0-9_-]+@[a-z0-9_-]+(.[a-z0-9_-]+)*.[a-z]{2,6}$",str);
    }

    public static int convertCityInToInteger(String city){
        if(city.equalsIgnoreCase("Москва")){
            return Constans.CITY.INT_MOSCOW;
        }
        if(city.equalsIgnoreCase("Санкт-Петербург")){
            return Constans.CITY.INT_SAINT_PETERBURG;
        }
        return Constans.CITY.INT_OTHER_CITY;
    }

}