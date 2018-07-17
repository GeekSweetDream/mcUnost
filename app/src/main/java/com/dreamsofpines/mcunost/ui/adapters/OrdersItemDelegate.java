package com.dreamsofpines.mcunost.ui.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dreamsofpines.mcunost.R;
import com.dreamsofpines.mcunost.data.storage.mItemRecyclerView;
import com.dreamsofpines.mcunost.data.storage.models.OrderItem;

import java.util.List;

public class OrdersItemDelegate extends AdapterDelegate<List<mItemRecyclerView>> {

    private Context context;
    private LayoutInflater inflater;
    private OnClickListener listener;

    public interface OnClickListener{
        void onClick(int position);
    }

    public OnClickListener getListener() {
        return listener;
    }

    public OrdersItemDelegate setListener(OnClickListener listener) {
        this.listener = listener;
        return this;
    }

    public OrdersItemDelegate(Activity activity){
        this.inflater = activity.getLayoutInflater();
        this.context = activity.getApplicationContext();
    }


    @Override
    protected boolean isForViewType(List<mItemRecyclerView> items, int position) {
        return items.get(position) instanceof OrderItem;
    }

    @Override
    protected RecyclerView.ViewHolder OnCreateViewHolder(ViewGroup parent) {
        return new OrdersItemHolder(inflater.inflate(R.layout.order_item,parent,false)) ;
    }

    @Override
    protected void onBindViewHolder(List<mItemRecyclerView> items, int position, RecyclerView.ViewHolder holder) {
        OrdersItemHolder ordersItemHolder = (OrdersItemHolder) holder;
        OrderItem orderItem = (OrderItem) items.get(position);
        ordersItemHolder.itemView.setOnClickListener((view)->listener.onClick(position));
        ordersItemHolder.city.setText(orderItem.getCity());
        ordersItemHolder.date.setText(orderItem.getDate());
        ordersItemHolder.cost.setText(String.valueOf(orderItem.getCost())+"\u20BD"); // код знака - ₽
        ordersItemHolder.status.setText(orderItem.getStatus());
        ordersItemHolder.view.setImageResource(
                context.getResources().getIdentifier(
                        orderItem.getPathImage(), "mipmap",context.getPackageName())
        );
    }

    public class OrdersItemHolder extends RecyclerView.ViewHolder{
        public ImageView view;
        public TextView city;
        public TextView cost;
        public TextView date;
        public TextView status;

        public OrdersItemHolder(View itemView) {
            super(itemView);

            city = (TextView) itemView.findViewById(R.id.city_ord);
            date = (TextView) itemView.findViewById(R.id.date_ord);
            cost = (TextView) itemView.findViewById(R.id.cost_ord);
            status = (TextView) itemView.findViewById(R.id.status_ord);
            view = (ImageView) itemView.findViewById(R.id.image_ord);
        }
    }
}
