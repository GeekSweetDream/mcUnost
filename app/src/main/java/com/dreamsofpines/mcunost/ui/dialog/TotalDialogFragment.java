package com.dreamsofpines.mcunost.ui.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dreamsofpines.mcunost.R;
import com.dreamsofpines.mcunost.data.storage.help.menu.Order;
import com.dreamsofpines.mcunost.ui.fragments.SettingFragment;
import com.wang.avi.AVLoadingIndicatorView;

/**
 * Created by ThePupsick on 31.08.17.
 */

public class TotalDialogFragment extends DialogFragment {
    private String totalCost, date, pupil,teacher;
    private TextView data, pupilView, teacherView, costView;
    private Button cancel,ok;
    private AVLoadingIndicatorView ind;

    public OnClickButton mListener;

    public interface OnClickButton{
        void OnClick(boolean accepted);
    }

    public static TotalDialogFragment newInstance(OnClickButton callback, String pupil, String teacher, String date){
        TotalDialogFragment tD = new TotalDialogFragment();
        tD.initialize(callback,pupil,teacher,date);
        return tD;
    }

    public void initialize(OnClickButton callback,String pupil,String teacher,String date){
        this.mListener = callback;
        this.pupil = pupil;
        this.teacher = teacher;
        this.date = date;
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
        View view = inflater.inflate(R.layout.dialog_total_cost,container,false);

        bindView(view);
        data.setText(date);
        pupilView.setText(pupil);
        teacherView.setText(teacher);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.OnClick(true);
                dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.OnClick(false);
                dismiss();
            }
        });
        ind.show();
        return view;
    }

    public void setOrder(String cost){
        costView.setText(cost);
        ind.hide();
        costView.setVisibility(View.VISIBLE);
    }

    private void bindView(View view){
        data = (TextView) view.findViewById(R.id.dialog_total_date);
        pupilView = (TextView) view.findViewById(R.id.dialog_total_quan_pupil);
        teacherView = (TextView) view.findViewById(R.id.dialog_total_quan_teacher);
        costView = (TextView) view.findViewById(R.id.dialog_total_cost);
        ok = (Button) view.findViewById(R.id.dialog_total_ok_butt);
        cancel = (Button) view.findViewById(R.id.dialog_total_cancel_butt);
        ind = (AVLoadingIndicatorView) view.findViewById(R.id.total_indicator);
    }

}

