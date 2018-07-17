package com.dreamsofpines.mcunost.ui.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.dreamsofpines.mcunost.data.storage.mItemRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AdapterMessages extends RecyclerView.Adapter {

    public final int DATE_TEXT = 0;
    public final int MESS_TEXT = 1;
    public final int MESS_FILE_TEXT = 2;

    private AdapterDelegateManager<List<mItemRecyclerView>> mListAdapterDelegateManager;
    private List<mItemRecyclerView> items;

    public AdapterMessages(Activity activity){
        items = new ArrayList<>();
        addDelegates(activity);
    }

    private void addDelegates(Activity activity){
        mListAdapterDelegateManager = new AdapterDelegateManager<>();
        mListAdapterDelegateManager.addDelegate(DATE_TEXT,new DateItemDelegate(activity));
        mListAdapterDelegateManager.addDelegate(MESS_TEXT,new MessageTextItemDelegate(activity));
        mListAdapterDelegateManager.addDelegate(MESS_FILE_TEXT,new MessageFileItemDelegate(activity));
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return mListAdapterDelegateManager.onCreateViewHolder(parent,viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        mListAdapterDelegateManager.onBindViewHolder(items,position,holder);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mListAdapterDelegateManager.getItemViewType(items,position);
    }

    public List<mItemRecyclerView> getItems() {
        return items;
    }

    public void setItems(List<mItemRecyclerView> items) {
        this.items = items;
    }
}
