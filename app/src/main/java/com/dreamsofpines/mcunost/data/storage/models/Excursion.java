package com.dreamsofpines.mcunost.data.storage.models;

/**
 * Created by ThePupsick on 03.03.2018.
 */

public class Excursion {
    private String id,name;
    private boolean isChecked;
    private int ratio;

    public Excursion(String id, String name, boolean isChecked,int ratio) {
        this.id = id;
        this.name = name;
        this.isChecked = isChecked;
        this.ratio = ratio;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public int getRatio() {
        return ratio;
    }

    public void setRatio(int ratio) {
        this.ratio = ratio;
    }
}
