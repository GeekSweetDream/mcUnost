package com.dreamsofpines.mcunost.ui.dialog;

import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.dreamsofpines.mcunost.R;
import com.dreamsofpines.mcunost.data.network.api.Constans;
import com.dreamsofpines.mcunost.data.network.api.RequestSender;
import com.dreamsofpines.mcunost.data.network.service.MyFirebaseInstanceIDService;
import com.dreamsofpines.mcunost.data.storage.preference.GlobalPreferences;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ThePupsick on 31.10.2017.
 */

public class AuthDialogFragment extends android.support.v4.app.DialogFragment{


    private Button mAuth, mRegist, mCancel, bAuth ,bCancel;
    private View view,auth;
    private RelativeLayout main;
    private LinearLayout LLAuth;
    private AppCompatEditText email, phone;
    private Animation slideLF,slideLS, slideRF, slideRS;
    private boolean isDisabledRegistbtn = false;

    public OnSuccessAuth mOnSuccessAuth;

    public interface OnSuccessAuth{
        void onSuccess();
    }

    public OnClickedReg mListenner;

    public interface OnClickedReg{
        void onClickedReg();
    }


    public static AuthDialogFragment newInstace(OnClickedReg callback, OnSuccessAuth scsCallBack){
        AuthDialogFragment aD = new AuthDialogFragment();
        aD.init(callback,scsCallBack);
        return aD;
    }

    public void init(OnClickedReg callback, OnSuccessAuth scsCallBack){
        mListenner = callback;
        mOnSuccessAuth = scsCallBack;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.view_registration,container,false);
        bindView();
        setListenner();
        if(isDisabledRegistbtn) mRegist.setVisibility(View.GONE);
        return view;
    }

    private void bindView(){
        mAuth = (Button) view.findViewById(R.id.butt_autoriz);
        mRegist = (Button) view.findViewById(R.id.butt_registr);
        mCancel = (Button) view.findViewById(R.id.butt_registr_cancel);
        LLAuth = (LinearLayout) view.findViewById(R.id.auth_view);
        auth = (View) view.findViewById(R.id.view_reg_auth);
        main = (RelativeLayout) view.findViewById(R.id.view_reg_main);
        email = (AppCompatEditText) auth.findViewById(R.id.auth_email_edit);
        phone = (AppCompatEditText) auth.findViewById(R.id.auth_phone_edit);
        bAuth = (Button) auth.findViewById(R.id.butt_auth_auth);
        bCancel = (Button) auth.findViewById(R.id.butt_auth_cancel);
        email.clearFocus();
        phone.clearFocus();
    }

    private void setListenner(){
        mRegist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListenner.onClickedReg();
                dismiss();
            }
        });
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        mAuth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                slideLF = AnimationUtils.loadAnimation(getContext(),R.anim.slide_left_1_obj);
                slideLS = AnimationUtils.loadAnimation(getContext(),R.anim.slide_left_2_obj);
                main.setAnimation(slideLF);
                main.setVisibility(View.GONE);
                LLAuth.setVisibility(View.VISIBLE);
                LLAuth.setAnimation(slideLS);

            }
        });

        bAuth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strEmail = email.getText().toString();
                String strPhone = phone.getText().toString();
                if(strEmail.equalsIgnoreCase("") || strPhone.equalsIgnoreCase("")){
                    Toast.makeText(getContext(), "Не все поля были заполнены",Toast.LENGTH_LONG).show();
                }else {
                    AuthTask authTask = new AuthTask();
                    authTask.execute(strEmail,strPhone);

                }
            }
        });


        bCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                slideRF = AnimationUtils.loadAnimation(getContext(),R.anim.slide_right_1_obj);
                slideRS = AnimationUtils.loadAnimation(getContext(),R.anim.slide_right_2_obj);

                LLAuth.setAnimation(slideRS);
                LLAuth.setVisibility(View.GONE);

                main.setVisibility(View.VISIBLE);
                main.setAnimation(slideRF);

            }
        });

    }

    public void disabledRegistr(){
        isDisabledRegistbtn = true;
    }

    private class AuthTask extends AsyncTask<String, Void, Boolean>{

        private String errrorMsg = "Ooops! Что-то пошло не так! Попробуйте позже! :)";

        @Override
        protected Boolean doInBackground(String... strings) {
            Boolean success = true;
            String responce = null;
            try {
                responce = RequestSender.GetUserInformation(strings[0], strings[1]);
                JSONObject answer = new JSONObject(responce);
                if(answer.getString("result").equalsIgnoreCase("success")){
                    JSONObject user = answer.getJSONObject("data");
                    try{
                        GlobalPreferences.setPrefIdUser(getContext(),user.getString("id"));
                        GlobalPreferences.setPrefUserEmail(getContext(),user.getString("email"));
                        GlobalPreferences.setPrefUserNumber(getContext(),user.getString("phone"));
                        GlobalPreferences.setPrefUserName(getContext(),user.getString("name"));
                        GlobalPreferences.setPrefAddUser(getContext(),Constans.AUTH.LOG_IN);
                    }catch (JSONException e){
                        Log.i("AUTH","JSON PARSER ERROR! TEXT ERROR:" +e.getMessage());
                    }
                } else{
                    success = false;

                    /*
                        error 401
                        i can't get information

                     */

                    errrorMsg = answer.getString("mess");
                }

            }catch (Exception e) {
                Log.i("AUTH", "GET ERROR, ERROR MESSAGE: " + e.getMessage());
                success = false;
            }
            return success;
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if(success){
                Toast.makeText(getContext(), "Успешная авторизация", Toast.LENGTH_LONG).show();
                MyFirebaseInstanceIDService id = new MyFirebaseInstanceIDService();
                id.onTokenRefresh(getContext());
                mOnSuccessAuth.onSuccess();
                dismiss();
            }else {
                Toast.makeText(getContext(), errrorMsg, Toast.LENGTH_LONG).show();
            }
        }
    }


}

