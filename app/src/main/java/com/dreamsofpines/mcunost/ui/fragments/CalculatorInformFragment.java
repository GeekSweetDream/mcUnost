package com.dreamsofpines.mcunost.ui.fragments;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatEditText;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.dreamsofpines.mcunost.R;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;
import java.util.Locale;

/**
 * Created by ThePupsick on 14.08.17.
 */

public class CalculatorInformFragment extends Fragment implements DatePickerDialog.OnDateSetListener{

    private Button mButton;
    private AppCompatEditText dateEdit;
    private TextInputLayout mTextInputLayout;
    private Calendar calendar;

    public static OnClickCancel mListener;

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        calendar.set(Calendar.MONTH,monthOfYear);
        dateEdit.setText(dayOfMonth+" "+ calendar.getDisplayName(Calendar.MONTH,Calendar.SHORT, Locale.getDefault())+" "+year);
    }

    public interface  OnClickCancel{
        void onClicked();
    }

    public void setClickCancelListenner(OnClickCancel listener){
        mListener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = (View) inflater.inflate(R.layout.fragment_inform_calculator,container,false);

        calendar = Calendar.getInstance();
        final DatePickerDialog dpd = DatePickerDialog.newInstance(
                CalculatorInformFragment.this,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        dpd.setVersion(DatePickerDialog.Version.VERSION_1);

        mButton = (Button) view.findViewById(R.id.butt_cancel_book);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onClicked();
            }
        });
        dateEdit = (AppCompatEditText) view.findViewById(R.id.data_edit);
        mTextInputLayout = (TextInputLayout) view.findViewById(R.id.data_tour);
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
        return view;
    }
}
