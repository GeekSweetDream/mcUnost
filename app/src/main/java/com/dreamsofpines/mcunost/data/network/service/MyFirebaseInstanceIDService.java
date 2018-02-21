package com.dreamsofpines.mcunost.data.network.service;

import android.content.Context;
import android.os.AsyncTask;

import com.dreamsofpines.mcunost.data.network.api.RequestSender;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by ThePupsick on 28.01.2018.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private Context mContext;

    public void onTokenRefresh(Context context) {
        String token  = FirebaseInstanceId.getInstance().getToken();
        mContext = context;
        sendTokenOnServer(token);
    }

    private void sendTokenOnServer(String token){
        PushToken pushToken = new PushToken();
        pushToken.execute(token);
    }


    private class PushToken extends AsyncTask<String,Void,Void>{
        @Override
        protected Void doInBackground(String... strings) {
            RequestSender.pushToken(mContext,strings[0]);
            return null;
        }
    }

}
