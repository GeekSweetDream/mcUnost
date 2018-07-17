package com.dreamsofpines.mcunost.ui.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
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
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class OrdersFragment extends Fragment implements AppBarLayout.OnOffsetChangedListener{

    private static OrdersFragment sOrdersFragment;
    private ToolbarCalendar mToolbarCalendar;
    private ImageView mProfileImage;
    private TextView name;
    private RecyclerView mRecyclerView;
    private int mMaxScrollSize;
    private static final int PERCENTAGE_TO_ANIMATE_AVATAR = 20;
    private boolean mIsAvatarShown = true;
    private OnClickListener listener;
    private SwipeRefreshLayout mSwipeRefreshLayout;
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
        View view = (View) inflater.inflate(R.layout.fragment_orders,container,false);
        bindView(view);
        init();
        onPressBackListener(view);

//        new Handler().postDelayed(()->{
//            Toast.makeText(getContext(),"Успешно",Toast.LENGTH_LONG).show();
//            Calendar calendar = new GregorianCalendar(2018,4,3);
//            HashSet<Calendar> hashSet = new HashSet<>();
//            hashSet.add(calendar);
//            mToolbarCalendar.setDateOrders(hashSet);
//        },5000);
        GetOrderTask getOrderTask = new GetOrderTask();
        getOrderTask.execute();
        return view;
    }

    private void init(){
        Picasso.with(getContext()).load("file:///android_asset/"+
                ImageUtils.getNameAvatars(GlobalPreferences.getPrefStateAvatar(getContext())))
                .into(mProfileImage);
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
                return true;
            }
            return false;
        });
    }

    private void updateUI(){
        sortedList = getSortedListWithTitle();
        AdapterOrders adapterOrders = new AdapterOrders(getActivity());
        adapterOrders.setItems(sortedList);
        adapterOrders.setListener((position)->{
            OrderInformationFragment oF = OrderInformationFragment.getInstance(
                    ((OrderItem)sortedList.get(position)).getOrder(), fm
            );
            FragmentQueueUtils.addValueOrders(oF);
            currentOrderInforamtionFr = oF;
            showSecondFragment(oF);
        });
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
            // сортировка
            addOrderInList(activeOrdList,list,"Активные");
        }

        if (passiveOrdList.size()!=0) {
            // сортировка
            addOrderInList(passiveOrdList, list, "Завершенные");
        }
        return list;
    }

    private void addOrderInList(List<Order> orders, List<mItemRecyclerView> orderItems,String status){
        orderItems.add(new TextItem().setText(status));
        for (int i = 0; i < orders.size(); ++i) {
            Order ord = orders.get(i);
            OrderItem orderItem = new OrderItem();
            orderItem.setCity(ord.getTour());
            orderItem.setCost(Integer.valueOf(ord.getCost()));
            orderItem.setDate(ord.getDate());
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
                        JSONObject ordJs = ordersJsonArray.getJSONObject(i);
                        String managerName = "Неизвестно";
                        String managerPhone = "Неизвестно";
                        if(!ordJs.getString("manager").equalsIgnoreCase("null")) {
                            JSONObject manager = ordJs.getJSONObject("manager");
                            managerName = manager.getString("name");
                            managerPhone = manager.getString("phone");
                        }
                        Order order = new Order(
                                ordJs.getString("nameTour"),
                                removeEndTravelDate(ordJs.getString("dateTravelTour")),
                                ordJs.getString("cost"),
                                ordJs.getString("quantityChildren"),
                                ordJs.getString("quantityTeacher"),
                                ordJs.getJSONObject("status").getString("status"),
                                managerName,
                                managerPhone);
                        order.setId(ordJs.getString("id"));
                        order.setDateCreate(translateDateInToString(ordJs.getLong("dateCreateOrder")));
                        order.setIdHotel(ordJs.getString("idHotel"));
                        order.setCountBr(ordJs.getInt("countBr"));
                        order.setCountLu(ordJs.getInt("countLu"));
                        order.setCountDin(ordJs.getInt("countDin"));
                        order.setAddTrain(ordJs.getInt("addTrain")==1);
                        order.setCount4Bus(ordJs.getString("count4Bus"));
                        order.setCountBusMeet(ordJs.getString("countMeetBus"));
                        order.setCountAllDayBus(ordJs.getString("countAllDayBus"));
                        order.setHotel(ordJs.getString("nameHotel"));
                        JSONArray jsArray = ordJs.getJSONArray("list");
                        List<Excursion> list = new ArrayList<>();
                        for(int j = 0; j < jsArray.length();++j){
                            list.add(new Excursion(jsArray.getJSONObject(j).getString("id"),
                                    jsArray.getJSONObject(j).getString("name"),true,1));
                        }
                        order.setExcursionList(list);
                        ord.add(order);
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

        private String translateDateInToString(long date){
            return new SimpleDateFormat("dd.MM.yyyy").format(new Date(date));
        }

        private String removeEndTravelDate(String str){
            return str.split("-")[0];
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if(success) {
                mSwipeRefreshLayout.setEnabled(true);
                mSwipeRefreshLayout.setRefreshing(false);
                if(ord.size()==0) {
                }else{
                    updateUI();
//                    Collections.sort(ord, new Comparator<Order>() {
//                        @Override
//                        public int compare(Order order, Order t1) {
//                            return Integer.valueOf(order.getId()).compareTo(Integer.valueOf(t1.getId()));
//                        }
//                    });
                }
//                avl.hide();
//                if(errorView.getVisibility() != View.GONE) {
//                    errorView.setVisibility(View.GONE);
//                }

            }else{
//                avl.show();
//                errorView.setAnimation(anim);
//                errorView.setVisibility(View.VISIBLE);
                Toast.makeText(getContext(),"Oops! Проблемы с соединением, попробуйте позже!",Toast.LENGTH_LONG)
                        .show();
            }
        }
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
