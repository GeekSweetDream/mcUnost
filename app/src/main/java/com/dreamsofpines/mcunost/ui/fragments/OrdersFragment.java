package com.dreamsofpines.mcunost.ui.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dreamsofpines.mcunost.R;
import com.dreamsofpines.mcunost.data.network.api.RequestSender;
import com.dreamsofpines.mcunost.data.storage.mBarItem;
import com.dreamsofpines.mcunost.data.storage.models.EmptyItem;
import com.dreamsofpines.mcunost.data.storage.models.Excursion;
import com.dreamsofpines.mcunost.data.storage.models.Order;
import com.dreamsofpines.mcunost.data.utils.ImageUtils;
import com.dreamsofpines.mcunost.data.utils.FragmentQueueUtils;
import com.dreamsofpines.mcunost.data.storage.mItemRecyclerView;
import com.dreamsofpines.mcunost.data.storage.models.OrderItem;
import com.dreamsofpines.mcunost.data.storage.models.TextItem;
import com.dreamsofpines.mcunost.data.storage.preference.GlobalPreferences;
import com.dreamsofpines.mcunost.ui.adapters.AdapterOrders;
import com.dreamsofpines.mcunost.ui.customView.ToolbarCalendar;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;

public class OrdersFragment extends Fragment implements AppBarLayout.OnOffsetChangedListener{


    private static OrdersFragment sOrdersFragment;

    private View view;
    private ToolbarCalendar mToolbarCalendar;
    private ImageView mProfileImage;
    private TextView name;
    private RecyclerView mRecyclerView;
    private int mMaxScrollSize;
    private static final int PERCENTAGE_TO_ANIMATE_AVATAR = 20;
    private boolean mIsAvatarShown = true;
    private OnClickListener listener;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private Order openedOrder;
    private OrderInformationFragment currentOrderInforamtionFr;
    private List<mItemRecyclerView> sortedList;
    private FragmentManager fm;
    private List<Order> ord;
    private AppBarLayout appbarLayout;


    public interface OnClickListener{
        void onClick();
    }

    public void setOnClickListener(OnClickListener listener){
        this.listener = listener;
    }

    public static OrdersFragment getInstance(FragmentManager fm){
        if(sOrdersFragment == null){
            sOrdersFragment = new OrdersFragment();
        }
        sOrdersFragment.setFm(fm);
        return sOrdersFragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = (View) inflater.inflate(R.layout.fragment_orders,container,false);
        bindView(view);
        init();
        GetOrderTask getOrderTask = new GetOrderTask();
        getOrderTask.execute();
        onPressBackListener(view);
        return view;
    }

    private void init(){
        Picasso.with(getContext()).load("file:///android_asset/"+
                ImageUtils.getNameAvatars(GlobalPreferences.getPrefStateAvatar(getContext())))
                .into(mProfileImage);
        openedOrder = null;
        currentOrderInforamtionFr = null;
        mSwipeRefreshLayout.setEnabled(false);
        mSwipeRefreshLayout.setRefreshing(true);
        mSwipeRefreshLayout.setOnRefreshListener(()->{
            GetOrderTask getOrderTask = new GetOrderTask();
            getOrderTask.execute();
        });
        appbarLayout.addOnOffsetChangedListener(this);

    }

    private void bindView(View view){
        mToolbarCalendar = (ToolbarCalendar) view.findViewById(R.id.toolbar_calendar);
        mProfileImage = (ImageView) view.findViewById(R.id.avatar);
        appbarLayout = (AppBarLayout) view.findViewById(R.id.appbar);
        name = (TextView) view.findViewById(R.id.name);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_order);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_layout);
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
                openedOrder = null;
                return true;
            }
            return false;
        });
    }

    private void updateUI(boolean empty){
        AdapterOrders adapterOrders = new AdapterOrders(getActivity());
        if(!empty){
            sortedList = getSortedListWithTitle();
            adapterOrders.setListener((position)->{
                openedOrder = ((OrderItem)sortedList.get(position)).getOrder();
                currentOrderInforamtionFr = OrderInformationFragment.getInstance(
                        openedOrder, fm);
                showSecondFragment(currentOrderInforamtionFr);
            });
        }else{
            sortedList = new ArrayList<>();
            EmptyItem emptyItem = new EmptyItem();
            emptyItem.setText("Нет заказов");
            sortedList.add(emptyItem);
        }
        adapterOrders.setItems(sortedList);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(adapterOrders);
    }

    private void showSecondFragment(Fragment fragment){
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

    public OrderInformationFragment getCurrentOrderInforamtionFr() {
        return currentOrderInforamtionFr;
    }

    private List<mItemRecyclerView> getSortedListWithTitle(){
        List<Order> activeOrdList = findActiveOrder();
        List<Order> passiveOrdList = findPassiveOrder();
        List<mItemRecyclerView> list = new ArrayList<>();

        if (activeOrdList.size()!=0){
            sortList(activeOrdList);
            addOrderInList(activeOrdList,list,"Активные");
        }
        if (passiveOrdList.size()!=0) {
            sortList(activeOrdList);
            addOrderInList(passiveOrdList, list, "Завершенные");
        }
        return list;
    }

    private void sortList(List<Order> ord){
        Collections.sort(ord,(order, t1) ->
                order.getDateCreate().compareTo(t1.getDateCreate()));
    }

    private void addOrderInList(List<Order> orders, List<mItemRecyclerView> orderItems,String status){
        orderItems.add(new TextItem().setText(status));
        for (int i = 0; i < orders.size(); ++i) {
            Order ord = orders.get(i);
            OrderItem orderItem = new OrderItem();
            orderItem.setCity(ord.getTour());
            orderItem.setCost(Integer.valueOf(ord.getCost()));
            orderItem.setDate(ord.getStringDateBeginTour());
            orderItem.setStatus(ord.getStatus());
            orderItem.setOrder(ord);
            orderItem.setPathImage(ImageUtils.getNameImageCity(ord.getTour()));
            orderItems.add(orderItem);
        }
    }

    private List<Order> findActiveOrder(){
        List<Order> list = new ArrayList<>();
        for(Order order: ord ){
            if(order.getStatus().equals("Заказан") || order.getStatus().equals("В обработке")){
                list.add(order);
            }
        }
        return list;
    }
    private List<Order> findPassiveOrder(){
        List<Order> list = new ArrayList<>();
        for(Order order: ord ){
            if(!order.getStatus().equals("Заказан") && !order.getStatus().equals("В обработке")){
                list.add(order);
            }
        }
        return list;
    }


    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        if (mMaxScrollSize == 0)
            mMaxScrollSize = appBarLayout.getTotalScrollRange();

        int percentage = (Math.abs(verticalOffset)) * 100 / mMaxScrollSize;

        if (percentage >= PERCENTAGE_TO_ANIMATE_AVATAR && mIsAvatarShown) {
            mIsAvatarShown = false;

            mProfileImage.animate()
                    .alpha(0)
                    .setDuration(300)
                    .start();
            name.animate()
                    .alpha(0)
                    .setDuration(300)
                    .start();
        }

        if (percentage <= PERCENTAGE_TO_ANIMATE_AVATAR && !mIsAvatarShown) {
            mIsAvatarShown = true;

            mProfileImage.animate()
                    .alpha(1)
                    .setDuration(200)
                    .start();
            name.animate()
                    .alpha(1)
                    .setDuration(200)
                    .start();
        }
    }

    public FragmentManager getFm() {
        return fm;
    }

    public void setFm(FragmentManager fm) {
        this.fm = fm;
    }

    private class GetOrderTask extends AsyncTask<Void,Void,Boolean> {

        @Override
        protected Boolean doInBackground(Void... voids) {
            Boolean success = true;
            ord = new ArrayList<>();
            String response = RequestSender.GetAllOrdersById(getContext());
            try {
                JSONObject js = new JSONObject(response);
                String resultResponce = js.getString("result");
                if (resultResponce.equalsIgnoreCase("success")) {
                    JSONArray ordersJsonArray = js.getJSONArray("data");
                    int length = ordersJsonArray.length();
                    for(int i = 0; i < length; ++i) {
                        ord.add(createOrderFromJson(ordersJsonArray.getJSONObject(i)));
                    }
                }else{
                    success = false;
                    Log.i("PackExcur:","Bad answer! Error message:"+js.getString("mess"));
                    /* error answer (add log.i())*/
                }
            } catch (JSONException e) {
                success = false;
                Log.i("PackExcur:","JsonExeption from parsing json pack_excur! Error message:"+e.getMessage());
            } catch (Exception e){
                success = false;
                Log.i("PackExcur:"," Error! Error message:"+e.getMessage());
            }
            return success;
        }


        @Override
        protected void onPostExecute(Boolean success) {
            if(success) {
                mSwipeRefreshLayout.setEnabled(true);
                mSwipeRefreshLayout.setRefreshing(false);
                updateUI(ord.size() == 0);
                setDateOnCalendarToolbar(ord);
            }else{
                mSwipeRefreshLayout.setEnabled(true);
                mSwipeRefreshLayout.setRefreshing(false);
                Toast.makeText(getContext(),"Oops! Проблемы с соединением, попробуйте позже!",Toast.LENGTH_LONG)
                        .show();
            }
        }
    }

    private void setDateOnCalendarToolbar(List<Order> ord){
        new Handler().postDelayed(()->{
            HashSet<Calendar> hashSet = new HashSet<>();
            for(Order order : ord) {
                Date date = order.getDateTravelTourBegin();
                Calendar calendar = new GregorianCalendar(getYear(date),getMonth(date),getDay(date));
                hashSet.add(calendar);
            }
            mToolbarCalendar.setDateOrders(hashSet);
        },1000);
    }

    private int getDay(Date date){
        return Integer.valueOf(new SimpleDateFormat("dd").format(date));
    }
    private int getMonth(Date date){
        return Integer.valueOf(new SimpleDateFormat("MM").format(date));
    }
    private int getYear(Date date){
        return Integer.valueOf(new SimpleDateFormat("yyyy").format(date));
    }

    private Order createOrderFromJson(JSONObject ordJs){
        Order order = new Order();
        try {
            String managerName = "Неизвестно";
            String managerPhone = "Неизвестно";
            if(!ordJs.getString("manager").equalsIgnoreCase("null")) {
                JSONObject manager = ordJs.getJSONObject("manager");
                managerName = manager.getString("name");
                managerPhone = manager.getString("phone");
            }
            order.setTour(ordJs.getString("nameTour"));
            order.setDateTravelTourBegin(new Date(ordJs.getLong("dateTravelTourBegin")));
            order.setDateTravelTourEnd(new Date(ordJs.getLong("dateTravelTourEnd")));
            order.setCost(ordJs.getString("cost"));
            order.setPupils(ordJs.getString("quantityChildren"));
            order.setTeachers(ordJs.getString("quantityTeacher"));
            order.setStatus(ordJs.getJSONObject("status").getString("status"));
            order.setManager(managerName);
            order.setPhone(managerPhone);
            order.setId(ordJs.getString("id"));
            order.setDateCreate(new Date(ordJs.getLong("dateCreateOrder")));
            order.setIdHotel(ordJs.getString("idHotel"));
            order.setCountBr(ordJs.getInt("countBr"));
            order.setCountLu(ordJs.getInt("countLu"));
            order.setCountDin(ordJs.getInt("countDin"));
            order.setAddTrain(ordJs.getInt("addTrain")==1);
            order.setCount4Bus(ordJs.getString("count4Bus"));
            order.setCountBusMeet(ordJs.getString("countMeetBus"));
            order.setCountAllDayBus(ordJs.getString("countAllDayBus"));
            order.setHotel(ordJs.getString("nameHotel"));
            order.setExcursionList(getListExcursionFromJson(ordJs));
        }catch (Exception e){
            Toast.makeText(getContext(),"что-то сломалось",Toast.LENGTH_SHORT).show();
        }
        return order;
    }

    private List<Excursion> getListExcursionFromJson(JSONObject ordJs) throws JSONException{
        JSONArray jsArray = ordJs.getJSONArray("list");
        List<Excursion> list = new ArrayList<>();
        for(int j = 0; j < jsArray.length();++j){
            list.add(new Excursion(jsArray.getJSONObject(j).getString("id"),
                    jsArray.getJSONObject(j).getString("name"),true,1));
        }
        return list;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if(!hidden) {
            name.setText(GlobalPreferences.getPrefAddUser(getContext()) == 1?
                    GlobalPreferences.getPrefUserName(getContext())
                    :"Имя");

            Picasso.with(getContext()).load("file:///android_asset/"+
                    ImageUtils.getNameAvatars(GlobalPreferences.getPrefStateAvatar(getContext())))
                    .into(mProfileImage);

            if(currentOrderInforamtionFr != null) {
                currentOrderInforamtionFr = OrderInformationFragment.getInstance(openedOrder,fm);
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
