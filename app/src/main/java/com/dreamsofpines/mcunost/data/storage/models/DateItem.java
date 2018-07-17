package com.dreamsofpines.mcunost.data.storage.models;

import android.support.v4.app.Fragment;

import com.dreamsofpines.mcunost.data.storage.mItemRecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateItem extends Fragment implements mItemRecyclerView {
    private Date date;

    public Date getDate() {
        return date;
    }

    public DateItem setDate(Date date) {
        this.date = date;
        return this;
    }

    public String getStringDate() {
        SimpleDateFormat formatForDateNow = new SimpleDateFormat(" dd.MM.yyyy ");
        return formatForDateNow.format(date);
    }

}
