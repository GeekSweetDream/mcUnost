package com.dreamsofpines.mcunost.data.storage.help.menu;

import android.app.Activity;

import com.dreamsofpines.mcunost.R;
import com.dreamsofpines.mcunost.data.storage.preference.GlobalPreferences;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;

/**
 * Created by ThePupsick on 31.07.17.
 */

public class LeftMenu {

    private Person mPerson;
    private PrimaryDrawerItem item0, item1,item2,item3,item4;

    public LeftMenu(Activity activity) {
        mPerson = new Person(GlobalPreferences.getPrefUserName(activity), GlobalPreferences.getPrefUserNumber(activity));
        item0 = new PrimaryDrawerItem()
                .withIdentifier(0)
                .withName("Туры")
                .withIcon(R.mipmap.ic_flight_takeoff_black_48dp);
        item1 = new PrimaryDrawerItem()
                .withIdentifier(1)
                .withName("Акции")
                .withIcon(R.mipmap.ic_star_rate_black_18dp);
        item2 = new PrimaryDrawerItem()
                .withIdentifier(2)
                .withName("О нас")
                .withIcon(R.mipmap.ic_info_outline_black_48dp);
        item3 = new PrimaryDrawerItem()
                .withIdentifier(3)
                .withName("Контакты")
                .withIcon(R.mipmap.ic_book_black_48dp);
        item4 = new PrimaryDrawerItem()
                .withIdentifier(4)
                .withName("Настройки")
                .withIcon(R.mipmap.ic_book_black_48dp);

    }

    public void build(Activity activity){
        AccountHeader accountHeader = new AccountHeaderBuilder()
                .withActivity(activity)
                .withCompactStyle(true)
                .withProfileImagesClickable(false)
                .withHeaderBackground(R.color.material_drawer_dark_background)
                .withSelectionListEnabledForSingleProfile(false)
                .addProfiles( new ProfileDrawerItem()
                        .withName(mPerson.getName())
                        .withEmail(mPerson.getNumb())
                        .withIcon(R.mipmap.ic_account_circle_white_48dp))
                .build();

        Drawer result = new DrawerBuilder()
                .withActivity(activity)
                .withAccountHeader(accountHeader)
                .addDrawerItems(item0, item1,item2,item3, new DividerDrawerItem(),item4)
                .build();
    }
}
