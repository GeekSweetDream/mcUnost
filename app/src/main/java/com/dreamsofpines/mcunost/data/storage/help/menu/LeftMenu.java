package com.dreamsofpines.mcunost.data.storage.help.menu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.dreamsofpines.mcunost.R;
import com.dreamsofpines.mcunost.data.network.api.Constans;
import com.dreamsofpines.mcunost.data.storage.preference.GlobalPreferences;
import com.dreamsofpines.mcunost.ui.dialog.AuthDialogFragment;
import com.dreamsofpines.mcunost.ui.dialog.ChooseCityDialogFragment;
import com.dreamsofpines.mcunost.ui.dialog.CityDialogFragment;
import com.dreamsofpines.mcunost.ui.dialog.PhoneOrderDialog;
import com.dreamsofpines.mcunost.ui.fragments.ChatFragment;
import com.dreamsofpines.mcunost.ui.fragments.ChatsRecyclerFragment;
import com.dreamsofpines.mcunost.ui.fragments.ContactFragment;
import com.dreamsofpines.mcunost.ui.fragments.MyOrderFragment;
import com.dreamsofpines.mcunost.ui.fragments.RegLogInFragment;
import com.dreamsofpines.mcunost.ui.fragments.SettingFragment;
import com.dreamsofpines.mcunost.ui.fragments.StockFragment;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.holder.BadgeStyle;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

/**
 * Created by ThePupsick on 31.07.17.
 */

public class LeftMenu implements CityDialogFragment.OnCityChangedListener,AuthDialogFragment.OnClickedReg,
        AuthDialogFragment.OnSuccessAuth{

    private PrimaryDrawerItem tour, item1, userCity,item3,
            myorder,/* chat ,*/ setting, logInOut;
    private Drawer result;
    private Activity activity;
    private ContactFragment contact;
    private StockFragment stock;
    private MyOrderFragment order;
    private SettingFragment sett;
    private RegLogInFragment regLogIn;
    private View footer;
    private Context mContext;
    public static OnCityChanged mListener;
    public getTourPage listener;

    @Override
    public void onSuccess() {
        build(activity);
    }

    @Override
    public void onClickedReg() {}

    public interface OnCityChanged{ void onChanged(String city);}

    public void setOnCityChangedListener(OnCityChanged listener){
        mListener = listener;
    }

    public interface getTourPage{ void getPage(); }

    public void setGetPageList(getTourPage listener){ this.listener = listener;}

    public void setContext(Context context) {
        mContext = context;
    }

    @Override
    public void onChange(String city) {
        GlobalPreferences.setPrefUserCity(mContext,city);
        updateCity();
        mListener.onChanged(city);
    }


    public LeftMenu(final Activity activity, final FragmentManager fm, final Context context) {
        this.activity = activity;
        this.mContext = context;
        tour = new PrimaryDrawerItem()
                .withIdentifier(0)
                .withName("Туры")
                .withIcon(R.mipmap.ic_flight_takeoff_black_48dp)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        listener.getPage();
                        return false;
                    }
                });
        item1 = new PrimaryDrawerItem()
                .withIdentifier(1)
                .withName("Акции")
                .withIcon(R.mipmap.ic_star_rate_black_18dp)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if(null == stock){
                            stock = new StockFragment();
                        }
                        fm.beginTransaction()
                                .replace(R.id.frame_layout,stock)
                                .addToBackStack(null)
                                .commit();
                        return false;
                    }
                });
        userCity = new PrimaryDrawerItem()
                .withName("Ваш город: "+ GlobalPreferences.getPrefUserCity(mContext))
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        CityDialogFragment dF = CityDialogFragment.newInstance(LeftMenu.this,
                                GlobalPreferences.getPrefUserCity(activity));
                        dF.show(fm,"123");
                        return false;
                    }
                });

        item3 = new PrimaryDrawerItem()
                .withIdentifier(2)
                .withName("Контакты")
                .withIcon(R.mipmap.ic_book_black_48dp)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if(null == contact){
                            contact = new ContactFragment();
                        }
                        fm.beginTransaction()
                                .replace(R.id.frame_layout,contact)
                                .addToBackStack(null)
                                .commit();
                        return false;
                    }
                });
        myorder = new PrimaryDrawerItem()
                .withName("Мои заявки")
                .withIcon(R.mipmap.ic_event_black_36dp)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        order = new MyOrderFragment();
                        order.setOnClickChatListener(new MyOrderFragment.OnClickChatListener() {
                            @Override
                            public void onClick(Bundle bundle) {
                                ChatFragment chatFragment = new ChatFragment();
                                chatFragment.setArguments(bundle);
                                fm.beginTransaction()
                                        .replace(R.id.frame_layout,chatFragment)
                                        .addToBackStack(null)
                                        .commit();
                            }
                        });
                        updateDeleteOrder();
                        fm.beginTransaction()
                                .replace(R.id.frame_layout,order)
                                .addToBackStack(null)
                                .commit();
                        return false;
                    }
                });
//        chat = new PrimaryDrawerItem()
//                .withName("Сообщения")
//                .withIcon(R.mipmap.ic_email_black_36dp)
//                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
//                    @Override
//                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
////                        ChatsRecyclerFragment chatsRecyclerFragment = new ChatsRecyclerFragment();
////                        chatsRecyclerFragment.setOnClickRecyclerListener(new ChatsRecyclerFragment.OnClickRecyclerListener() {
////                            @Override
////                            public void onClicked(Bundle bundle) {
////                                ChatFragment chatFragment = new ChatFragment();
////                                chatFragment.setArguments(bundle);
////                                fm.beginTransaction()
////                                        .replace(R.id.frame_layout,chatFragment)
////                                        .addToBackStack(null)
////                                        .commit();
////                            }
////                        });
////                        fm.beginTransaction()
////                                .replace(R.id.frame_layout,chatsRecyclerFragment)
////                                .addToBackStack(null)
////                                .commit();
//                        Toast.makeText(context,"Раздел находится в разработке",Toast.LENGTH_SHORT).show();
//                        return false;
//                    }
//                });
        setting = new PrimaryDrawerItem()
                .withName("Настройки")
                .withIcon(R.mipmap.ic_settings_black_36dp)
                .withEnabled(GlobalPreferences.getPrefAddUser(mContext)==1)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if(GlobalPreferences.getPrefAddUser(mContext)==1) {
                            if (null == sett) {
                                sett = new SettingFragment();
                            }
                            sett.setChangeInfListener(new SettingFragment.changeInformationListener() {
                                @Override
                                public void onChange() {
                                    build(activity);
                                }
                            });
                            fm.beginTransaction()
                                    .replace(R.id.frame_layout, sett)
                                    .addToBackStack(null)
                                    .commit();
                        }
                        return false;
                    }
                });
        logInOut = new PrimaryDrawerItem()
                .withName(getStrLog())
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if(!logInOut.getName().toString().equalsIgnoreCase("Войти")) {
                            GlobalPreferences.setPrefAddUser(mContext, Constans.AUTH.LOG_OUT);
                            GlobalPreferences.setPrefUserName(mContext, "Неизвестно");
                            GlobalPreferences.setPrefUserEmail(mContext, "Неизвестно");
                            GlobalPreferences.setPrefUserNumber(mContext, "Не задан");
                            updateDeleteOrder();
                            listener.getPage();
                            build(activity);
                        }else{
                            AuthDialogFragment auth = AuthDialogFragment.newInstace(LeftMenu.this,LeftMenu.this);
                            auth.disabledRegistr();
                            auth.show(fm,"auth");
                            build(activity);
                        }
                        return false;
                    }
                });

        footer = View.inflate(activity,R.layout.button_skype_call,null);
        footer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PhoneOrderDialog pD = PhoneOrderDialog.newInstance();
                pD.show(fm,"PhoneDialog");
            }
        });


    }

    public void build(final Activity activity){
        this.activity = activity;
        boolean userAuthed = GlobalPreferences.getPrefAddUser(mContext)==1;
        AccountHeader accountHeader= new AccountHeaderBuilder()
                .withActivity(activity)
                .withCompactStyle(false)
                .withProfileImagesClickable(false)
                .withProfileImagesVisible(false)
                .addProfiles(new ProfileDrawerItem()
                        .withTextColorRes(R.color.md_deep_orange_400)
                        .withName(GlobalPreferences.getPrefUserName(activity))
                        .withEmail(GlobalPreferences.getPrefUserEmail(activity)))
                .withHeaderBackground(R.mipmap.baykal2)
                .withSelectionListEnabledForSingleProfile(false)
                .build();

        logInOut.withName(getStrLog());
//        chat.withEnabled(userAuthed);
        myorder.withEnabled(userAuthed);
        setting.withEnabled(userAuthed);
        if(GlobalPreferences.getPrefQuantityNew(mContext)!=0 && userAuthed) {
            myorder.withBadge(""+GlobalPreferences.getPrefQuantityNew(mContext))
                    .withBadgeStyle(new BadgeStyle()
                            .withColorRes(R.color.md_red_400)
                            .withTextColorRes(R.color.md_white_1000));
        }
        result = new DrawerBuilder()
                .withActivity(activity)
                .withAccountHeader(accountHeader)
                .withFooter(footer)
                .withFooterClickable(true)
                .addDrawerItems(tour, myorder, /*chat,*/ item1,item3,
                        new DividerDrawerItem(),
                        setting,
                        new DividerDrawerItem(),
                        userCity,
                        new DividerDrawerItem(),
                        logInOut,
                        new DividerDrawerItem(),
                        new SecondaryDrawerItem()
                                .withName("Наш телефон")
                                .withSelectable(false)
                                .withTextColor(activity.getResources().getColor(R.color.md_blue_grey_500)),
                        new PrimaryDrawerItem()
                                .withIdentifier(4)
                                .withName("8(999) 840-70-28 (Москва)")
                                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                                    @Override
                                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:89998407028"));
                                        activity.startActivity(intent);
                                        return false;
                                    }
                                }),
                        new PrimaryDrawerItem()
                                .withIdentifier(4)
                                .withName("8(999) 245-81-52 (Cанкт-Петербург)")
                                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                                    @Override
                                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:89992458152"));
                                        activity.startActivity(intent);
                                        return false;
                                    }
                                })

                                )
                .build();
    }

    public void updateNewOrder(){
        if(result!=null) {
            GlobalPreferences.setPrefQuantityNew(mContext);
            myorder.withBadge(""+GlobalPreferences.getPrefQuantityNew(mContext))
                    .withBadgeStyle(new BadgeStyle()
                            .withColorRes(R.color.md_red_400)
                            .withTextColorRes(R.color.md_white_1000));
            result.updateItem(myorder);
        }
    }

    public void updateMess(){
        if(result!=null) {
            int count = GlobalPreferences.getPrefQuantityNewMess(mContext);
            if(count>0) {
//                chat.withBadge("" + count)
//                        .withBadgeStyle(new BadgeStyle()
//                                .withColorRes(R.color.md_red_400)
//                                .withTextColorRes(R.color.md_white_1000));
            }else{
//                chat.withBadge("").withBadgeStyle(new BadgeStyle().withColorRes(R.color.transparent));
            }
//            result.updateItem(chat);
        }

    }


    private void changeFragment(Fragment fragment){

    }

    public void updateDeleteOrder(){
        if(result!=null){
            myorder.withBadge("").withBadgeStyle(new BadgeStyle().withColorRes(R.color.transparent));
            result.updateItem(myorder);
            GlobalPreferences.setZeroPrefQuantityNew(mContext);
        }
    }

    public void updateCity(){
        if (result!=null){
            userCity.withName("Ваш город: "+ GlobalPreferences.getPrefUserCity(mContext));
            result.updateItem(userCity);
        }

    }

    private String getStrLog(){
        if(GlobalPreferences.getPrefAddUser(mContext)==1){
            return "Выход из аккаунта";
        }
        return "Войти";
    }

    public void openMenu(){
        if (result!=null) {
            result.openDrawer();
        }
    }

}
