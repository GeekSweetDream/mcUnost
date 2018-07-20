package com.dreamsofpines.mcunost.ui.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dreamsofpines.mcunost.R;
import com.dreamsofpines.mcunost.data.storage.mItemRecyclerView;
import com.dreamsofpines.mcunost.data.storage.models.EmptyItem;

import java.util.List;

public class EmptyDelegate extends  AdapterDelegate<List<mItemRecyclerView>> {

    private LayoutInflater inflater;

    public EmptyDelegate(Activity activity) {
        this.inflater = activity.getLayoutInflater();
    }

    @Override
    protected boolean isForViewType(List<mItemRecyclerView> items, int position) {
        return items.get(position) instanceof EmptyItem;
    }

    @Override
    protected RecyclerView.ViewHolder OnCreateViewHolder(ViewGroup parent) {
        return new EmptyItemDelegateHolder(inflater.inflate(R.layout.empty_item,parent,false));
    }

    @Override
    protected void onBindViewHolder(List<mItemRecyclerView> items, int position, RecyclerView.ViewHolder holder) {
        EmptyItemDelegateHolder emptyItemDelegateHolder = (EmptyItemDelegateHolder) holder;
        EmptyItem emptyItem = (EmptyItem) items.get(position);
        emptyItemDelegateHolder.title.setText(emptyItem.getText());
    }

    public class EmptyItemDelegateHolder extends RecyclerView.ViewHolder{
        public TextView title;

        public EmptyItemDelegateHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title_txt);
        }
    }

}
