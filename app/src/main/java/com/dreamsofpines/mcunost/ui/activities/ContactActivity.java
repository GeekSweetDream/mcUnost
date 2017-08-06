package com.dreamsofpines.mcunost.ui.activities;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.dreamsofpines.mcunost.R;
import com.dreamsofpines.mcunost.data.storage.help.menu.LeftMenu;

/**
 * Created by ThePupsick on 07.08.17.
 */

public class ContactActivity extends AppCompatActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        LeftMenu leftMenu = new LeftMenu(this);
        leftMenu.build(this);
        TextView textView = (TextView) findViewById(R.id.moscow_contact);
        textView.setText("Москва\n" +
                "Адрес: пр-д Батайский д. 31, оф. 375 \n" +
                "Тел: 8 (999) 840-70-28 \n" +
                "E-mail: msk@mcunost.ru \n\n"+
                "Санкт-Петербург\n" +
                "Адрес: пр. Энтузиастов, д. 53/38, оф. 348 \n" +
                "Тел/факс: 8 (812) 656-86-72 \n" +
                "Тел: 8 (999) 245-81-52 \n" +
                "E-mail: info@mcunost.ru ");
    }
}
