package com.dreamsofpines.mcunost.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;

import com.dreamsofpines.mcunost.R;
import com.dreamsofpines.mcunost.data.storage.models.Order;
import com.dreamsofpines.mcunost.data.storage.preference.GlobalPreferences;
import com.dreamsofpines.mcunost.ui.dialog.TotalDialogFragment;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.json.JSONObject;

import java.util.Calendar;
import java.util.Locale;

/**
 * Created by ThePupsick on 14.08.17.
 */

public class  CalculatorInformFragment extends Fragment implements DatePickerDialog.OnDateSetListener,TotalDialogFragment.OnClickButton{

    private Button mButtonCancel,mButtonOk;
    private AppCompatEditText dateEdit,countSchool,countTeacher;
    private Calendar calendar;
    private TextInputLayout data, school, teacher;
    private Bundle bundle;
    private DatePickerDialog dpd;
    private Order mOrder;
    private TotalDialogFragment tD;

    public static OnClickCancel mListener;
    public static OnClickOk sOnClickOk;

    @Override
    public void OnClick(boolean accepted, Order order) {

    }


    public interface  OnClickCancel{
        void onClicked();
    }

    public interface OnClickOk{
        void onClicked(boolean isLogin,Order order);
    }

    public void setClickOkListenner(OnClickOk listenner){ sOnClickOk = listenner;}
    public void setClickCancelListenner(OnClickCancel listener){
        mListener = listener;
    }

//    public void OnClick(boolean accepted) {
//        if(accepted){
//            checkRegistration(mOrder);
//        }
//    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        calendar.set(Calendar.MONTH,monthOfYear);
        int countDay = Integer.parseInt(bundle.getString("day"));
        Calendar cl = Calendar.getInstance();
        cl.set(Calendar.YEAR,year);
        cl.set(Calendar.MONTH,monthOfYear);
        cl.set(Calendar.DAY_OF_MONTH,dayOfMonth+countDay-1);
        dateEdit.setText(dayOfMonth+" "+ calendar.getDisplayName(Calendar.MONTH,Calendar.SHORT, Locale.getDefault())+" "+year
                +" - "+cl.get(Calendar.DAY_OF_MONTH) + " "+cl.getDisplayName(Calendar.MONTH,Calendar.SHORT,Locale.getDefault())+ " "+
                cl.get(Calendar.YEAR) );
        dateEdit.setError(null);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = (View) inflater.inflate(R.layout.fragment_inform_calculator,container,false);

        bundle = getArguments();
        calendar = Calendar.getInstance();
        dpd = DatePickerDialog.newInstance(
                CalculatorInformFragment.this,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        dpd.setVersion(DatePickerDialog.Version.VERSION_1);

        bindView(view);
        setListenerView();

        return view;
    }

    private void bindView(View view){
        data = (TextInputLayout) view.findViewById(R.id.data_tour);
        school = (TextInputLayout) view.findViewById(R.id.count_pupil);
        teacher = (TextInputLayout) view.findViewById(R.id.count_teacher);
        countSchool = (AppCompatEditText) view.findViewById(R.id.count_pupil_edit);
        countTeacher = (AppCompatEditText) view.findViewById(R.id.count_teacher_edit);
        mButtonCancel = (Button) view.findViewById(R.id.butt_cancel_book);
        dateEdit = (AppCompatEditText) view.findViewById(R.id.data_edit);
        mButtonOk = (Button) view.findViewById(R.id.butt_book_tour);
    }

    private void setListenerView(){
        data.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!b) hideKeybord();
            }
        });


        school.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!b) hideKeybord();
            }
        });

        teacher.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!b) hideKeybord();
            }
        });

        mButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onClicked();
            }
        });

        dateEdit.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_UP:
                        dpd.show(getFragmentManager(),"DatePickerDialog");
                }
                return false;
            }
        });
        countSchool.setText("30");
        countTeacher.setText("2");
        mButtonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean allFill = true;
                if(dateEdit.getText().toString().equalsIgnoreCase("")){
                    allFill = false;
                    dateEdit.setError("Выберите дату тура");
                }
                if(countSchool.getText().toString().equalsIgnoreCase("")
                        && checkInputDataMoreZero(countSchool.getText().toString())){
                    allFill = false;
                    countSchool.setError("Ошибка в числе школьников");
                }
                if(countTeacher.getText().toString().equalsIgnoreCase("")
                        && checkInputDataMoreZero(countTeacher.getText().toString())){
                    allFill = false;
                    countTeacher.setError("Ошибка в числе преподавателей");
                }
                if(allFill){
                    JSONObject js = createJson(countTeacher.getText().toString(),
                            countSchool.getText().toString(),bundle.getString("idPack"));
//                    tD = TotalDialogFragment.newInstance(CalculatorInformFragment.this,countSchool.getText().toString(),
//                            countTeacher.getText().toString(),dateEdit.getText().toString());
//                    tD.show(getFragmentManager(),null);
                    if(js!=null) {
//                        CalculateTask calculateTask = new CalculateTask();
//                        calculateTask.execute(js);
                    }else{
                        Toast.makeText(getContext(),"Что-то пошло не так, попробуйте позднее! =)",Toast.LENGTH_LONG)
                                .show();
                    }
                }
            }
        });
    }

    private boolean checkInputDataMoreZero(String count){
        boolean clean = true;
        try{
            int number = Integer.parseInt(count);
            if(number<=0){
                clean = false;
            }
        }catch (Exception e){
            clean = false;
        }
        return clean;
    }

    private void checkRegistration(Order order){
        if (1 == GlobalPreferences.getPrefAddUser(getActivity())) {
            sOnClickOk.onClicked(true, order);
        } else {
            sOnClickOk.onClicked(false, order);
        }

    }

    public void hideKeybord(){
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getActivity().getWindow().getDecorView().getWindowToken(),0);
    }

    private JSONObject createJson(String leadCount, String studentCount, String idPack){
        JSONObject js = new JSONObject();
        try {
            js.put("id", idPack);
            js.put("countLead",leadCount);
            js.put("countStudent",studentCount);
        }catch (Exception e){
            Log.i("CalculatorFragment", "Error create json! Error text: "+e.getMessage());
        }
        return js;
    }


//    private void successAnswer(String cost){
//        if(tD!=null){
//            tD.setOrder(cost);
//        }
//        Order ord = new Order(bundle.getString("pack_exc"),dateEdit.getText().toString(),cost,
//                countSchool.getText().toString(),countTeacher.getText().toString());
//        mOrder = ord;
//    }



}
