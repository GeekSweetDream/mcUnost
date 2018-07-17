package com.dreamsofpines.mcunost.ui.adapters.recyclerCategory;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dreamsofpines.mcunost.R;
import com.dreamsofpines.mcunost.data.storage.models.InformExcursion;

import net.igenius.customcheckbox.CustomCheckBox;

import java.util.List;

import static android.media.CamcorderProfile.get;

/**
 * Created by ThePupsick on 04.08.17.
 */

public class ViewAdapter extends RecyclerView.Adapter {

//    private List<InformExcursion> mExcursionList;
    private List<InformExcursion> mCategory;
    private int currentPosition = -1;
    private Context mContext;

    public OnItemClickListener listener;


    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public ViewAdapter(List<InformExcursion> category,Context context) {
        this.mContext = context;
        mCategory = category;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_category_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        InformExcursion inf = mCategory.get(position);
        ((ViewHolder)holder).mTittle.setText(inf.getTittle());
        ((ViewHolder)holder).mCustomCheckBox.setChecked(inf.isCheck());
    }

    public List<InformExcursion> getCategory() {
        return mCategory;
    }

    public void updateCity(List<InformExcursion> list){
        this.mCategory = list;
    }

    @Override
    public int getItemCount() {
        return mCategory.size();
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mTittle;
        public CustomCheckBox mCustomCheckBox;

        public ViewHolder(final View itemView) {
            super(itemView);
            mTittle = (TextView) itemView.findViewById(R.id.tittle_category);
            mCustomCheckBox = (CustomCheckBox) itemView.findViewById(R.id.checkBox);
            mCustomCheckBox.setOnClickListener((view)->{
                if(currentPosition != -1) mCategory.get(currentPosition).setCheck(false);
                currentPosition = getAdapterPosition();
                mCategory.get(currentPosition).setCheck(true);
                notifyItemRangeChanged(0,mCategory.size());
                listener.onItemClick(currentPosition);
            });
        }

    }




}
