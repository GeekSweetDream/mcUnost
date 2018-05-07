package com.dreamsofpines.mcunost.ui.adapters.recyclerOrder;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.dreamsofpines.mcunost.R;
import com.dreamsofpines.mcunost.data.storage.models.Order;

/**
 * Created by ThePupsick on 20.08.17.
 */

public class OrderHolder extends RecyclerView.ViewHolder {
    private TextView date, cost, status,title,id;
    private Button mButton;
    public OrderHolder(final View itemView) {
        super(itemView);
        id = (TextView) itemView.findViewById(R.id.num_my_order);
        title = (TextView) itemView.findViewById(R.id.title_my_order);
        date = (TextView) itemView.findViewById(R.id.date_my_order);
        cost = (TextView) itemView.findViewById(R.id.cost_my_order);
        status = (TextView) itemView.findViewById(R.id.status_ord_my_order);
        mButton = (Button) itemView.findViewById(R.id.butt_read_more);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(OrderAdapter.mListener!=null){
                    OrderAdapter.mListener.OnClicked(itemView,getLayoutPosition());
                }
            }
        });
    }
    public void bindHolder(final Order order, Activity activity){
        id.setText(order.getId());
        title.setText(order.getTour());
        date.setText(order.getDateCreate());
        cost.setText(order.getCost()+"P");
        String st = order.getStatus();
        if(st.equalsIgnoreCase("в обработке")){
            status.setTextColor(activity.getResources().getColor(R.color.md_orange_400));
        }else if(st.equalsIgnoreCase("заказан")){
            status.setTextColor(activity.getResources().getColor(R.color.md_blue_400));
        }else if(st.equalsIgnoreCase("Отменен")){
            status.setTextColor(activity.getResources().getColor(R.color.md_red_600));
        }else if(st.equalsIgnoreCase("выполнен")){
            status.setTextColor(activity.getResources().getColor(R.color.md_green_400));
        }
        status.setText(st);
    }
}
