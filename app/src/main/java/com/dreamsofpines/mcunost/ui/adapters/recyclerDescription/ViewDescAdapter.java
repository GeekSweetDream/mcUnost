package com.dreamsofpines.mcunost.ui.adapters.recyclerDescription;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dreamsofpines.mcunost.R;
import com.dreamsofpines.mcunost.Parser.StringParser;

import java.util.List;

/**
 * Created by ThePupsick on 24.08.17.
 */

public class ViewDescAdapter extends RecyclerView.Adapter {

    private List<String> description;
    private Context mContext;

    public static final int ITEM_VIEW_TITLE = 0;
    public static final int ITEM_VIEW_DESCRIPTION= 1;
    public static final int ITEM_VIEW_SMALL = 2;
    public static final int ITEM_VIEW_FOOTER = 3;

    public ViewDescAdapter(String description, Context context) {
        this.description = StringParser.getArrayStringFromText(description);
        this.mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == ITEM_VIEW_TITLE){
            View titleView = LayoutInflater.from(mContext).inflate(R.layout.item_day_recycler_inform_text,parent,false);
            return new ViewDayHolder(titleView);
        }else if(viewType == ITEM_VIEW_DESCRIPTION) {
            View descView = LayoutInflater.from(mContext).inflate(R.layout.item_descr_recycler_inform_text, parent, false);
            return new ViewDescHolder(descView);
        }else if(viewType == ITEM_VIEW_FOOTER){
            View footerView = LayoutInflater.from(mContext).inflate(R.layout.item_title_footer,parent,false);
            return new TitleHolder(footerView);
        }
        View smallView = LayoutInflater.from(mContext).inflate(R.layout.item_descr_small_inform_text, parent, false);
        return new ViewDescSmallHolder(smallView);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final int itemType = getItemViewType(position);
        if(itemType == ITEM_VIEW_TITLE){
            ((ViewDayHolder) holder).bindDay(description.get(position-1));
        }else if(itemType == ITEM_VIEW_DESCRIPTION){
            ((ViewDescHolder) holder).bindDesc(description.get(position-1));
        }else if(itemType == ITEM_VIEW_SMALL){
            ((ViewDescSmallHolder) holder).bindSmallDesc(description.get(position-1));
        }
    }


    @Override
    public int getItemViewType(int position) {
        if(position==0){
            return ITEM_VIEW_FOOTER;
        }
        int ans;
        int ch = StringParser.getNumbItemRecycler(description.get(position-1));
        if(1 == ch){
            ans = ITEM_VIEW_TITLE;
        }else if(2 == ch){
            ans = ITEM_VIEW_SMALL;
        }else{
            ans = ITEM_VIEW_DESCRIPTION;
        }
        return ans;
    }

    @Override
    public int getItemCount() {
        return description.size();
    }


    private class TitleHolder extends RecyclerView.ViewHolder{
        private TextView title;
        public TitleHolder(View itemView){
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title_footer);
        }
    }

    private class ViewDayHolder extends RecyclerView.ViewHolder {

        private TextView day;

        public ViewDayHolder(View itemView) {
            super(itemView);
            day = (TextView) itemView.findViewById(R.id.title_day_recycler);
        }

        public void bindDay(String quantityDay){
            day.setText(StringParser.getTitleDesc(quantityDay));
        }
    }



    private class ViewDescHolder extends RecyclerView.ViewHolder {
        private TextView title,descr;
        private ImageView ic;

        public ViewDescHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title_descr_recycler);
            descr = (TextView) itemView.findViewById(R.id.descr_descr_recycler);
            ic = (ImageView) itemView.findViewById(R.id.icon_tour_text_inform);
        }
        public void bindDesc(String str){
            String titleItem = StringParser.getTitleDesc(str);
            title.setText(titleItem);
            descr.setText(StringParser.getDescription(str));
            ic.setBackgroundResource(R.mipmap.ic_bus);
        }
    }

    private class ViewDescSmallHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private ImageView ic;

        public ViewDescSmallHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title_descr_small_recycler);
            ic = (ImageView) itemView.findViewById(R.id.icon_tour_small_text_inform);
        }

        public void bindSmallDesc(String str){
            String titleItem = StringParser.getTitleDesc(str);
            title.setText(titleItem);
            if(StringParser.isDinner(str)){
                ic.setBackgroundResource(R.mipmap.ic_din);
            }else{
                ic.setBackgroundResource(R.mipmap.ic_time);
            }
        }
    }


}
