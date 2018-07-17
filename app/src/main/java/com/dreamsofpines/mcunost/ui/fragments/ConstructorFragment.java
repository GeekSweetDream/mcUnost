package com.dreamsofpines.mcunost.ui.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dreamsofpines.mcunost.R;
import com.dreamsofpines.mcunost.data.network.api.Constans;
import com.dreamsofpines.mcunost.data.network.api.RequestSender;
import com.dreamsofpines.mcunost.data.storage.models.Excursion;
import com.dreamsofpines.mcunost.data.storage.models.Order;
import com.dreamsofpines.mcunost.data.storage.preference.GlobalPreferences;
import com.dreamsofpines.mcunost.ui.activities.CategoriesActivity;
import com.dreamsofpines.mcunost.ui.customView.SuccsesBookView;
import com.dreamsofpines.mcunost.ui.customView.helpers.MainHelper;
import com.dreamsofpines.mcunost.ui.dialog.AuthDialogFragment;
import com.dreamsofpines.mcunost.ui.dialog.CityDialogFragment;
import com.dreamsofpines.mcunost.ui.dialog.TotalDialogFragment;
import com.squareup.picasso.Picasso;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by ThePupsick on 21.02.2018.
 */

public class ConstructorFragment extends Fragment implements DatePickerDialog.OnDateSetListener, TotalDialogFragment.OnClickButton{


    // Создать свой класс для каждого пункта меню

    private View view;
    private LinearLayout linCalendar,linDay, linGroup, linDinner,linBus,linHotel,linRoute,linTravel,linExcur;
    private Calendar calendar;
    private DatePickerDialog dpd;
    private FragmentManager fm;
    private Button butOrder, help;
    private TextView txtDay,txtTeacher,txtGroup, txtBr, txtDin, txtLu,txtMBus,
            txtBusMore,txtHotel,txtDateBeg,txtDateEnd,txtRoute,txtFrom,txtTravel,txtExc;
    private int countT,countC, countD, countBr, countLu, countDin,countBusMore,countBus4hour;
    private String nameHotel,idHotel,idCity,nameRoute;
    private List<Excursion> mExcursionList;
    private boolean busAdd, addTrain;
    private CategoriesFragment cF;
    private DayFragment dF;
    private QuantityGroupFragment qGF;
    private DinnerFragment dinF;
    private BusFragment bF;
    private HotelFragment hF;
    private TravelWayFragment twF;
    private ExcursionFragment excF;
    private MainHelper mMainHelper;
    private SuccsesBookView succesView;
    private ImageView mImageView;
    private TextView title;

    private static ConstructorFragment sConstructorFragment;

    public static ConstructorFragment getInstance(FragmentManager fm){
        if(sConstructorFragment == null){
            sConstructorFragment = new ConstructorFragment();
        }
        sConstructorFragment.setFragmentManager(fm);
        return sConstructorFragment;
    }

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

        countBr = countD-1;
        countLu = 1;
        countDin = 0;

        countBusMore = 0;
        countBus4hour = 0;
        busAdd = true;
        addTrain = true;

        nameHotel = "Нет";

        bindView();
        setListener();

        Picasso.with(getContext()).load("file:///android_asset/main44.jpg").into(mImageView);

        txtTeacher.setText(countT+" преподавателей");
        txtGroup.setText(countC+" группа");
        txtFrom.setText(GlobalPreferences.getPrefUserCity(getContext()));
        txtFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CityDialogFragment cF = CityDialogFragment.newInstance(new CityDialogFragment.OnCityChangedListener() {
                    @Override
                    public void onChange(String city) {
                        txtFrom.setText(city);
                    }
                },txtFrom.getText().toString());
                cF.show(getFragmentManager(),null);
            }
        });


        setActiveOrDisactive(linHotel,false);
        showCountDin();
        showBus();

//        setHelpButton();
        if(GlobalPreferences.getPrefHelpMain(getContext())!=1){
            mMainHelper.show();
            GlobalPreferences.setPrefHelpMain(getContext(),1);
        }
//        title.setText("Конструктор");

        return view;
    }

    private void setHelpButton(){
        help = (Button) getActivity().findViewById(R.id.button_help);
        help.setVisibility(View.VISIBLE);
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMainHelper.show();
            }
        });
    }

    private void showCountDin(){
        txtBr.setText("Выбрать");
        txtLu.setVisibility(View.GONE);
        txtDin.setVisibility(View.GONE);
        if(countBr > 0){
            txtBr.setText("Завтрак "+countBr);
        }
        if(countLu > 0){
            if(txtBr.getText().toString().equalsIgnoreCase("Выбрать")){
                txtBr.setText("Обед "+countLu);
            }else{
                txtLu.setVisibility(View.VISIBLE);
                txtLu.setText("Обед "+countLu);
            }
        }
        if(countDin > 0){
            if(txtBr.getText().toString().equalsIgnoreCase("Выбрать")){
                txtBr.setText("Ужин "+countDin);
            }else{
                if(txtLu.getVisibility() == View.GONE) {
                    txtLu.setVisibility(View.VISIBLE);
                    txtLu.setText("Ужин " + countDin);
                }else{
                    txtDin.setVisibility(View.VISIBLE);
                    txtDin.setText("Ужин "+countDin);
                }
            }
        }
    }

    private void showBus(){
        if(countBusMore != 0){
            txtBusMore.setVisibility(View.VISIBLE);
            txtBusMore.setText(countBusMore + countBus4hour + " доп. автобус");
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
        linTravel   = (LinearLayout) view.findViewById(R.id.linear_travel);
        linExcur    = (LinearLayout) view.findViewById(R.id.linear_exc);
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
        txtTravel   = (TextView) view.findViewById(R.id.txt_travel);
        txtExc      = (TextView) view.findViewById(R.id.txt_exc);
        butOrder    = (Button) view.findViewById(R.id.butt_order);
        mMainHelper = (MainHelper) view.findViewById(R.id.main_helper);
        succesView  = (SuccsesBookView) view.findViewById(R.id.succes_view);
        mImageView  = (ImageView) view.findViewById(R.id.asd);
        title       = (TextView) getActivity().findViewById(R.id.title_tour);
    }

    private void setActiveOrDisactive(LinearLayout layout,boolean isActive){
        layout.setClickable(isActive);
        layout.setAlpha((float) (isActive?1:0.5));
    }


    private void setListener(){
        linRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                if(cF == null) {
                    cF = new CategoriesFragment();
                    cF.setOnClickRecyclerListener(new CategoriesFragment.OnClickRecyclerListener() {
                        @Override
                        public void onClicked(Bundle bundle) {
                            nameRoute = bundle.getString("pack_exc");
                            idCity = bundle.getString("id");
                            txtRoute.setText(nameRoute);
                            txtExc.setText("Выбрать");
                            txtHotel.setText("Выбрать");
                            idHotel = String.valueOf(-1);
                            nameHotel = "";
                            mExcursionList = null;
                            linRoute.setBackgroundResource(R.color.transparent);
                            fm.popBackStack();
                            title.setText("Конструктор");
//                            setHelpButton();
                        }
                    });
//                    setHelpButton();
                }

                Bundle bundle = new Bundle();
                bundle.putString("city",txtFrom.getText().toString());
                cF.setArguments(bundle);
                fm.beginTransaction()
                        .setCustomAnimations(R.anim.slide_left_2_obj,R.anim.slide_right_2_obj,
                                R.anim.slide_left_2_obj,R.anim.slide_right_2_obj)
                        .add(R.id.frame_main,cF)
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
               if(dF==null) {
                   dF = new DayFragment();
//                   dF.setListener(new DayFragment.OnClickListener() {
//                       @Override
//                       public void onClick(boolean accept, String str) {
//                           if (accept) {
//                               countD = Integer.parseInt(str.split(" ")[0]);
//                               countBr = countD - 1;
//                               countLu = countD == 1 ? 1 : 0;
//                               countDin = 0;
//                               countBusMore = 0;
//                               countBus4hour = 0;
//                               showBus();
//                               busAdd = true;
//                               addTrain = true;
//                               showCountDin();
//                               txtDateBeg.setText("Выбрать");
//                               txtDateEnd.setVisibility(View.GONE);
//                               txtDay.setText(str);
//                               txtExc.setText("Выбрать");
//                               linRoute.setBackgroundResource(R.color.transparent);
//                               linCalendar.setBackgroundResource(R.color.transparent);
//                               linHotel.setBackgroundResource(R.color.transparent);
//                               linExcur.setBackgroundResource(R.color.transparent);
//                               mExcursionList = null;
//                               setActiveOrDisactive(linHotel, countD > 1);
//                           }
//                           fm.popBackStack();
//                           title.setText("Конструктор");
//                           setHelpButton();
//                       }
//                   });
               }
               Bundle bundle = new Bundle();
                bundle.putInt("day",countD);
                dF.setArguments(bundle);
                fm.beginTransaction()
                        .setCustomAnimations(R.anim.slide_left_2_obj,R.anim.slide_right_2_obj,
                                R.anim.slide_left_2_obj,R.anim.slide_right_2_obj)
                        .add(R.id.frame_layout,dF)
                        .addToBackStack(null)
                        .commit();
            }
        });

        linGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                if(qGF ==null) {
                    qGF = new QuantityGroupFragment();
                    qGF.setListener(new QuantityGroupFragment.OnClickListener() {
                        @Override
                        public void OnClick( int countT, int countC) {
//                            if (accept) {
                                setCountT(countT);
                                setCountC(countC);
                                txtTeacher.setText(countT + " преподавателей");
                                txtGroup.setText(countC + " группа");
//                            }
                            fm.popBackStack();
                            title.setText("Конструктор");
                            setHelpButton();
                        }
                    });
                }
                Bundle bundle = new Bundle();
                bundle.putInt("countT", countT);
                bundle.putInt("countC", countC);
                qGF.setArguments(bundle);
                fm.beginTransaction()
                        .setCustomAnimations(R.anim.slide_left_2_obj,R.anim.slide_right_2_obj,
                                R.anim.slide_left_2_obj,R.anim.slide_right_2_obj)
                        .add(R.id.frame_layout,qGF)
                        .addToBackStack(null)
                        .commit();
            }
        });

        linDinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                if(dinF == null) {
                    dinF = new DinnerFragment();
//                    dinF.setOnClickListener(new DinnerFragment.OnClickListener() {
//                        @Override
//                        public void onClick(boolean accept, int countBr, int coutnLun, int countDin) {
//                            if (accept) {
//                                setCountBr(countBr);
//                                setCountLu(coutnLun);
//                                setCountDin(countDin);
//                                linDinner.setBackgroundResource(R.color.transparent);
//                                showCountDin();
//                            }
//                            fm.popBackStack();
//                            title.setText("Конструктор");
//                            setHelpButton();
//                        }
//                    });
                }
                Bundle bundle = new Bundle();
                bundle.putInt("br", countBr);
                bundle.putInt("lu", countLu);
                bundle.putInt("din", countDin);
                bundle.putInt("day", countD);
                dinF.setArguments(bundle);

                fm.beginTransaction()
                        .setCustomAnimations(R.anim.slide_left_2_obj,R.anim.slide_right_2_obj,
                                R.anim.slide_left_2_obj,R.anim.slide_right_2_obj)

                        .add(R.id.frame_layout,dinF)
                        .addToBackStack(null)
                        .commit();
            }
        });

        linBus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                bF = new BusFragment();
//                bF.setListener(new BusFragment.OnClickListener() {
//                        @Override
//                        public void onClick(boolean accept, boolean mainBus, int moreBus,int more4Bus) {
//                            if (accept) {
//                                setBusAdd(mainBus); 
//                                setCountBusMore(moreBus);
//                                setCountBus4hour(more4Bus);
//                                showBus();
//                            }
//                            fm.popBackStack();
//                            title.setText("Конструктор");
//                            setHelpButton();
//                        }
//                    });
                Bundle bundle = new Bundle();
                bundle.putInt("countbus", countBusMore);
                bundle.putInt("count4Bus",countBus4hour);
                bundle.putBoolean("bus", busAdd);
                bundle.putInt("day", countD);
                bF.setArguments(bundle);
                fm.beginTransaction()
                        .setCustomAnimations(R.anim.slide_left_2_obj,R.anim.slide_right_2_obj,
                                R.anim.slide_left_2_obj,R.anim.slide_right_2_obj)
                        .add(R.id.frame_layout,bF)
                        .addToBackStack(null)
                        .commit();
            }
        });

        linHotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                if(!txtRoute.getText().toString().equalsIgnoreCase("Выбрать")) {
                    hF = new HotelFragment();
//                    hF.setOnClickListener(new HotelFragment.OnClickListener() {
//                        @Override
//                        public void onClick(boolean accept, String idHotel, String hotel) {
//                            if (accept) {
//                                setIdHotel(idHotel);
//                                setNameHotel(hotel);
//                                txtHotel.setText(hotel);
//                                linHotel.setBackgroundResource(R.color.transparent);
//                            }
//                            fm.popBackStack();
//                            title.setText("Конструктор");
//                            setHelpButton();
//                        }
//                    });
                    Bundle bundle = new Bundle();
                    bundle.putString("idCity",idCity);
                    if (!nameHotel.equalsIgnoreCase("Нет")) {
                        bundle.putString("id", idHotel);
                    }else{
                        bundle.putString("id", String.valueOf(-1));
                    }
                    hF.setArguments(bundle);
                    fm.beginTransaction()
                            .setCustomAnimations(R.anim.slide_left_2_obj, R.anim.slide_right_2_obj,
                                    R.anim.slide_left_2_obj, R.anim.slide_right_2_obj)
                            .add(R.id.frame_layout, hF)
                            .addToBackStack(null)
                            .commit();
                }else{
                    Toast.makeText(getContext(),"Сначала выберите направление!",Toast.LENGTH_LONG).show();
                    linRoute.setBackgroundResource(R.color.md_orange_400);
                }
            }
        });
        linTravel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(twF == null) {
                    twF = new TravelWayFragment();
                    twF.setOnClickListener(new TravelWayFragment.OnClickListener() {
                        @Override
                        public void onClick(int choose) {
//                            if (accept) {
                                addTrain = choose == 0;
                                txtTravel.setText(choose == 0 ? "Поезд" : "Самолет");
//                            }
                            fm.popBackStack();
                            title.setText("Конструктор");
                            setHelpButton();
                        }
                    });
                }
                Bundle bundle = new Bundle();
                bundle.putInt("travel", addTrain ? 0 : 1);
                twF.setArguments(bundle);

                fm.beginTransaction()
                        .setCustomAnimations(R.anim.slide_left_2_obj,R.anim.slide_right_2_obj,
                                R.anim.slide_left_2_obj,R.anim.slide_right_2_obj)
                        .add(R.id.frame_layout,twF)
                        .addToBackStack(null)
                        .commit();
            }
        });

        linExcur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!txtRoute.getText().toString().equalsIgnoreCase("Выбрать")) {
                    excF = new ExcursionFragment();
//                    excF.setOnClickListener(new ExcursionFragment.OnClickListener() {
//                        @Override
//                        public void onClick(boolean accept, List<Excursion> list) {
//                            if (accept) {
//                                mExcursionList = list;
//                                txtExc.setText("Выбрать");
//                                if (mExcursionList.size() > 0) {
//                                    txtExc.setText("Выбрано " + mExcursionList.size());
//                                }
//                                linExcur.setBackgroundResource(R.color.transparent);
//
//                            }
//                            fm.popBackStack();
//                            title.setText("Конструктор");
//                            setHelpButton();
//                        }
//                    });
                    Bundle bundle = new Bundle();
                    bundle.putInt("maxday", countD);
                    bundle.putString("idcity", idCity);
                    if (mExcursionList != null) {
                        bundle.putInt("size", mExcursionList.size());
                        for (int i = 0; i < mExcursionList.size(); ++i) {
                            bundle.putString(String.valueOf(i), mExcursionList.get(i).getId());
                        }
                    } else {
                        bundle.putInt("size", 0);
                    }
                    excF.setArguments(bundle);
                    fm.beginTransaction()
                            .setCustomAnimations(R.anim.slide_left_2_obj, R.anim.slide_right_2_obj,
                                    R.anim.slide_left_2_obj, R.anim.slide_right_2_obj)
                            .add(R.id.frame_layout, excF)
                            .addToBackStack(null)
                            .commit();
                }else{
                    Toast.makeText(getContext(),"Сначала выберите направление!",Toast.LENGTH_LONG).show();
                    linRoute.setBackgroundResource(R.color.md_orange_400);
                }
            }
        });

        butOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isAllFill()){

                    List<Excursion> allExcursionList = mExcursionList;

                    Order order = new Order(idCity,txtDateBeg.getText()+"-"+txtDateEnd.getText(),
                            String.valueOf(countC),String.valueOf(countT),allExcursionList);
                    order.setTour(txtRoute.getText().toString());
                    order.setAddTrain(addTrain);
                    order.setCountBr(countBr);
                    order.setCountLu(countLu);
                    order.setCountDin(countDin);
                    order.setFromCity(txtFrom.getText().toString());
                    order.setCountBusMeet(String.valueOf((busAdd)?2:0));
                    order.setCountAllDayBus(String.valueOf(countBusMore));
                    order.setCount4Bus(String.valueOf(countBus4hour));
                    order.setContext(getContext());
                    order.setHotel(nameHotel);
                    order.setCountDay(String.valueOf(countD));
                    if(linHotel.isClickable()){
                        order.setHotel(nameHotel);
                        order.setIdHotel(String.valueOf(idHotel));
                    }
                    TotalDialogFragment td = TotalDialogFragment.newInstance(ConstructorFragment.this, order);
                    td.show(getFragmentManager(),"td");
                }else {
                    Toast.makeText(getContext(),"У вас есть незаполненные поля!",Toast.LENGTH_LONG).show();

                }
            }
        });


    }

    private boolean isAllFill(){
        boolean fill = true;
//        Animation animation = new AlphaAnimation((float) 0,(float) 1);  // на потом
//        animation.setDuration(1000);
        if(txtRoute.getText().toString().equalsIgnoreCase("Выбрать")){
            linRoute.setBackgroundResource(R.color.md_orange_400);
            fill = false;
        }
        if(txtDateBeg.getText().toString().equalsIgnoreCase("Выбрать")){
            linCalendar.setBackgroundResource(R.color.md_orange_400);
            fill = false;
        }
        if(linHotel.isClickable() &&
                txtHotel.getText().toString().equalsIgnoreCase("Выбрать")){
            linHotel.setBackgroundResource(R.color.md_orange_400);
            fill = false;
        }
        if(txtBr.getText().toString().equalsIgnoreCase("Выбрать")){
            linDinner.setBackgroundResource(R.color.md_orange_400);
            fill = false;
        }
        if(txtExc.getText().toString().equalsIgnoreCase("Выбрать")){
            linExcur.setBackgroundResource(R.color.md_orange_400);
            fill = false;
        }
        return fill;
    }

    private void addOrderTask(Order order){
        AddNewOrderTask addNewOrderTask = new AddNewOrderTask();
        addNewOrderTask.execute(order.getJson());
        succesView.show();
    }


    // От TotalDialogFragment
    @Override
    public void OnClick(boolean accepted, final Order order) {
        if (accepted) {
            if (1 == GlobalPreferences.getPrefAddUser(getActivity())) {
                addOrderTask(order);
            } else {
                AuthDialogFragment.OnClickedReg regListener = new AuthDialogFragment.OnClickedReg() {
                    @Override
                    public void onClickedReg() {
                        RegistrFragment rF = new RegistrFragment();
                        rF.setOnClickRegistrListener(new RegistrFragment.OnClickCancelListener() {
                            @Override
                            public void onClicked(boolean isLogUp) {
                                if(isLogUp){
                                    ((CategoriesActivity) getActivity()).updateAllMenu();
                                    addOrderTask(order);
                                }
                                fm.popBackStack();
                            }
                        });
                        fm.beginTransaction()
                                .add(R.id.frame_layout,rF)
                                .addToBackStack(null)
                                .setCustomAnimations(R.anim.slide_left_2_obj,R.anim.slide_right_2_obj,
                                        R.anim.slide_left_2_obj,R.anim.slide_right_2_obj)
                                .commit();
                    }
                };
                AuthDialogFragment.OnSuccessAuth successAuthListener = new AuthDialogFragment.OnSuccessAuth() {
                    @Override
                    public void onSuccess() {
                        ((CategoriesActivity) getActivity()).updateAllMenu();
                        addOrderTask(order);
                    }
                };
                AuthDialogFragment adF = AuthDialogFragment.newInstace(regListener,successAuthListener);
                adF.show(getFragmentManager(),"adF");
            }
        }
    }



    public void setFragmentManager(FragmentManager fm){
        this.fm = fm;
    }


    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        linCalendar.setBackgroundResource(R.color.transparent);
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

    private void showSuccsesView(boolean add){
        if(add) {
            ((CategoriesActivity) getActivity()).setUpdateBadge();
            succesView.stopping();
        }else{
            succesView.errorStoping();
        }
    }

    public void updateCity(){
        txtFrom.setText(GlobalPreferences.getPrefUserCity(getContext()));
    }


    private class AddNewOrderTask extends AsyncTask<JSONObject,Void,Boolean> {

        private String errorMsg =  "Ooops! Проблемы сети, попробуйте позже! =)";

        @Override
        protected Boolean doInBackground(JSONObject... jsonObjects) {
            boolean success=true;
            JSONObject js = jsonObjects[0];
            try {
                js.put("idCustomer", GlobalPreferences.getPrefIdUser(getContext()));
                js.put("idStatus", 1);
                String answer = RequestSender.POST(getContext(), Constans.URL.ORDER.ADD_NEW_ORDER,js,true);
                JSONObject answerJs = new JSONObject(answer);
                if(!answerJs.getString("result").equalsIgnoreCase("success")){
                    success = false;
                }
            }catch (JSONException e){
                Log.i("Constructor","Create JSON Order! Error message: " + e.getMessage());
                success = false;
            }catch (NullPointerException e){
                Log.i("Constructor","Empty answer! Error message: " + e.getMessage());
                success = false;
            }
            return success;
        }


        @Override
        protected void onPostExecute(Boolean result) {
            if (result){
                showSuccsesView(true);
            }else {
                showSuccsesView(false);
                Toast.makeText(getActivity(),errorMsg, Toast.LENGTH_LONG)
                        .show();
            }
        }
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

    public int getCountBus4hour() {
        return countBus4hour;
    }

    public void setCountBus4hour(int countBus4hour) {
        this.countBus4hour = countBus4hour;
    }
}
