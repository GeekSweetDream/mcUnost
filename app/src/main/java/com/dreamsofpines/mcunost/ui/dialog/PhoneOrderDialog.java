package com.dreamsofpines.mcunost.ui.dialog;

import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.dreamsofpines.mcunost.R;
import com.dreamsofpines.mcunost.data.network.api.Constans;
import com.dreamsofpines.mcunost.data.network.api.RequestSender;
import com.dreamsofpines.mcunost.data.storage.preference.GlobalPreferences;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ThePupsick on 12.11.2017.
 */

public class PhoneOrderDialog extends DialogFragment {

    private View view;
    private AppCompatEditText name, phone;
    private Button cancel, book;

    public static PhoneOrderDialog newInstance(){
        PhoneOrderDialog pD = new PhoneOrderDialog();
        return pD;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dialog_phone_order,container,false);
        bindView();
        setListener();
        return view;
    }

    private void bindView(){
        name = (AppCompatEditText) view.findViewById(R.id.name_phone_order_edit);
        phone = (AppCompatEditText) view.findViewById(R.id.phone_phone_order_edit);
        cancel = (Button) view.findViewById(R.id.butt_phone_order_cancel);
        book = (Button) view.findViewById(R.id.butt_phone_order);
        name.clearFocus();
        phone.clearFocus();
    }

    private void setListener(){
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean success = true;
                String n = name.getText().toString();
                String p = phone.getText().toString();
                if(n.equalsIgnoreCase("")){
                    success = false;
                    name.setError("Заполните поле");
                }
                if((p.equalsIgnoreCase(""))){
                    success = false;
                    phone.setError("Заполните поле");
                }
                if(success){
                    book.setClickable(false);
                    PhoneBookTask phoneBookTask = new PhoneBookTask();
                    phoneBookTask.execute(createJson(n,p));
                }
            }
        });
    }

    private JSONObject createJson(String name, String phone){
        JSONObject js = new JSONObject();
        try {
            js.put("name", name);
            js.put("phone", phone);
        }catch (Exception e){}
        return js;
    }

    private class PhoneBookTask extends AsyncTask<JSONObject, Void, Boolean> {

        private String errrorMsg = "Ooops! Что-то пошло не так! Попробуйте позже! :)";

        @Override
        protected Boolean doInBackground(JSONObject... jsonObjects) {
            Boolean success = true;
            String responce = null;
            try {
                responce = RequestSender.POST(getContext(),Constans.URL.PHONE_ORDER.BOOOK_CALL,jsonObjects[0],false);
                JSONObject answer = new JSONObject(responce);
                if(answer.getString("result").equalsIgnoreCase("success")){
                } else{
                    success = false;
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
                Toast.makeText(getContext(), "Ваша заявка успешна добавлена! " +
                        "Наш менеджер скоро свяжется с вами!", Toast.LENGTH_LONG).show();
                dismiss();
            }else {
                Toast.makeText(getContext(), errrorMsg, Toast.LENGTH_LONG).show();
            }
            book.setClickable(true);
        }
    }



}

