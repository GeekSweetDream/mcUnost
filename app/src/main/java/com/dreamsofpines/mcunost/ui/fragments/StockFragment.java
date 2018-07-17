package com.dreamsofpines.mcunost.ui.fragments;

import android.os.Bundle;
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
import com.dreamsofpines.mcunost.data.storage.models.SaleItem;

import org.w3c.dom.Text;

/**
 * Created by ThePupsick on 07.08.17.
 */

public class StockFragment extends Fragment {


    private View view;
    private TextView title;
    private TextView text;

    private SaleItem saleItem;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_stock,container,false);
        bindView();
        init();
        return view;
    }

    private void bindView(){
        title = (TextView) view.findViewById(R.id.title_sale_txt);
        text = (TextView) view.findViewById(R.id.text_sale_txt);
    }

    private void init(){
        title.setText(saleItem.getTitle());
        text.setText(saleItem.getText());
    }

    public SaleItem getSaleItem() {
        return saleItem;
    }

    public void setSaleItem(SaleItem saleItem) {
        this.saleItem = saleItem;
    }
}
