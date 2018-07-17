package com.dreamsofpines.mcunost.data.storage.models;

import android.app.Fragment;

import com.dreamsofpines.mcunost.data.storage.mItemRecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ThePupsick on 18.01.2018.
 */

public class MessageTextItem extends Fragment implements mItemRecyclerView {

    private String mess;
    private Date date;
    private boolean isDock;
    private String uri;
    private String nameFile;

    public String getMess() {
        return mess;
    }

    public MessageTextItem setMess(String mess) {
        this.mess = mess;
        return this;
    }

    public Date getDate() {
        return date;
    }

    public MessageTextItem setDate(Date date) {
        this.date = date;
        return this;
    }

    public MessageTextItem setLongDate(Long date){
        this.date = new Date(date);
        return this;
    }

    public String getNameFile() {
        return nameFile;
    }

    public MessageTextItem setNameFile(String nameFile) {
        this.nameFile = nameFile;
        return this;
    }

    public boolean isDock() {
        return isDock;
    }

    public MessageTextItem setDock(boolean dock) {
        isDock = dock;
        return this;
    }

    public String getUri() {
        return uri;
    }

    public MessageTextItem setUri(String uri) {
        this.uri = uri;
        return this;
    }

    public String getTime(){
        SimpleDateFormat formatForDateNow = new SimpleDateFormat(" HH:mm");
        return formatForDateNow.format(date);
    }

    public String getDateString(){
        SimpleDateFormat formatForDateNow = new SimpleDateFormat(" dd.MM.yyyy ");
        return formatForDateNow.format(date);
    }
}


