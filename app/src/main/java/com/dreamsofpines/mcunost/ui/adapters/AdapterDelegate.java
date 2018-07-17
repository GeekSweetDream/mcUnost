package com.dreamsofpines.mcunost.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

public abstract class AdapterDelegate<T> {

        protected abstract boolean isForViewType(T items, int position);
        protected abstract RecyclerView.ViewHolder OnCreateViewHolder(ViewGroup parent);
        protected abstract void onBindViewHolder(T items, int position,RecyclerView.ViewHolder holder);

}
