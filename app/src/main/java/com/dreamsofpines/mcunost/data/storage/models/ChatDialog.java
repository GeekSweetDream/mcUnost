package com.dreamsofpines.mcunost.data.storage.models;

/**
 * Created by ThePupsick on 17.01.2018.
 */

public class ChatDialog {
    private String title, date, number;
    private boolean newMes;

    public ChatDialog(String title, String date, String number, boolean newMes) {
        this.title = title;
        this.date = date;
        this.number = number;
        this.newMes = newMes;
    }

    public ChatDialog(String title, String date, String number) {
        this.title = title;
        this.date = date;
        this.number = number;
    }

    public boolean isNewMes() {
        return newMes;
    }

    public void setNewMes(boolean newMes) {
        this.newMes = newMes;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
