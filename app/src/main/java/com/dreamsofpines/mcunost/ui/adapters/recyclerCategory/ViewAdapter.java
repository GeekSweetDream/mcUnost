package com.dreamsofpines.mcunost.ui.adapters.recyclerCategory;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dreamsofpines.mcunost.R;
import com.dreamsofpines.mcunost.data.storage.models.InformExcursion;

import java.util.List;

import static android.media.CamcorderProfile.get;

/**
 * Created by ThePupsick on 04.08.17.
 */

public class ViewAdapter extends RecyclerView.Adapter<ViewHolder> {

    private List<InformExcursion> mExcursionList;
    private List<InformExcursion> mCategory;
    private Context mContext;

    public static OnItemClickListener listener;


    public interface OnItemClickListener{
        void onItemClick(View itemView, int position);
    }

    public static void setOnItemListener(OnItemClickListener listener) {
        ViewAdapter.listener = listener;
    }

    public ViewAdapter(List<InformExcursion> category,Context context) {
        this.mContext = context;
        mCategory = category;
    }

    public void setContext(Context context) {
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.list_category_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        InformExcursion inf = mCategory.get(position);
        holder.setContext(mContext);
        holder.bindExcursion(inf);
    }

    public void updateCity(List<InformExcursion> list){
        this.mCategory = list;
    }

    @Override
    public int getItemCount() {
        return mCategory.size();
    }
}
