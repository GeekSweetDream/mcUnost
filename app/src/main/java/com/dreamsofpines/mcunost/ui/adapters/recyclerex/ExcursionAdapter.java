package com.dreamsofpines.mcunost.ui.adapters.recyclerex;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dreamsofpines.mcunost.R;
import com.dreamsofpines.mcunost.data.network.api.Constans;
import com.dreamsofpines.mcunost.data.storage.models.InformExcursion;
import com.squareup.picasso.Picasso;


import java.util.List;


/**
 * Created by ThePupsick on 05.08.17.
 */

public class ExcursionAdapter extends RecyclerView.Adapter {

    private Activity mActivity;
    private List<InformExcursion> mExcursionList;
    private int dayNow,cur,diff;
    public static OnItemTouchListener mListener;


    private static final int DAY_FOOTER = 0;
    private static final int PACK_EXCUR = 1;

    public interface OnItemTouchListener{
        void onTouched(View itemView,int position);
    }

    public void setOnTouchListener(OnItemTouchListener listener){
        this.mListener = listener;
    }

    public void setExcursionList(List<InformExcursion> excursionList){
        mExcursionList = excursionList;
    }

    public ExcursionAdapter(List<InformExcursion> excursionList) {
        mExcursionList = excursionList;
    }

    public void setActivity(Activity activity) {
        mActivity = activity;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == DAY_FOOTER){
            View view = LayoutInflater.from(mActivity).inflate(R.layout.item_day_pack,parent,false);
            return new DayHolder(view);
        }
        View view = LayoutInflater.from(mActivity).inflate(R.layout.fragment_excursion,parent,false);
        return new ExcursionHolder(view);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof  DayHolder){
            ((DayHolder) holder).bindView(mExcursionList.get(position).getDay());
        }else if(holder instanceof  ExcursionHolder){
            ((ExcursionHolder) holder).bindExcursion(mExcursionList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return mExcursionList.size();
    }

    @Override
    public int getItemViewType(int position) {
        int ans = PACK_EXCUR;
        if(mExcursionList.get(position).getTittle().equalsIgnoreCase("day")){
            ans = DAY_FOOTER;
        }
        return ans;
    }

    public class ExcursionHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView cost;
        public ImageView mImageView;
        public InformExcursion mInformExcursion;
        private Context mContext;

        public void setContex(Context context) {
            mContext = context;
        }

        public ExcursionHolder(final View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title_exc);
            cost = (TextView) itemView.findViewById(R.id.cost_exc);
            mImageView = (ImageView) itemView.findViewById(R.id.img_exc);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(ExcursionAdapter.mListener != null)
                        ExcursionAdapter.mListener.onTouched(itemView,getLayoutPosition());
                }
            });
        }

        public void bindExcursion(InformExcursion inf){
            mInformExcursion = inf;
            title.setText(mInformExcursion.getTittle());
            cost.setText(mInformExcursion.getCount()+" \u20BD");
            Picasso.with(mContext).load(Constans.URL.DOWNLOAD.GET_IMG+mInformExcursion.getNameImage()).into(mImageView);
        }
    }

    public class DayHolder extends RecyclerView.ViewHolder{

        private TextView day;

        public DayHolder(View itemView) {
            super(itemView);
            day = (TextView) itemView.findViewById(R.id.day_footer);
        }

        public void bindView(String countDay){
            day.setText(countDay+"-дневные");
        }
    }




}
