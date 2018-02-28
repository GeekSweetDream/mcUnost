package com.dreamsofpines.mcunost.ui.adapters.recyclerHotel;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dreamsofpines.mcunost.R;
import com.dreamsofpines.mcunost.data.storage.help.menu.Hotel;
import com.dreamsofpines.mcunost.ui.adapters.recyclerOrder.OrderAdapter;
import com.dreamsofpines.mcunost.ui.fragments.DinnerFragment;

import java.util.List;

/**
 * Created by ThePupsick on 24.02.2018.
 */

public class HotelAdapter extends RecyclerView.Adapter<HotelHolder>{

    private List<Hotel> hotelList;
    private Context mContext;

    public static OnClickBoxListener boxListener;

    public interface OnClickBoxListener{
        void onClicked(View itemView, int position);
    }

    public void setOnClickBoxListener(OnClickBoxListener listener){
        this.boxListener = listener;
    }
    public HotelAdapter(){};

    public void setContext(Context context){
        this.mContext = context;
    }

    @Override
    public HotelHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_hotel_recycler,parent,false);
        return new HotelHolder(view);
    }

    @Override
    public void onBindViewHolder(HotelHolder holder, int position) {
        Hotel hotel = hotelList.get(position);
        holder.bindHolder(mContext,hotel.isChecked(),hotel.getName(),hotel.getPathImage(),position);
    }

    @Override
    public int getItemCount() {
        return hotelList.size();
    }

    public void setHotelList(List<Hotel> hotelList) {
        this.hotelList = hotelList;
    }

}
