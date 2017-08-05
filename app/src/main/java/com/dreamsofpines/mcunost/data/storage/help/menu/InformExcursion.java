package com.dreamsofpines.mcunost.data.storage.help.menu;

import java.util.List;

/**
 * Created by ThePupsick on 04.08.17.
 */

public class InformExcursion {
    private String tittle, description, city;
    private String count, day;

//    public InformExcursion(String tittle, String city) {
//        this.tittle = tittle;
//        this.city = city;
//    }

    public InformExcursion(String tittle, String count) {
        this.tittle = tittle;
        this.count = count;
    }


    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCount() {
        return count;
    }

    public String getDay() {
        return day;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public void setDay(String day) {
        this.day = day;
    }
}
