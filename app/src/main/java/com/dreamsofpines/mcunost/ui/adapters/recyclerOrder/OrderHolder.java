package com.dreamsofpines.mcunost.ui.adapters.recyclerOrder;

import android.app.Activity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.dreamsofpines.mcunost.R;
import com.dreamsofpines.mcunost.data.storage.help.menu.Order;

/**
 * Created by ThePupsick on 20.08.17.
 */

public class OrderHolder extends RecyclerView.ViewHolder {
    private TextView date, cost, status,title;
    private Button mButton;
    public OrderHolder(final View itemView) {
        super(itemView);
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
        title.setText(order.getTour());
        date.setText(order.getDate());
        cost.setText(order.getCost()+" \u20BD");
        Log.i("Myapp",order.getManager());

        if(order.getManager().equalsIgnoreCase("null")){
            status.setText("В обработке");
        }else{
            status.setText("Принята");
            status.setTextColor(activity.getResources().getColor(R.color.md_green_400));
        }
    }
}
