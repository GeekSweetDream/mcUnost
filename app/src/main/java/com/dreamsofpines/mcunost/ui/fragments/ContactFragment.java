package com.dreamsofpines.mcunost.ui.fragments;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.dreamsofpines.mcunost.R;
import com.dreamsofpines.mcunost.data.storage.help.menu.LeftMenu;

/**
 * Created by ThePupsick on 07.08.17.
 */

public class ContactFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View  view = inflater.inflate(R.layout.activity_contact,container,false);
        TextView textView = (TextView) view.findViewById(R.id.moscow_contact);
        textView.setText("Москва\n" +
                "Адрес: пр-д Батайский д. 31, оф. 375 \n" +
                "Тел: 8 (999) 840-70-28 \n" +
                "E-mail: msk@mcunost.ru \n\n"+
                "Санкт-Петербург\n" +
                "Адрес: пр. Энтузиастов, д. 53/38, оф. 348 \n" +
                "Тел/факс: 8 (812) 656-86-72 \n" +
                "Тел: 8 (999) 245-81-52 \n" +
                "E-mail: info@mcunost.ru ");
        textView = (TextView) getActivity().findViewById(R.id.title_tour);
        textView.setText("Контакты");
        return view;
    }


}

