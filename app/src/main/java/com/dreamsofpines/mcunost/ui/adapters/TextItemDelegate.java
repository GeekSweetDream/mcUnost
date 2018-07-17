package com.dreamsofpines.mcunost.ui.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dreamsofpines.mcunost.R;
import com.dreamsofpines.mcunost.data.storage.mItemRecyclerView;
import com.dreamsofpines.mcunost.data.storage.models.TextItem;

import org.w3c.dom.Text;

import java.util.List;

public class TextItemDelegate extends AdapterDelegate<List<mItemRecyclerView>> {

    private LayoutInflater inflater;

    public TextItemDelegate(Activity activity){
        inflater = activity.getLayoutInflater();
    }

    @Override
    protected boolean isForViewType(List<mItemRecyclerView> items, int position) {
        return items.get(position) instanceof TextItem;
    }

    @Override
    protected RecyclerView.ViewHolder OnCreateViewHolder(ViewGroup parent) {
        return new TextItemHolder(inflater.inflate(R.layout.item_text,parent,false));
    }

    @Override
    protected void onBindViewHolder(List<mItemRecyclerView> items, int position, RecyclerView.ViewHolder holder) {
        TextItemHolder textItemHolder = (TextItemHolder) holder;
        TextItem textItem = (TextItem) items.get(position);
        textItemHolder.text.setText(textItem.getText());
    }

    public class TextItemHolder extends RecyclerView.ViewHolder{

        public TextView text;

        public TextItemHolder(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.text);
        }
    }


}
