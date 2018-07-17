package com.dreamsofpines.mcunost.data.storage.models;

import android.app.Fragment;

import com.dreamsofpines.mcunost.data.storage.mItemRecyclerView;

public class SaleItem extends Fragment implements mItemRecyclerView {

    private String text;
    private String title;
    private int idSale;

    public int getIdSale() {
        return idSale;
    }

    public void setIdSale(int idSale) {
        this.idSale = idSale;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
