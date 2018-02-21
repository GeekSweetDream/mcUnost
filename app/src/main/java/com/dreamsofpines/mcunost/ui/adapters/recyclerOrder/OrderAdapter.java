package com.dreamsofpines.mcunost.ui.adapters.recyclerOrder;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dreamsofpines.mcunost.R;
import com.dreamsofpines.mcunost.data.storage.help.menu.Order;

import java.util.List;

/**
 * Created by ThePupsick on 20.08.17.
 */

public class OrderAdapter extends RecyclerView.Adapter<OrderHolder> {

    private List<Order> mOrders;
    private Activity mActivity;

    public static OnClickOrderListener mListener;

    public interface OnClickOrderListener{
        void OnClicked(View itemView,int position);
    }

    public void setOnClickOrderListener(OnClickOrderListener listener){
        mListener = listener;
    }

    public OrderAdapter(){};
    public void setOrderList(List<Order> orders) {mOrders = orders;}
    public void setActivity(Activity activity) {mActivity = activity;}

    @Override
    public OrderHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mActivity);
        View view = inflater.inflate(R.layout.item_order_recycler_my_order,parent,false);
        return new OrderHolder(view);
    }

    @Override
    public void onBindViewHolder(OrderHolder holder, int position) {
        Order order = mOrders.get(position);
        holder.bindHolder(order,mActivity);
    }

    @Override
    public int getItemCount() {
        return mOrders.size();
    }
}
