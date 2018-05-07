package com.dreamsofpines.mcunost.ui.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatEditText;
import android.text.LoginFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dreamsofpines.mcunost.R;
import com.dreamsofpines.mcunost.data.network.api.Constans;
import com.dreamsofpines.mcunost.data.network.api.RequestSender;
import com.dreamsofpines.mcunost.Parser.StringParser;
import com.dreamsofpines.mcunost.data.network.service.MyFirebaseInstanceIDService;
import com.dreamsofpines.mcunost.data.storage.preference.GlobalPreferences;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ThePupsick on 18.08.17.
 */

public class RegistrFragment extends Fragment {

    private Button mCancel, mRegistr;
    private AppCompatEditText name, email, number;
    private TextView tittle;

    public static OnClickCancelListener mListener;

    public interface OnClickCancelListener {
        void onClicked(boolean isLogUp);
    }

    public void setOnClickRegistrListener(OnClickCancelListener listener) {
        mListener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_registr, container, false);

        bindView(view);
        setListenerView();

        TextView title = (TextView) getActivity().findViewById(R.id.title_tour);
        title.setText("Регистрация");
        Button help = (Button) getActivity().findViewById(R.id.button_help);
        help.setVisibility(View.GONE);
        return view;
    }

    private void bindView(View view) {
        name = (AppCompatEditText) view.findViewById(R.id.registr_name_edit);
        email = (AppCompatEditText) view.findViewById(R.id.registr_email_edit);
        number = (AppCompatEditText) view.findViewById(R.id.registr_number_edit);
        mRegistr = (Button) view.findViewById(R.id.fr_butt_registr);
        mCancel = (Button) view.findViewById(R.id.fr_butt_registr_cancel);
    }

    private void setListenerView() {

        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onClicked(false);
            }
        });

        mRegistr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean allFill = true;
                if (name.getText().toString().equalsIgnoreCase("")) {
                    allFill = false;
                    name.setError("Введите имя и фамилию пользователя");
                }
                if (!StringParser.isValidEmail(email.getText().toString())) {
                    allFill = false;
                    email.setError("Введите вашу почту");
                }
                if (!StringParser.isValidNumber(number.getText().toString())) {
                    allFill = false;
                    number.setError("Введите ваш номер");
                }
                if (allFill) {
                    addUserTask userTask = new addUserTask();
                    userTask.execute(createJsonUser());
                }
            }
        });
    }
        private void registrSuccsess() {
            GlobalPreferences.setPrefUserName(getActivity(), name.getText().toString());
            GlobalPreferences.setPrefUserNumber(getActivity(), number.getText().toString());
            GlobalPreferences.setPrefUserEmail(getActivity(), email.getText().toString());
            GlobalPreferences.setPrefAddUser(getActivity(),Constans.AUTH.LOG_IN);
            MyFirebaseInstanceIDService id = new MyFirebaseInstanceIDService();
            id.onTokenRefresh(getContext());
            Toast.makeText(getActivity(), "Вы успешно зарегестрированы!", Toast.LENGTH_LONG)
                    .show();
            mListener.onClicked(true);
        }


    private JSONObject createJsonUser() {
        JSONObject jsUser = new JSONObject();
        try {
            jsUser.put("name", name.getText().toString());
            jsUser.put("phone", number.getText().toString());
            jsUser.put("email", email.getText().toString());
            jsUser.put("accountNonExpired", Constans.ACCOUNT.ACCESS);
            jsUser.put("accountNonLocked",Constans.ACCOUNT.ACCESS);
            jsUser.put("credentialsNonExpired",Constans.ACCOUNT.ACCESS);
            jsUser.put("enabled",Constans.ACCOUNT.ACCESS);
            jsUser.put("idRole", Constans.ROLE.USER);
        } catch (JSONException e) {
            Log.i("Myapp", "Create json user error! TextMessage: " + e.getMessage());
        }
        return jsUser;
    }

    private class addUserTask extends AsyncTask<JSONObject, Void, Boolean> {
        private String errorMsg = "Ooops! Что-то произошло! Попробуйте позднее! :)";
        @Override
        protected Boolean doInBackground(JSONObject... jsonObjects) {
            boolean success = true;
                String answer = RequestSender.POST(getContext(),Constans.URL.USER.ADD_NEW_USER, jsonObjects[0],false);
                try{
                    JSONObject js = new JSONObject(answer);
                    if(js.getString("result").equalsIgnoreCase("success")){
                        GlobalPreferences.setPrefIdUser(getContext(),js.getString("data"));
                    }else{
                        errorMsg = js.getString("mess");
                        success = false;
                    }
                }catch (Exception e){
                    Log.i("Registr","JsonExeption from parsing json registr user! Error message: "+e.getMessage());
                    success = false;
                }
            return success;
        }

        @Override
        protected void onPostExecute(Boolean succsess) {
            if(succsess) {
                registrSuccsess();
            }else{
                Toast.makeText(getActivity(), errorMsg, Toast.LENGTH_LONG)
                        .show();
                email.setError("Email или Телефон уже был зарегистрирован!");
                number.setError("Email или Телефон уже был зарегистрирован!");
            }
        }
    }

}