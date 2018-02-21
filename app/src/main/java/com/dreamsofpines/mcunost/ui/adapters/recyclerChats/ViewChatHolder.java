package com.dreamsofpines.mcunost.ui.adapters.recyclerChats;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dreamsofpines.mcunost.R;
import com.dreamsofpines.mcunost.data.storage.help.menu.ChatDialog;
import com.dreamsofpines.mcunost.ui.adapters.recyclerCategory.ViewAdapter;

/**
 * Created by ThePupsick on 17.01.2018.
 */

public class ViewChatHolder extends RecyclerView.ViewHolder {

    private TextView title,date,number;
    private ImageView notificate;

    public ViewChatHolder(final View itemView) {
        super(itemView);
        title = (TextView) itemView.findViewById(R.id.title_chat);
        date = (TextView) itemView.findViewById(R.id.date_order_chat);
        number = (TextView) itemView.findViewById(R.id.number_order_chat);
        notificate = (ImageView) itemView.findViewById(R.id.notification_chat);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ViewChatAdapter.listener != null)
                    ViewChatAdapter.listener.onItemClick(itemView,getLayoutPosition());
            }
        });

    }

    public void bindView(ChatDialog chatDialog){
        title.setText(chatDialog.getTitle());
        date.setText("Дата: "+chatDialog.getDate());
        number.setText("№ заказа: "+chatDialog.getNumber());
        notificate.setVisibility(chatDialog.isNewMes()?View.VISIBLE:View.INVISIBLE);
//        notificate.setVisibility(View.VISIBLE);
    }
}
