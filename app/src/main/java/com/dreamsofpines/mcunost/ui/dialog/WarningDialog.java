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
import com.dreamsofpines.mcunost.ui.fragments.ExcursionFragment;

import org.json.JSONObject;

import static android.R.id.list;

/**
 * Created by ThePupsick on 12.11.2017.
 */

public class WarningDialog extends DialogFragment {

    private View view;
    private Button cancel, book;
    private okListner listener;
    public interface okListner{
        void okPress();
    }
    public void setOkPressListener(okListner listener){
        this.listener = listener;
    }
    public static WarningDialog newInstance(){
        WarningDialog pD = new WarningDialog();
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
        view = inflater.inflate(R.layout.dialog_warning,container,false);
        bindView();
        setListener();
        return view;
    }

    private void bindView(){
        cancel = (Button) view.findViewById(R.id.butt_cancel);
        book = (Button) view.findViewById(R.id.butt_ok);
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
                listener.okPress();
                dismiss();
            }
        });
    }

}

