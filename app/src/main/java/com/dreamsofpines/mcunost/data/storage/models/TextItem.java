package com.dreamsofpines.mcunost.data.storage.models;

import android.support.v4.app.Fragment;

import com.dreamsofpines.mcunost.data.storage.mItemRecyclerView;

public class TextItem extends Fragment implements mItemRecyclerView {

    private String text;

    public String getText() {
        return text;
    }

    public TextItem setText(String text) {
        this.text = text;
        return this;
    }
}
