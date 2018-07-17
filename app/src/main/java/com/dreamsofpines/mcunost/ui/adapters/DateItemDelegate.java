package com.dreamsofpines.mcunost.ui.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dreamsofpines.mcunost.R;
import com.dreamsofpines.mcunost.data.storage.mItemRecyclerView;
import com.dreamsofpines.mcunost.data.storage.models.DateItem;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class DateItemDelegate extends AdapterDelegate<List<mItemRecyclerView>>  {

    private LayoutInflater inflater;

    public DateItemDelegate(Activity activity){
        inflater = activity.getLayoutInflater();
    }

    @Override
    protected boolean isForViewType(List<mItemRecyclerView> items, int position) {
        return items.get(position) instanceof DateItem;
    }

    @Override
    protected RecyclerView.ViewHolder OnCreateViewHolder(ViewGroup parent) {
        return new DateItemHolder(inflater.inflate(R.layout.item_date_txt,parent,false));
    }

    @Override
    protected void onBindViewHolder(List<mItemRecyclerView> items, int position, RecyclerView.ViewHolder holder) {
        DateItemHolder dateItemHolder = (DateItemHolder) holder;
        DateItem dateItem = (DateItem) items.get(position);
        dateItemHolder.text.setText(dateItem.getStringDate());
    }


    public class DateItemHolder extends RecyclerView.ViewHolder{
        public TextView text;


        public DateItemHolder(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.date_txt);
        }
    }
}
