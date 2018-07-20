package com.dreamsofpines.mcunost.ui.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.dreamsofpines.mcunost.data.storage.mItemRecyclerView;
import com.dreamsofpines.mcunost.data.storage.models.SaleItem;

import java.util.ArrayList;
import java.util.List;

public class AdapterOrders extends RecyclerView.Adapter {

    public final int TITLE_TEXT = 0;
    public final int ORDER_ITEM = 1;
    public final int SALE_ITEM = 2;
    public final int EMPTY_ITEM = 3;

    private AdapterDelegateManager<List<mItemRecyclerView>> mListAdapterDelegateManager;
    private List<mItemRecyclerView> items;

    private OnClickListener listener;

    public interface OnClickListener{
        void onClick(int id);
    }

    public void setListener(OnClickListener listener) {
        this.listener = listener;
    }

    public AdapterOrders(Activity activity){
        items = new ArrayList<>();
        addDelegates(activity);
    }

    private void addDelegates(Activity activity){
        mListAdapterDelegateManager = new AdapterDelegateManager<>();
        mListAdapterDelegateManager.addDelegate(TITLE_TEXT,new TextItemDelegate(activity));
        mListAdapterDelegateManager.addDelegate(ORDER_ITEM,new OrdersItemDelegate(activity).setListener((position)->listener.onClick(position)));
        mListAdapterDelegateManager.addDelegate(SALE_ITEM,new SaleItemDelegate(activity).setListener((id -> listener.onClick(id))));
        mListAdapterDelegateManager.addDelegate(EMPTY_ITEM,new EmptyDelegate(activity));
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
