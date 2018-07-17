package com.dreamsofpines.mcunost.data.utils;

public class CalendarUtils {
    private static String[] months = {"Январь","Февраль","Март","Апрель","Май","Июнь","Июль",
            "Август","Сентябрь","Октябрь","Ноябрь","Декабрь"};

    public static String getMonth(int month){
        return months[month];
    }

    private static String dayName[] = {"П","В","C","Ч","П","С","В"};

    public static String getDayName(int i){
        return dayName[i%7];
    }


}
