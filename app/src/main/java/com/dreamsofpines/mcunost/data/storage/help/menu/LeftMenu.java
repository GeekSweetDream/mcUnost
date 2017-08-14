package com.dreamsofpines.mcunost.data.storage.help.menu;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.View;

import com.dreamsofpines.mcunost.R;
import com.dreamsofpines.mcunost.data.storage.preference.GlobalPreferences;
import com.dreamsofpines.mcunost.ui.activities.CategoriesActivity;
import com.dreamsofpines.mcunost.ui.activities.ContactActivity;
import com.dreamsofpines.mcunost.ui.activities.StockActivity;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

/**
 * Created by ThePupsick on 31.07.17.
 */

public class LeftMenu {

    private Person mPerson;
    private PrimaryDrawerItem item0, item1,item2,item3,item4;
    private Drawer result;
    private View stickyDraw;
    private Activity activity;

    public LeftMenu(final Activity activity) {
        this.activity = activity;
        mPerson = new Person(GlobalPreferences.getPrefUserName(activity), GlobalPreferences.getPrefUserNumber(activity));
        item0 = new PrimaryDrawerItem()
                .withIdentifier(0)
                .withName("Туры")
                .withIcon(R.mipmap.ic_flight_takeoff_black_48dp)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        Intent intent = new Intent(activity, CategoriesActivity.class);
                        activity.startActivity(intent);
                        activity.finish();
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
                        Intent intent = new Intent(activity, StockActivity.class);
                        activity.startActivity(intent);
                        activity.finish();
                        return false;
                    }
                });
        item2 = new PrimaryDrawerItem()
                .withName("Ваш город: "+ GlobalPreferences.getPrefUserCity(activity));

        item3 = new PrimaryDrawerItem()
                .withIdentifier(2)
                .withName("Контакты")
                .withIcon(R.mipmap.ic_book_black_48dp)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        Intent intent = new Intent(activity, ContactActivity.class);
                        activity.startActivity(intent);
                        activity.finish();
                        return false;
                    }
                });


    }

    public void build(final Activity activity,int select){
        this.activity = activity;
        AccountHeader accountHeader = new AccountHeaderBuilder()
                .withActivity(activity)
                .withCompactStyle(false)
                .withProfileImagesClickable(false)
                .withProfileImagesVisible(false)
                .withHeaderBackground(R.mipmap.baykal)
                .withSelectionListEnabledForSingleProfile(false)
                .build();

        result = new DrawerBuilder()
                .withActivity(activity)
                .withAccountHeader(accountHeader)
                .withStickyFooter(R.layout.button_skype_call)
                .addDrawerItems(item0, item1,item3,
                        new DividerDrawerItem(),
                        item2,
                        new DividerDrawerItem(),
                        new SecondaryDrawerItem()
                            .withName("Наши телефоны").withSelectable(false).withTextColor(activity.getResources().getColor(R.color.md_blue_grey_500)),
                        new PrimaryDrawerItem()
                                .withIdentifier(4)
                                .withName("8(495)349-56-91 (Москва)")
                                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                                    @Override
                                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:84953495691"));
                                        activity.startActivity(intent);
                                        return false;
                                    }
                                }),
                        new PrimaryDrawerItem()
                                .withIdentifier(5)
                                .withName("8(812)656-86-72 (Санкт-Петербург)")
                                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                                    @Override
                                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:88126568672"));
                                        activity.startActivity(intent);
                                        return false;
                                    }
                                })
                        )
                .build();
    }

    public void updateCity(){
        if (result!=null){
            item2.withName("Ваш город: "+ GlobalPreferences.getPrefUserCity(activity));
            result.updateItem(item2);
        }

    }

    public void openMenu(){
        if (result!=null) {
            result.openDrawer();
        }
    }
}
