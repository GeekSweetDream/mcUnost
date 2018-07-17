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

public class MessageFileItemDelegate extends AdapterDelegate<List<mItemRecyclerView>> {


    private LayoutInflater inflater;

    public MessageFileItemDelegate(Activity activity){
        inflater = activity.getLayoutInflater();
    }


    @Override
    protected boolean isForViewType(List<mItemRecyclerView> items, int position) {
        mItemRecyclerView item = items.get(position);
        return (item instanceof MessageTextItem) && ((MessageTextItem) item).isDock() ;
    }

    @Override
    protected RecyclerView.ViewHolder OnCreateViewHolder(ViewGroup parent) {
        return new MessageFileItemDelegateHolder(inflater.inflate(R.layout.item_mess_with_file,parent,false));
    }

    @Override
    protected void onBindViewHolder(List<mItemRecyclerView> items, int position, RecyclerView.ViewHolder holder) {
        MessageFileItemDelegateHolder messageTextItemDelegateHolder =
                (MessageFileItemDelegateHolder) holder;
        MessageTextItem item = (MessageTextItem) items.get(position);
        messageTextItemDelegateHolder.message.setText(item.getMess());
        messageTextItemDelegateHolder.time.setText(item.getTime());
        messageTextItemDelegateHolder.nameFile.setText(item.getNameFile());
    }

    public class MessageFileItemDelegateHolder extends RecyclerView.ViewHolder{

        private TextView message;
        private TextView time;
        private TextView nameFile;

        public MessageFileItemDelegateHolder(View itemView) {
            super(itemView);
            message = (TextView) itemView.findViewById(R.id.text_mess_txt);
            time = (TextView) itemView.findViewById(R.id.time_mess_txt);
            nameFile = (TextView) itemView.findViewById(R.id.name_file_txt);
        }
    }

}
