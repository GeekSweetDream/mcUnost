package com.dreamsofpines.mcunost.data.storage.help.menu;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ThePupsick on 18.01.2018.
 */

public class Message {

    private String mess,url;
    private Date date;
    private boolean isDoc;

    public Message(String mess, String url, Date date, boolean isDoc) {
        this.mess = mess;
        this.url = url;
        this.date = date;
        this.isDoc = isDoc;
    }

    public Message() {
    }

    public String getMess() {
        return mess;
    }

    public void setMess(String mess) {
        this.mess = mess;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getDate() {
        return date;
    }

    public String getStringDate(){
        SimpleDateFormat formatForDateNow = new SimpleDateFormat(" dd.MM.yyyy ");
        return formatForDateNow.format(date);
    }


    public void setDate(Date date) {
        this.date = date;
    }

    public void setLongDate(Long date){
        this.date = new Date(date);
    }

    public boolean isDoc() {
        return isDoc;
    }

    public void setDoc(boolean doc) {
        isDoc = doc;
    }
}

