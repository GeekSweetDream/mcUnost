package com.dreamsofpines.mcunost.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatEditText;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.dreamsofpines.mcunost.R;
import com.dreamsofpines.mcunost.data.storage.preference.GlobalPreferences;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;
import java.util.Locale;

/**
 * Created by ThePupsick on 14.08.17.
 */

public class CalculatorInformFragment extends Fragment implements DatePickerDialog.OnDateSetListener{

    private Button mButtonCancel,mButtonOk;
    private AppCompatEditText dateEdit,countSchool,countTeacher;
    private Calendar calendar;

    public static OnClickCancel mListener;

    public interface  OnClickCancel{
        void onClicked();
    }
    public void setClickCancelListenner(OnClickCancel listener){
        mListener = listener;
    }

    public static OnClickOk sOnClickOk;

    public interface OnClickOk{
        void onClicked(boolean isLogin);
    }

    public void setClickOkListenner(OnClickOk listenner){ sOnClickOk = listenner;}

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        calendar.set(Calendar.MONTH,monthOfYear);
        dateEdit.setText(dayOfMonth+" "+ calendar.getDisplayName(Calendar.MONTH,Calendar.SHORT, Locale.getDefault())+" "+year);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = (View) inflater.inflate(R.layout.fragment_inform_calculator,container,false);

        calendar = Calendar.getInstance();
        final DatePickerDialog dpd = DatePickerDialog.newInstance(
                CalculatorInformFragment.this,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        dpd.setVersion(DatePickerDialog.Version.VERSION_1);

        countSchool = (AppCompatEditText) view.findViewById(R.id.count_pupil_edit);
        countTeacher = (AppCompatEditText) view.findViewById(R.id.count_teacher_edit);

        mButtonCancel = (Button) view.findViewById(R.id.butt_cancel_book);
        mButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onClicked();
            }
        });
        dateEdit = (AppCompatEditText) view.findViewById(R.id.data_edit);
        dateEdit.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        dpd.show(getFragmentManager(),"DatePickerDialog");
                }
                return false;
            }
        });
        mButtonOk = (Button) view.findViewById(R.id.butt_book_tour);
        mButtonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean allFill = true;
                if(dateEdit.getText().toString().equalsIgnoreCase("")){
                    allFill = false;
                    dateEdit.setError("Выберите дату тура");
                }
                if(countSchool.getText().toString().equalsIgnoreCase("")){
                    allFill = false;
                    countSchool.setError("Введите кол-во школьников");
                }
                if(countTeacher.getText().toString().equalsIgnoreCase("")){
                    allFill = false;
                    countTeacher.setError("Введите кол-во преподавателей");
                }
                if(allFill){
                    if(1 == GlobalPreferences.getPrefAddUser(getActivity())){
                        sOnClickOk.onClicked(true);
                    }else{
                        sOnClickOk.onClicked(false);
                    }
                }
            }
        });
        return view;
    }
}
