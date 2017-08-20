package com.dreamsofpines.mcunost.ui.adapters.recyclerCategory;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dreamsofpines.mcunost.R;
import com.dreamsofpines.mcunost.data.storage.help.menu.InformExcursion;

import java.util.List;

import static android.media.CamcorderProfile.get;

/**
 * Created by ThePupsick on 04.08.17.
 */

public class ViewAdapter extends RecyclerView.Adapter<ViewHolder> {

    private List<InformExcursion> mExcursionList;
    private Activity mActivity;
    public static OnItemClickListener listener;


    public interface OnItemClickListener{
        void onItemClick(View itemView, int position);
    }

    public static void setOnItemListener(OnItemClickListener listener) {
        ViewAdapter.listener = listener;
    }

    public ViewAdapter(List<InformExcursion> ExcursionList) {
        mExcursionList = ExcursionList ;
    }

    public void setActivity(Activity activity) {
        mActivity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mActivity);
        View view = layoutInflater.inflate(R.layout.list_category_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        InformExcursion inf = mExcursionList.get(position);
        holder.setContext(mActivity);
        holder.bindExcursion(inf);
    }

    @Override
    public int getItemCount() {
        return mExcursionList.size();
    }
}
