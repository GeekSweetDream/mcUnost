package com.dreamsofpines.mcunost.ui.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dreamsofpines.mcunost.R;
import com.dreamsofpines.mcunost.data.storage.mItemRecyclerView;
import com.dreamsofpines.mcunost.data.storage.models.SaleItem;

import java.util.List;

public class SaleItemDelegate extends AdapterDelegate<List<mItemRecyclerView>> {


    private LayoutInflater inflater;
    private AdapterOrders.OnClickListener listener;
    private final int MAX_SIZE_STRING = 80;

    public AdapterOrders.OnClickListener getListener() {
        return listener;
    }

    public SaleItemDelegate setListener(AdapterOrders.OnClickListener listener) {
        this.listener = listener;
        return this;
    }

    public SaleItemDelegate(Activity activity){
        this.inflater = activity.getLayoutInflater();
    }


    @Override
    protected boolean isForViewType(List<mItemRecyclerView> items, int position) {
        return items.get(position) instanceof SaleItem;
    }

    @Override
    protected RecyclerView.ViewHolder OnCreateViewHolder(ViewGroup parent) {
        return new SaleItemHolder(inflater.inflate(R.layout.item_card_sale,parent,false));
    }

    @Override
    protected void onBindViewHolder(List<mItemRecyclerView> items, int position, RecyclerView.ViewHolder holder) {
        SaleItemHolder saleItemHolder = (SaleItemHolder) holder;
        SaleItem saleItem = (SaleItem) items.get(position);
        saleItemHolder.text.setText(getCutText(saleItem.getText()));
        saleItemHolder.title.setText(saleItem.getTitle());
        saleItemHolder.moreBtn.setOnClickListener(view -> listener.onClick(saleItem.getIdSale()));
    }

    private String getCutText(String str){
        String[] arr = str.split("\n");
        String answer = arr[0];
        for(int i = 1; (i < arr.length) && (answer.length() < MAX_SIZE_STRING); ++ i) {
            answer = answer.concat(" "+arr[i]);
        }
        return answer.substring(0, MAX_SIZE_STRING - 3) + "...";
    }

    public  class SaleItemHolder extends  RecyclerView.ViewHolder{
        public TextView title;
        public TextView text;
        public TextView moreBtn;


        public SaleItemHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title_sale_txt);
            text = (TextView) itemView.findViewById(R.id.text_sale_txt);
            moreBtn = (TextView) itemView.findViewById(R.id.more_sale_btn);
        }
    }
}
