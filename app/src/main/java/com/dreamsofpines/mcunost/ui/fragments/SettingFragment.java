package com.dreamsofpines.mcunost.ui.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dreamsofpines.mcunost.R;
import com.dreamsofpines.mcunost.data.network.api.Constans;
import com.dreamsofpines.mcunost.data.network.api.RequestSender;
import com.dreamsofpines.mcunost.data.storage.preference.GlobalPreferences;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import org.json.JSONException;
import org.json.JSONObject;

import static android.R.attr.name;

/**
 * Created by ThePupsick on 21.08.17.
 */

public class SettingFragment extends Fragment {

    private TextView tittle;
    private Button btnSave;
    private String nameBase,emailBase,phoneBase;
    public changeInformationListener mListener;
    private AppCompatEditText name, email, phone;

    public interface changeInformationListener{
        void onChange();
    }

    public void setChangeInfListener(changeInformationListener listener){
        mListener = listener;
    }

    @Override
    public void onResume() {
        nameBase = GlobalPreferences.getPrefUserName(getActivity());
        emailBase = GlobalPreferences.getPrefUserEmail(getActivity());
        phoneBase = GlobalPreferences.getPrefUserNumber(getActivity());
        super.onResume();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting,container,false);

        tittle = (TextView) getActivity().findViewById(R.id.title_tour);
        tittle.setText("Настройки");
        Button help = (Button) getActivity().findViewById(R.id.button_help);
        help.setVisibility(View.GONE);

        btnSave = (Button) view.findViewById(R.id.butt_save_setting);

        nameBase = GlobalPreferences.getPrefUserName(getActivity());
        emailBase = GlobalPreferences.getPrefUserEmail(getActivity());
        phoneBase = GlobalPreferences.getPrefUserNumber(getActivity());

        name = view.findViewById(R.id.name_setting);
        name.setText(nameBase);

        email = view.findViewById(R.id.email_setting);
        email.setText(emailBase);

        phone = view.findViewById(R.id.phone_setting);
        phone.setText(phoneBase);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean needSave = !name.getText().toString().equalsIgnoreCase(nameBase)
                            || !email.getText().toString().equalsIgnoreCase(emailBase)
                            || !phone.getText().toString().equalsIgnoreCase(phoneBase);
                if(needSave){
                    JSONObject user = createJsonUserObject();
                    UpdateUserInfoTask update = new UpdateUserInfoTask();
                    update.execute(user);
                }else {
                    Toast.makeText(getContext(), "Нет изменений, которые нужно сохранить", Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });
        return view;
    }

    private void saveInfo(){
        GlobalPreferences.setPrefUserName(getContext(),name.getText().toString());
        GlobalPreferences.setPrefUserEmail(getContext(),email.getText().toString());
        GlobalPreferences.setPrefUserNumber(getContext(),phone.getText().toString());
        Toast.makeText(getContext(),"Данные успешно обновлены",Toast.LENGTH_SHORT)
                .show();
        mListener.onChange();
    }



    private JSONObject createJsonUserObject(){
        JSONObject js = new JSONObject();
        try {
            js.put("id",GlobalPreferences.getPrefIdUser(getContext()));
            js.put("email",email.getText().toString());
            js.put("phone",phone.getText().toString());
            js.put("name",name.getText().toString());
            mListener.onChange();
        }catch (JSONException e){
            Log.i("Setting","Create json user error! Error message: "+e.getMessage());
            return null;
        }
        return js;
    }

    private class UpdateUserInfoTask extends AsyncTask<JSONObject,Void,Boolean>{
        private String errorMsg ="Ooops! Проблемы сети, попробуйте позже! =)";
        @Override
        protected Boolean doInBackground(JSONObject... jsonObjects) {
            Boolean success = true;
            String responce = RequestSender.POST(getContext(), Constans.URL.USER.UPDATE_USER_INFO,jsonObjects[0],true);
            if(responce!=null) {
                try {
                    JSONObject js = new JSONObject(responce);
                    if (!js.getString("result").equalsIgnoreCase("success")) {
                        success = false;
                        errorMsg = js.getString("mess");
                    }
                } catch (JSONException e) {
                    Log.i("Setting", "Parsing answer server error! Error message: " + e.getMessage());
                    success = false;
                }
            }
            else{
                success = false;
            }
            return success;
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if(success){
                saveInfo();
            }else{
                Toast.makeText(getContext(),errorMsg,Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }


}
