package com.dreamsofpines.mcunost.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.dreamsofpines.mcunost.R;
import com.dreamsofpines.mcunost.data.storage.preference.GlobalPreferences;

/**
 * Created by ThePupsick on 18.08.17.
 */

public class RegistrFragment extends Fragment {

    private Button mCancel, mRegistr;
    private AppCompatEditText name,email,number;

    public static OnClickCancelListener mListener;
    public interface OnClickCancelListener{
        void onClicked(boolean isLogUp);
    }

    public void setOnClickRegistrListener(OnClickCancelListener listener){
        mListener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_registr,container,false);

        name = (AppCompatEditText) view.findViewById(R.id.registr_name_edit);
        email = (AppCompatEditText) view.findViewById(R.id.registr_email_edit);
        number = (AppCompatEditText) view.findViewById(R.id.registr_number_edit);

        mRegistr = (Button) view.findViewById(R.id.fr_butt_registr);
        mRegistr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean allFill = true;
                if(name.getText().toString().equalsIgnoreCase("")){
                    allFill = false;
                    name.setError("Введите имя и фамилию пользователя");
                }
                if(email.getText().toString().equalsIgnoreCase("")){
                    allFill = false;
                    email.setError("Введите вашу почту");
                }
                if(number.getText().toString().equalsIgnoreCase("")){
                    allFill = false;
                    number.setError("Введите ваш номер");
                }
                if(allFill) {
                    GlobalPreferences.setPrefUserName(getActivity(),name.getText().toString());
                    GlobalPreferences.setPrefUserNumber(getActivity(),number.getText().toString());
                    GlobalPreferences.setPrefUserEmail(getActivity(),email.getText().toString());
                  //  GlobalPreferences.setPrefAddUser(getActivity());
                    Toast.makeText(getActivity(), "Вы успешно зарегестрированы!", Toast.LENGTH_LONG)
                            .show();
                    mListener.onClicked(true);
                }
            }
        });

        mCancel = (Button) view.findViewById(R.id.fr_butt_registr_cancel);
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onClicked(false);
            }
        });

        return view;
    }
}
