package com.dreamsofpines.mcunost.data.network.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.dreamsofpines.mcunost.R;
import com.dreamsofpines.mcunost.data.storage.preference.GlobalPreferences;
import com.dreamsofpines.mcunost.ui.activities.CategoriesActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

/**
 * Created by ThePupsick on 28.01.2018.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if(remoteMessage.getData().size()>0){
            Map<String,String> m = remoteMessage.getData();
            sendNotification(m.get("section"),m.get("mess"));
        }
    }

    private void sendNotification(String section, String messageBody) {
        Intent intent = new Intent(this, CategoriesActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, "fcm_default_channel")
                        .setSmallIcon(R.mipmap.untitled)
                        .setContentTitle(section)
                        .setContentText(messageBody)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        int fl = 1; 
//        if(section.equalsIgnoreCase("Сообщения")){
//            fl = 1;
////            GlobalPreferences.setPrefQuantityNewMess(getBaseContext());
//        }else if(section.equalsIgnoreCase("Менеджер")){
//            fl = 2;
//        }
        notificationManager.notify(fl, notificationBuilder.build());

    }

}
