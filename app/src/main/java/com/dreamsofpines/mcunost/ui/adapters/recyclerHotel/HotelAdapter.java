package com.dreamsofpines.mcunost.ui.adapters.recyclerHotel;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dreamsofpines.mcunost.R;
import com.dreamsofpines.mcunost.data.storage.models.Hotel;
import com.dreamsofpines.mcunost.ui.animation.ResizableWidthAnimation;
import com.dreamsofpines.mcunost.ui.customView.MyImageView;

import net.igenius.customcheckbox.CustomCheckBox;

import java.util.List;


/**
 * Created by ThePupsick on 24.02.2018.
 */

public class HotelAdapter extends RecyclerView.Adapter {

    private List<Hotel> hotelList;
    private Context mContext;
    private int curPosition;

    public static OnClickBoxListener boxListener;

    public interface OnClickBoxListener {
        void onClicked(int position);
    }

    public void setOnClickBoxListener(OnClickBoxListener listener) {
        this.boxListener = listener;
    }

    public HotelAdapter() {}

    public void setContext(Context context) {
        this.mContext = context;
    }

    @Override
    public HotelHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_hotel_recycler, parent, false);
        return new HotelHolder(view);
    }

    public List<Hotel> getHotelList() {
        return hotelList;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Hotel hotel = hotelList.get(position);
        ((HotelHolder)holder).txt.setText(hotel.getName());
        ((HotelHolder)holder).box.setChecked(hotel.isChecked());
    }

    @Override
    public int getItemCount() {
        return hotelList.size();
    }

    public void setHotelList(List<Hotel> hotelList) {
        this.hotelList = hotelList;
    }

    public class HotelHolder extends RecyclerView.ViewHolder {

        private CustomCheckBox box;
        private TextView txt;

        public HotelHolder(View itemView) {
            super(itemView);
            box = (CustomCheckBox) itemView.findViewById(R.id.checkBox);
            txt = (TextView) itemView.findViewById(R.id.title_hotel);
            box.setOnClickListener((view -> {
                if(curPosition!=-1)hotelList.get(curPosition).setChecked(false);
                curPosition = getAdapterPosition();
                hotelList.get(curPosition).setChecked(true);
                notifyItemRangeChanged(0,hotelList.size());
                boxListener.onClicked(curPosition);
            }));
        }
    }

    public int getCurPosition() {
        return curPosition;
    }

    public void setCurPosition(int curPosition) {
        this.curPosition = curPosition;
    }
}
