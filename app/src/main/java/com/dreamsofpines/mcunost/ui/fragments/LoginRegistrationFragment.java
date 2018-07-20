package com.dreamsofpines.mcunost.ui.fragments;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.dreamsofpines.mcunost.Parser.StringParser;
import com.dreamsofpines.mcunost.R;
import com.dreamsofpines.mcunost.data.network.api.Constans;
import com.dreamsofpines.mcunost.data.network.api.RequestSender;
import com.dreamsofpines.mcunost.data.network.service.MyFirebaseInstanceIDService;
import com.dreamsofpines.mcunost.data.storage.preference.GlobalPreferences;
import com.dreamsofpines.mcunost.ui.customView.LoadingView;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginRegistrationFragment extends Fragment {

    private View view;
    private View alpha;
    private LinearLayout mLinearLayout;
    private ViewGroup mViewGroup;
    private EditText email;
    private EditText name;
    private EditText phone;
    private Button loginBtn;
    private Button registerBtn;
    private LoadingView loadView;
    private int state = 0;
    private OnClickAlphaListener listener;

    public interface OnClickAlphaListener{
        void onClick(boolean success);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = (View) inflater.inflate(R.layout.login_registration_view,container,false);
        bindView();
        setListeners();
        return view;
    }

    private View.OnTouchListener changeShape = (view, motionEvent)-> {
            switch (motionEvent.getAction()){
                case MotionEvent.ACTION_UP:{
                    view.setBackground(getContext().getDrawable(R.drawable.border_blue_stroke_with_white_back));
                }
            }
            return false;
    };


    private View.OnFocusChangeListener chShape = (view,b)->{
          if(!b) view.setBackground(getContext().getDrawable(R.drawable.border_grey));
    };


    private void setListeners(){
        registerBtn.setOnClickListener((view)->{
            if(state == 1){
                if(allFill()) {
                    loadView.show();
                    AddUserTask addUserTask = new AddUserTask();
                    addUserTask.execute(createJsonUser());
                }
            }else{
                state = 1;
                show(1);
            }
        });
        alpha.setOnClickListener((view)->{
            hideKeyboard();
            listener.onClick(false);
        });
        loginBtn.setOnClickListener((view)->{
            if(allFill()){
                hideKeyboard();
                loadView.show();
                AuthTask authTask = new AuthTask();
                authTask.execute(email.getText().toString().toLowerCase(),phone.getText().toString().toLowerCase());
            }
        });
        email.setOnTouchListener(changeShape);
        email.setOnFocusChangeListener(chShape);
        phone.setOnTouchListener(changeShape);
        phone.setOnFocusChangeListener(chShape);
        name.setOnTouchListener(changeShape);
        name.setOnFocusChangeListener(chShape);

    }

    private void hideKeyboard(){
        InputMethodManager imm = (InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(email.getWindowToken(), 0);
    }


    @Override
    public void onResume() {
        super.onResume();
        state = 0;
    }

    private void bindView(){
        mLinearLayout = (LinearLayout) view.findViewById(R.id.lin);
        mViewGroup = (ViewGroup) view.findViewById(R.id.scene1);
        email    = (EditText) view.findViewById(R.id.email);
        name     = (EditText) view.findViewById(R.id.name);
        phone    = (EditText) view.findViewById(R.id.phone);
        alpha    = (View)   view.findViewById(R.id.alpha);
        loginBtn = (Button) view.findViewById(R.id.btn_log);
        registerBtn = (Button) view.findViewById(R.id.btn_reg);
        loadView = (LoadingView) view.findViewById(R.id.load_view);
    }


    public void show(int posView){
        TransitionManager.beginDelayedTransition(mViewGroup);
        ViewGroup.LayoutParams params = mLinearLayout.getLayoutParams();
        params.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        mLinearLayout.setLayoutParams(params);
        loginBtn.setVisibility(posView==1?View.GONE:View.VISIBLE);
        email.setVisibility(View.VISIBLE);
        phone.setVisibility(View.VISIBLE);
        name.setVisibility(posView == 1? View.VISIBLE:View.GONE);
        registerBtn.setVisibility(View.VISIBLE);
    }

    public void setListener(OnClickAlphaListener listener) {
        this.listener = listener;
    }


    private boolean allFill() {
        boolean allFill = true;
        String error = "";
        if (!StringParser.isValidEmail(email.getText().toString())) {
            allFill = false;
            error = "Введите вашу почту";
        }
        if (allFill && !StringParser.isValidNumber(phone.getText().toString().toLowerCase())) {
            allFill = false;
            error = "Введите ваш номер";
        }
        if (!allFill) Toast.makeText(getContext(),error,Toast.LENGTH_SHORT).show();

        return allFill;
    }


    private JSONObject createJsonUser() {
        JSONObject jsUser = new JSONObject();
        try {
            jsUser.put("name", name.getText().toString());
            jsUser.put("phone", phone.getText().toString());
            jsUser.put("email", email.getText().toString().toLowerCase());
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

    private void registrSuccsess() {
        GlobalPreferences.setPrefUserName(getActivity(), name.getText().toString());
        GlobalPreferences.setPrefUserNumber(getActivity(), phone.getText().toString());
        GlobalPreferences.setPrefUserEmail(getActivity(), email.getText().toString().toLowerCase());
        GlobalPreferences.setPrefAddUser(getActivity(),Constans.AUTH.LOG_IN);
        MyFirebaseInstanceIDService id = new MyFirebaseInstanceIDService();
        id.onTokenRefresh(getContext());
        listener.onClick(true);
        Toast.makeText(getActivity(), "Вы успешно зарегестрированы!", Toast.LENGTH_LONG)
                .show();
    }





    private class AuthTask extends AsyncTask<String, Void, Boolean> {

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
                        GlobalPreferences.setPrefAddUser(getContext(), Constans.AUTH.LOG_IN);
                    }catch (JSONException e){
                        Log.i("AUTH","JSON PARSER ERROR! TEXT ERROR:" +e.getMessage());
                    }
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
            loadView.hide();
            if(success){
                Toast.makeText(getContext(), "Успешная авторизация", Toast.LENGTH_LONG).show();
                MyFirebaseInstanceIDService id = new MyFirebaseInstanceIDService();
                id.onTokenRefresh(getContext());
                listener.onClick(true);
            }else {
                Toast.makeText(getContext(), errrorMsg, Toast.LENGTH_LONG).show();
            }
        }
    }


    private class AddUserTask extends AsyncTask<JSONObject, Void, Boolean> {
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
            loadView.hide();
            if(succsess) {
                registrSuccsess();
            }else{
                Toast.makeText(getActivity(), errorMsg, Toast.LENGTH_LONG)
                        .show();
            }
        }
    }






}

