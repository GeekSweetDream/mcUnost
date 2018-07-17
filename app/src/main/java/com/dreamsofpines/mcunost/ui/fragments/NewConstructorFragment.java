package com.dreamsofpines.mcunost.ui.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.dreamsofpines.mcunost.R;
import com.dreamsofpines.mcunost.data.storage.mBarItem;
import com.dreamsofpines.mcunost.data.storage.models.Excursion;
import com.dreamsofpines.mcunost.data.storage.models.Order;
import com.dreamsofpines.mcunost.data.storage.preference.GlobalPreferences;
import com.dreamsofpines.mcunost.data.utils.ScreenUtils;
import com.dreamsofpines.mcunost.ui.customView.ViewItemConstructor;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class NewConstructorFragment extends Fragment{



    private View view;
    private ViewItemConstructor travItem;
    private ViewItemConstructor dinItem;
    private ViewItemConstructor groupItem;
    private ViewItemConstructor cityToItem;
    private ViewItemConstructor hotelItem;
    private ViewItemConstructor dateItem;
    private ViewItemConstructor dayItem;
    private ViewItemConstructor transItem;
    private ViewItemConstructor excurItem;
    private ViewItemConstructor cityFromItem;
    private ViewItemConstructor current;
    private Button btnOrder;

    private DatePickerDialog dpd;
    private FragmentManager fm;
    private Order order;
    private static NewConstructorFragment sNewConstructorFragment;
    private OrderInformationFragment currentOrderInforamtionFr;

    public static NewConstructorFragment getInstance(FragmentManager fm){
        if(sNewConstructorFragment == null){
            sNewConstructorFragment = new NewConstructorFragment();
        }
        sNewConstructorFragment.setFm(fm);
        return sNewConstructorFragment;
    }

    public void setFm(FragmentManager fm){
        this.fm=fm;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = (View) inflater.inflate(R.layout.fragment_new_constructor,container,false);
        current = null;
        currentOrderInforamtionFr = null;
        bindView();
        setListener();
        settingDatePicker();
        init();
        onPressBackListener(view);
        return view;
    }



    private void settingDatePicker(){
        Calendar calendar = Calendar.getInstance();
        dpd = DatePickerDialog.newInstance(
                mOnDateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        dpd.setVersion(DatePickerDialog.Version.VERSION_1);
        dpd.setOnCancelListener((dialogInterface)->dateItem.setVisibility(View.VISIBLE));
    }

    private void init(){
        order = new Order(getActivity().getApplicationContext());
        setVisibleAndTouchbleItem(hotelItem,false);
    }

    private void setListener(){
        dinItem.setOnClickListener((view)->{
            current = dinItem;
            if(order.getCountDay() != null) {
                DinnerFragment dF = new DinnerFragment();
                Bundle bundle = new Bundle();
                if(order.getCountDay().equals("1")){
                    bundle.putInt("br", 0);
                    bundle.putInt("lu", 1);
                    bundle.putInt("din",0);

                }else {
                    bundle.putInt("br", order.getCountBr() == null ?
                            Integer.valueOf(order.getCountDay())-1:
                            Integer.valueOf(order.getCountBr()));
                    bundle.putInt("lu", order.getCountLu() == null ?
                            Integer.valueOf(order.getCountDay()):
                            Integer.valueOf(order.getCountLu()));
                    bundle.putInt("din", order.getCountDin() == null ? 0 :
                            Integer.valueOf(order.getCountDin()));
                    bundle.putInt("day", Integer.valueOf(order.getCountDay()));
                }
                addPosition(bundle, dinItem);
                dF.setArguments(bundle);
                dF.setOnClickListener(((countBr, countLun, countDin) -> {
                    order.setCountBr(countBr);
                    order.setCountLu(countLun);
                    order.setCountDin(countDin);
                    dinItem.setText(countBr + "/" + countLun + "/" + countDin);
                    waitAndClose(dinItem, fm);
                }));
                loadFragment(fm, dF, "din");
                dinItem.setVisibility(View.INVISIBLE);
            }else{
                Toast.makeText(getContext(),"Сначала выберите количество дней",Toast.LENGTH_LONG).show();
            }
        });
        travItem.setOnClickListener((view)->{
            current = travItem;
            TravelWayFragment tF = new TravelWayFragment();
            Bundle bundle = new Bundle();
            addPosition(bundle, travItem);
            tF.setArguments(bundle);
            tF.setOnClickListener(((choose) -> {
                order.setAddTrain(choose==0);
                travItem.setText(order.isAddTrain()?"Поезд":"Самолет");
                waitAndClose(travItem,fm);
            }));
            loadFragment(fm,tF,"trav");
            travItem.setVisibility(View.INVISIBLE);
        });
        groupItem.setOnClickListener((view)->{
            current = groupItem;
            QuantityGroupFragment gF = new QuantityGroupFragment();
            Bundle bundle = new Bundle();
            if(order.getTeachers()==null){
                order.setTeachers("2");
                order.setPupils("30");
            }
            bundle.putInt("countT",Integer.valueOf(order.getTeachers()));
            bundle.putInt("countC",Integer.valueOf(order.getPupils()));
            addPosition(bundle,groupItem);
            gF.setArguments(bundle);
            gF.setListener((countT,countC)->{
                order.setTeachers(String.valueOf(countT));
                order.setPupils(String.valueOf(countC));
                groupItem.setText(order.getTeachers()+"/"+order.getPupils());
                waitAndClose(groupItem,fm);
            });
            loadFragment(fm,gF,"group");
            groupItem.setVisibility(View.INVISIBLE);
        });
        cityToItem.setOnClickListener((view)->{
            current = cityToItem;
            CategoriesFragment cF = new CategoriesFragment();
            Bundle bundle = new Bundle();
            addPosition(bundle,cityToItem);
            bundle.putString("city",cityFromItem.getText());
            bundle.putString("curIdCity",order.getIdCity());
            bundle.putString("curCity",order.getTour());
            cF.setArguments(bundle);
            cF.setOnClickRecyclerListener((bundle1)->{
                if(!bundle1.getString("pack_exc").equals("-1")) {
                    order.setTour(bundle1.getString("pack_exc"));
                    order.setIdCity(bundle1.getString("id"));
                    cityToItem.setText(order.getTour());
                    cleanExcurItem();
                    cleanHotelItem();
                }
                waitAndClose(cityToItem,fm);
            });
            loadFragment(fm,cF,"category");
            cityToItem.setVisibility(View.INVISIBLE);
        });
        hotelItem.setOnClickListener((view)->{
            current = hotelItem;
            if(order.getIdCity() != null) {
                HotelFragment hF = new HotelFragment();
                Bundle bundle = new Bundle();
                addPosition(bundle, hotelItem);
                bundle.putString("idCity", order.getIdCity());
                bundle.putString("idHotel", order.getIdHotel());
                bundle.putString("nameHotel", order.getHotel());
                hF.setArguments(bundle);
                loadFragment(fm, hF, "hotel");
                hF.setOnClickListener((idHotel, hotel) -> {
                    if(!idHotel.equals("-1")) {
                        order.setIdHotel(idHotel);
                        order.setHotel(hotel);
                        hotelItem.setText(hotel);
                    }
                    waitAndClose(hotelItem, fm);
                });
                hotelItem.setVisibility(View.INVISIBLE);
            }else{
                Toast.makeText(getContext(),"Сначала выберите направление",Toast.LENGTH_LONG).show();
            }
        });
        dateItem.setOnClickListener((view)->{
            current = dateItem;
            if(order.getCountDay() != null) {
                dateItem.setVisibility(View.INVISIBLE);
                dpd.show(fm, "DatePicker");
            }else{
                Toast.makeText(getContext(),"Сначала выберите количество дней",Toast.LENGTH_LONG).show();
            }
        });
        dayItem.setOnClickListener((view)->{
            current = dayItem;
            DayFragment dF = new DayFragment();
            Bundle bundle = new Bundle();
            addPosition(bundle,dayItem);
            bundle.putInt("day",order.getCountDay()==null?1:Integer.valueOf(order.getCountDay()));
            dF.setArguments(bundle);
            loadFragment(fm,dF,"day");
            dF.setListener((day -> {
                order.setCountDay(String.valueOf(day));
                dayItem.setText(day==1?""+day:day+"/"+(day-1));
                setVisibleAndTouchbleItem(hotelItem,day != 1);
                if(order.getCountDay()!= null && (Integer.valueOf(order.getCountDay())!=day)){
                    cleanBusItem();
                    cleanDinItem();
                }
                waitAndClose(dayItem,fm);
            }));
            dayItem.setVisibility(View.INVISIBLE);
        });
        transItem.setOnClickListener((view)->{
            current = transItem;
            if(order.getCountDay()!=null) {
                BusFragment bF = new BusFragment();
                Bundle bundle = new Bundle();
                addPosition(bundle, transItem);
                bundle.putInt("day", Integer.valueOf(order.getCountDay()));
                bundle.putInt("addBusDays",order.getAddBusDays());
                bundle.putBoolean("addMainBus", (order.getCountBusMeet() == null)
                        || (Integer.valueOf(order.getCountBusMeet()) > 0));
                bF.setArguments(bundle);
                bF.setListener(((mainBus, addBusDays) -> {
                    transItem.setText("Выбран");
                    order.setAddBusDays(addBusDays);
                    order.setCountBusMeet(String.valueOf(mainBus?(order.getCountDay().equals("1")?1:2):0)); // Если кол-во дней 1, то встречающий автобус один
                    waitAndClose(transItem, fm);
                }));
                loadFragment(fm, bF, "bus");
                transItem.setVisibility(View.INVISIBLE);
            }else{
                Toast.makeText(getContext(),"Сначала выберите количество дней",Toast.LENGTH_LONG).show();
            }
        });
        excurItem.setOnClickListener((view)->{
            current = excurItem;
            if(order.getIdCity() != null) {
                ExcursionFragment eF = new ExcursionFragment();
                Bundle bundle = new Bundle();
                addPosition(bundle, excurItem);
                bundle.putInt("maxday", order.getCountDay() == null ? 0 : Integer.valueOf(order.getCountDay()));
                bundle.putString("idcity", order.getIdCity());
                if(order.getExcursionList() != null){
                    bundle.putInt("size", order.getExcursionList().size());
                    for(int i = 0; i < order.getExcursionList().size(); ++i){
                        bundle.putString(String.valueOf(i),order.getExcursionList().get(i).getId());
                    }
                }else{
                    bundle.putInt("size", 0 );
                }
                eF.setArguments(bundle);
                eF.setOnClickListener((List<Excursion> list) -> {
                    if(list.size() != 0) {
                        order.setExcursionList(list);
                        excurItem.setText(list.size() + "/" + (Integer.valueOf(order.getCountDay()) * 2));
                    }else{
                        cleanExcurItem();
                    }
                    waitAndClose(excurItem, fm);
                });
                loadFragment(fm, eF, "Excur");
                excurItem.setVisibility(View.INVISIBLE);
            }else{
                Toast.makeText(getContext(),"Сначала выберите направление!",Toast.LENGTH_LONG).show();
            }
        });
        cityFromItem.setOnClickListener((view)->{
            current = cityFromItem;
            ChooseCityFragment cF = new ChooseCityFragment();
            Bundle bundle = new Bundle();
            addPosition(bundle,cityFromItem);
            bundle.putString("city",cityFromItem.getText());
            cF.setArguments(bundle);
            cF.setOnClickCityListener(((change, city) -> {
                if(change){
                    cityFromItem.setText(city);
                    if(cityToItem.getText().equals(city)){
                        cleanCityToItem();
                        cleanExcurItem();
                        cleanHotelItem();
                    }
                }
                waitAndClose(cityFromItem,fm);
            }));
            loadFragment(fm,cF,"CityFrom");
        });
        btnOrder.setOnClickListener((view)->{
            OrderInformationFragment ordF = OrderInformationFragment.getInstance(order,fm);
            currentOrderInforamtionFr = ordF;
            showBookinOrder(ordF);
        });
    }

    private void bindView(){
        dinItem = (ViewItemConstructor) view.findViewById(R.id.din_item);
        travItem = (ViewItemConstructor) view.findViewById(R.id.trav_item);
        groupItem = (ViewItemConstructor) view.findViewById(R.id.group_item);
        cityToItem = (ViewItemConstructor) view.findViewById(R.id.city_to_item);
        hotelItem = (ViewItemConstructor) view.findViewById(R.id.hotel_item);
        dateItem = (ViewItemConstructor) view.findViewById(R.id.date_item);
        dayItem = (ViewItemConstructor) view.findViewById(R.id.days_item);
        transItem = (ViewItemConstructor) view.findViewById(R.id.bus_item);
        excurItem = (ViewItemConstructor) view.findViewById(R.id.exc_item);
        cityFromItem = (ViewItemConstructor) view.findViewById(R.id.from_city_item);
        btnOrder = (Button) view.findViewById(R.id.btn_order);
    }

    private void loadFragment(FragmentManager fm, Fragment fragment,String tag){
        fm.beginTransaction()
                .add(R.id.frame_main,fragment)
                .addToBackStack(tag)
                .commit();
    }

    private void addPosition(Bundle bundle, View view){
        int a[] = new int[2];
        view.getLocationInWindow(a);
        bundle.putFloat("x",a[0]);
        bundle.putFloat("y",a[1] + ScreenUtils.getStatusBarSize(getActivity()));
    }


    private void waitAndClose(View item, FragmentManager fm){
        current = null;
        new Handler().postDelayed(() -> {
            item.setVisibility(View.VISIBLE);
            fm.popBackStack();
        }, 300);
    }

    private void setVisibleAndTouchbleItem(View item, boolean visible){
        item.setClickable(visible);
        item.setAlpha(visible?1f:0.5f);
    }

    private DatePickerDialog.OnDateSetListener mOnDateSetListener = (DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) ->{
//            linCalendar.setBackgroundResource(R.color.transparent);
        dateItem.setVisibility(View.VISIBLE);
        Calendar calendar=Calendar.getInstance();
        calendar.set(Calendar.MONTH,monthOfYear);
        Calendar cl = Calendar.getInstance();
        cl.set(Calendar.YEAR,year);
        cl.set(Calendar.MONTH,monthOfYear);
        cl.set(Calendar.DAY_OF_MONTH,dayOfMonth); //Integer.valueOf(order.getCountDay())-1
        dateItem.setText(dayOfMonth+" "+calendar.getDisplayName(Calendar.MONTH,Calendar.SHORT, Locale.getDefault())+" "+year);
        order.setDate(dateItem.getText());
//            txtDateEnd.setText(cl.get(Calendar.DAY_OF_MONTH) + " "+cl.getDisplayName(Calendar.MONTH,Calendar.SHORT,Locale.getDefault())+ " "+
//                    cl.get(Calendar.YEAR));
//            txtDateEnd.setVisibility(View.VISIBLE);
        order.setDate(dateItem.getText());
    };


    private void cleanHotelItem(){
        hotelItem.cleanText();
        order.setHotel(null);
        order.setIdHotel(null);
    }


    private void cleanExcurItem(){
        excurItem.cleanText();
        order.setExcursionList(null);
    }

    private void cleanCityToItem(){
        cityToItem.cleanText();
        order.setId(null);
    }
    private void cleanDinItem(){
        dinItem.cleanText();
        order.setCountBr(null);
        order.setCountLu(null);
        order.setCountDin(null);
    }

    private void cleanBusItem(){
        transItem.cleanText();
        order.setCountBusMeet(null);
        order.setAddBusDays(0);
    }


    private void changeDate(){
        // Изменить конец даты путешествия
    }

    private void showBookinOrder(Fragment fragment){
        if(fm.findFragmentByTag("order") == null) {
            fm.beginTransaction()
                    .add(R.id.frame_main, fragment,"order")
                    .commit();
        }else{
            fm.beginTransaction()
                    .show(fragment)
                    .commit();
        }
    }

    private void onPressBackListener(View view){
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener((view1, i,keyEvent)->{
            if( i == KeyEvent.KEYCODE_BACK && keyEvent.getAction() == KeyEvent.ACTION_UP) {
                fm.beginTransaction()
                        .hide(currentOrderInforamtionFr)
                        .commit();
                currentOrderInforamtionFr = null;
                return true;
            }
            return false;
        });
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        if(!hidden){
            if(current!=null) current.setVisibility(View.VISIBLE);
            current = null;
            if(currentOrderInforamtionFr != null) {
                fm.beginTransaction()
                        .show(currentOrderInforamtionFr)
                        .commit();
            }
        }else{
            if(currentOrderInforamtionFr != null) {
                fm.beginTransaction()
                        .hide(currentOrderInforamtionFr)
                        .commit();
            }
        }
    }
}
