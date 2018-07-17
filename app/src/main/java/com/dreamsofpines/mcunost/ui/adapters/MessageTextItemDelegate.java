package com.dreamsofpines.mcunost.ui.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dreamsofpines.mcunost.R;
import com.dreamsofpines.mcunost.data.storage.mItemRecyclerView;
import com.dreamsofpines.mcunost.data.storage.models.MessageTextItem;

import java.util.List;

public class MessageTextItemDelegate extends AdapterDelegate<List<mItemRecyclerView>> {


    private LayoutInflater inflater;

    public  MessageTextItemDelegate(Activity activity){
        inflater = activity.getLayoutInflater();
    }


    @Override
    protected boolean isForViewType(List<mItemRecyclerView> items, int position) {
        mItemRecyclerView item = items.get(position);
        return (item instanceof MessageTextItem) && !((MessageTextItem) item).isDock() ;
    }

    @Override
    protected RecyclerView.ViewHolder OnCreateViewHolder(ViewGroup parent) {
        return new MessageTextItemDelegateHolder(inflater.inflate(R.layout.item_message_text,parent,false));
    }

    @Override
    protected void onBindViewHolder(List<mItemRecyclerView> items, int position, RecyclerView.ViewHolder holder) {
        MessageTextItemDelegateHolder messageTextItemDelegateHolder =
                (MessageTextItemDelegateHolder) holder;
        MessageTextItem item = (MessageTextItem) items.get(position);
        messageTextItemDelegateHolder.message.setText(item.getMess());
        messageTextItemDelegateHolder.time.setText(item.getTime());
    }

    public class MessageTextItemDelegateHolder extends RecyclerView.ViewHolder{

        private TextView message;
        private TextView time;


        public MessageTextItemDelegateHolder(View itemView) {
            super(itemView);
            message = (TextView) itemView.findViewById(R.id.text_mess_txt);
            time = (TextView) itemView.findViewById(R.id.time_mess_txt);
        }
    }

}
