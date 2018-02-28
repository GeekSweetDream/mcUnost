package com.dreamsofpines.mcunost.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.NestedScrollView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dreamsofpines.mcunost.R;
import com.dreamsofpines.mcunost.data.storage.help.menu.Hotel;
import com.dreamsofpines.mcunost.data.storage.preference.GlobalPreferences;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;
import java.util.Locale;

import static com.yandex.metrica.impl.q.a.F;
import static com.yandex.metrica.impl.q.a.b;
import static com.yandex.metrica.impl.q.a.m;
import static com.yandex.metrica.impl.q.a.q;
import static com.yandex.metrica.impl.q.a.v;
import static java.lang.Enum.valueOf;

/**
 * Created by ThePupsick on 21.02.2018.
 */

public class ConstructorFragment extends Fragment implements DatePickerDialog.OnDateSetListener{


    // Создать свой класс для каждого пункта меню

    private View view;
    private LinearLayout linCalendar,linDay, linGroup, linDinner,linBus,linHotel,linRoute;
    private Calendar calendar;
    private DatePickerDialog dpd;
    private NestedScrollView scrollView;
    private FragmentManager fm;
    private TextView txtDay,txtTeacher,txtGroup, txtBr, txtDin, txtLu,txtMBus,
            txtBusMore,txtHotel,txtDateBeg,txtDateEnd,txtRoute,txtFrom;
    private int countT,countC, countD, countBr, countLu, countDin,countBusMore;
    private String nameHotel,idHotel,idCity,nameRoute;
    private boolean busAdd;




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = (View) inflater.inflate(R.layout.fragment_constructor,container,false);

        calendar = Calendar.getInstance();
        dpd = DatePickerDialog.newInstance(
                ConstructorFragment.this,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        dpd.setVersion(DatePickerDialog.Version.VERSION_1);

        countT = 2;
        countC = 30;
        countD = 1;

        countBr = countD;
        countLu = 0;
        countDin = 0;

        countBusMore = 0;
        busAdd = true;

        nameHotel = "Нет";

        bindView();
        setListener();

        txtTeacher.setText(countT+" преподавателей");
        txtGroup.setText(countC+" группа");
        txtFrom.setText(GlobalPreferences.getPrefUserCity(getContext()));

        showCountDin();
        showBus();
        TextView title = (TextView) getActivity().findViewById(R.id.title_tour);
        title.setText("Конструктор");
        return view;
    }

    private void showCountDin(){
        if(countBr == 1){
            txtBr.setText("Нет");
        }else{
            txtBr.setText("Завтрак "+ countBr);
        }
        if(countLu != 0){
            txtLu.setVisibility(View.VISIBLE);
            txtLu.setText("Обед "+countLu);
        }else{
            txtLu.setVisibility(View.GONE);
        }
        if(countDin != 0){
            txtDin.setVisibility(View.VISIBLE);
            txtDin.setText("Ужин "+countDin);
        }else{
            txtDin.setVisibility(View.GONE);
        }
    }

    private void showBus(){
        if(countBusMore != 0){
            txtBusMore.setVisibility(View.VISIBLE);
            txtBusMore.setText(countBusMore + " доп. автобус");
        }else{
            txtBusMore.setVisibility(View.GONE);
        }
        if(busAdd){
            txtMBus.setText("Встречающий автобус");
        }else{
            txtMBus.setText("Нет встречающего автобуса");
        }
    }

    private void bindView(){
        linCalendar = (LinearLayout) view.findViewById(R.id.linear_calendar);
        linRoute    = (LinearLayout) view.findViewById(R.id.linear_route);
        linDay      = (LinearLayout) view.findViewById(R.id.linear_day);
        linDinner   = (LinearLayout) view.findViewById(R.id.linear_dinner);
        linGroup    = (LinearLayout) view.findViewById(R.id.linear_group);
        linBus      = (LinearLayout) view.findViewById(R.id.linear_bus);
        linHotel    = (LinearLayout) view.findViewById(R.id.linear_hotel);
        txtDay      = (TextView) view.findViewById(R.id.txt_day);
        txtTeacher  = (TextView) view.findViewById(R.id.txt_teacher);
        txtGroup    = (TextView) view.findViewById(R.id.txt_children);
        txtBr       = (TextView) view.findViewById(R.id.dinner_br);
        txtLu       = (TextView) view.findViewById(R.id.dinner_lu);
        txtDin      = (TextView) view.findViewById(R.id.dinner_din);
        txtMBus     = (TextView) view.findViewById(R.id.txt_bus_main);
        txtBusMore  = (TextView) view.findViewById(R.id.txt_bus_more);
        txtHotel    = (TextView) view.findViewById(R.id.txt_hotel);
        txtDateBeg  = (TextView) view.findViewById(R.id.txt_date_from);
        txtDateEnd  = (TextView) view.findViewById(R.id.txt_date_to);
        txtRoute    = (TextView) view.findViewById(R.id.txt_route);
        txtFrom     = (TextView) view.findViewById(R.id.txt_from);
        scrollView  = (NestedScrollView) view.findViewById(R.id.nestedScrollView);
    }

    private void setListener(){
        linRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                CategoriesFragment cF = new CategoriesFragment();
                cF.setOnClickRecyclerListener(new CategoriesFragment.OnClickRecyclerListener() {
                    @Override
                    public void onClicked(Bundle bundle) {
                        nameRoute = bundle.getString("pack_exc");
                        idCity = bundle.getString("id");
                        txtRoute.setText(nameRoute);
                        scrollView.setClickable(true);
                        fm.popBackStack();
                    }
                });
                scrollView.setClickable(false);
                fm.beginTransaction()
                        .add(R.id.frame_layout,cF)
                        .addToBackStack(null)
                        .commit();
            }
        });

        linCalendar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_UP:{
                        dpd.show(getFragmentManager(),"DatePickerDialog");
                    }
                }
                return false;
            }
        });

        linDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                DayFragment dF = new DayFragment();
                dF.setListener(new DayFragment.OnClickListener() {
                    @Override
                    public void onClick(boolean accept, String str) {
                        if(accept) {
                            countD = Integer.parseInt(str.split(" ")[0]);
                            countBr = countD;
                            countLu = 0;
                            countDin = 0;
                            showCountDin();
                            txtDateBeg.setText("Выбрать");
                            txtDateEnd.setVisibility(View.GONE);
                            txtDay.setText(str);
                        }
                        scrollView.setClickable(true);
                        fm.popBackStack();
                    }
                });
                scrollView.setClickable(false);
                fm.beginTransaction()
                        .add(R.id.frame_layout,dF)
                        .addToBackStack(null)
                        .commit();
            }
        });

        linGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                QuantityGroupFragment qGF = new QuantityGroupFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("countT",countT);
                bundle.putInt("countC",countC);
                qGF.setArguments(bundle);
                qGF.setListener(new QuantityGroupFragment.OnClickListener() {
                    @Override
                    public void OnClick(boolean accept,int countT, int countC) {
                        if(accept) {
                            setCountT(countT);
                            setCountC(countC);
                            txtTeacher.setText(countT + " преподавателей");
                            txtGroup.setText(countC + " группа");
                        }
                        scrollView.setClickable(true);
                        fm.popBackStack();
                    }
                });
                scrollView.setClickable(false);
                fm.beginTransaction()
                        .add(R.id.frame_layout,qGF)
                        .addToBackStack(null)
                        .commit();
            }
        });

        linDinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                DinnerFragment dF = new DinnerFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("br",countBr);
                bundle.putInt("lu",countLu);
                bundle.putInt("din",countDin);
                bundle.putInt("day",countD);
                dF.setArguments(bundle);
                dF.setOnClickListener(new DinnerFragment.OnClickListener() {
                    @Override
                    public void onClick(boolean accept,int countBr, int coutnLun, int countDin) {
                        if(accept) {
                            setCountBr(countBr);
                            setCountLu(coutnLun);
                            setCountDin(countDin);
                            showCountDin();
                        }
                        scrollView.setClickable(true);
                        fm.popBackStack();
                    }
                });
                scrollView.setClickable(false);
                fm.beginTransaction()
                        .add(R.id.frame_layout,dF)
                        .addToBackStack(null)
                        .commit();
            }
        });

        linBus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                BusFragment bF = new BusFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("countbus",countBusMore);
                bundle.putBoolean("bus",busAdd);
                bundle.putInt("day",countD);
                bF.setArguments(bundle);
                bF.setListener(new BusFragment.OnClickListener() {
                    @Override
                    public void onClick(boolean accept, boolean mainBus, int moreBus) {
                        if(accept){
                            setBusAdd(mainBus);
                            setCountBusMore(moreBus);
                            showBus();
                        }
                        scrollView.setClickable(true);
                        fm.popBackStack();
                    }
                });
                scrollView.setClickable(false);
                fm.beginTransaction()
                        .add(R.id.frame_layout,bF)
                        .addToBackStack(null)
                        .commit();
            }
        });

        linHotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                HotelFragment hF = new HotelFragment();
                if(!nameHotel.equalsIgnoreCase("Нет")){
                    Bundle bundle = new Bundle();
                    bundle.putString("id",idHotel);
                    hF.setArguments(bundle);
                }
                hF.setOnClickListener(new HotelFragment.OnClickListener() {
                    @Override
                    public void onClick(boolean accept, String idHotel, String hotel) {
                        if(accept){
                            setIdHotel(idHotel);
                            setNameHotel(hotel);
                            txtHotel.setText(hotel);
                        }
                        scrollView.setClickable(true);
                        fm.popBackStack();
                    }
                });
                scrollView.setClickable(false);
                fm.beginTransaction()
                        .add(R.id.frame_layout,hF)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    public void setFragmentManager(FragmentManager fm){
        this.fm = fm;
    }


    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        calendar.set(Calendar.MONTH,monthOfYear);

        Calendar cl = Calendar.getInstance();
        cl.set(Calendar.YEAR,year);
        cl.set(Calendar.MONTH,monthOfYear);
        cl.set(Calendar.DAY_OF_MONTH,dayOfMonth+countD-1);
        txtDateBeg.setText(dayOfMonth+" "+ calendar.getDisplayName(Calendar.MONTH,Calendar.SHORT, Locale.getDefault())+" "+year);
        txtDateEnd.setText(cl.get(Calendar.DAY_OF_MONTH) + " "+cl.getDisplayName(Calendar.MONTH,Calendar.SHORT,Locale.getDefault())+ " "+
                cl.get(Calendar.YEAR));
        txtDateEnd.setVisibility(View.VISIBLE);
    }

    public int getCountT() {
        return countT;
    }

    public void setCountT(int countT) {
        this.countT = countT;
    }

    public int getCountC() {
        return countC;
    }

    public void setCountC(int countC) {
        this.countC = countC;
    }

    public int getCountD() {
        return countD;
    }

    public void setCountD(int countD) {
        this.countD = countD;
    }

    public int getCountBr() {
        return countBr;
    }

    public void setCountBr(int countBr) {
        this.countBr = countBr;
    }

    public int getCountLu() {
        return countLu;
    }

    public void setCountLu(int countLu) {
        this.countLu = countLu;
    }

    public int getCountDin() {
        return countDin;
    }

    public void setCountDin(int countDin) {
        this.countDin = countDin;
    }

    public int getCountBusMore() {
        return countBusMore;
    }

    public void setCountBusMore(int countBusMore) {
        this.countBusMore = countBusMore;
    }

    public boolean isBusAdd() {
        return busAdd;
    }

    public void setBusAdd(boolean busAdd) {
        this.busAdd = busAdd;
    }

    public String getIdHotel() {
        return idHotel;
    }

    public void setIdHotel(String idHotel) {
        this.idHotel = idHotel;
    }

    public String getNameHotel() {
        return nameHotel;
    }

    public void setNameHotel(String nameHotel) {
        this.nameHotel = nameHotel;
    }
}
