package com.dreamsofpines.mcunost;

import android.app.Application;

//import com.yandex.metrica.YandexMetrica;

public class mcUnost extends Application {

    public final static String API_key = 	"****";


    @Override
    public void onCreate() {
        super.onCreate();
        // Инициализация AppMetrica SDK
//        YandexMetrica.activate(getApplicationContext(), API_key);
//        // Отслеживание активности пользователей
//        YandexMetrica.enableActivityAutoTracking(this);
    }
}
