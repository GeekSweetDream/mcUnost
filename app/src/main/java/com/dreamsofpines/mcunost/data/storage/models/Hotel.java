package com.dreamsofpines.mcunost.data.storage.models;

/**
 * Created by ThePupsick on 24.02.2018.
 */

public class Hotel {

    private String name, pathImage, id;
    private boolean isChecked;

    public Hotel(String id, String name, String pathImage) {
        this.id = id;
        this.name = name;
        this.pathImage = pathImage;
        isChecked = false;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPathImage() {
        return pathImage;
    }

    public void setPathImage(String pathImage) {
        this.pathImage = pathImage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
