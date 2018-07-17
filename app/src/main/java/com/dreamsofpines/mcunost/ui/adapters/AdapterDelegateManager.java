package com.dreamsofpines.mcunost.ui.adapters;

import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

public class AdapterDelegateManager<T> {

    private SparseArrayCompat<AdapterDelegate<T>> delegates = new SparseArrayCompat();

    public AdapterDelegateManager<T> addDelegate(int viewType, AdapterDelegate<T> delegate){
        delegates.put(viewType,delegate);
        return this;
    }

    public int getItemViewType(T items, int position){
        int size = delegates.size();
        for(int i = 0; i < size; ++i){
            AdapterDelegate<T> delegate = delegates.valueAt(i);
            if(delegate.isForViewType(items,position)){
                return delegates.keyAt(i);
            }
        }
        throw new NullPointerException("No adapter");
    }

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return getDelegateForViewType(viewType).OnCreateViewHolder(parent);
    }


    public void onBindViewHolder(T items, int position,  RecyclerView.ViewHolder viewHolder) {
        AdapterDelegate<T> delegate = getDelegateForViewType(viewHolder.getItemViewType());
        delegate.onBindViewHolder(items,position,viewHolder);
    }


    public AdapterDelegate<T> getDelegateForViewType(int viewType){
        return delegates.get(viewType);
    }
}
