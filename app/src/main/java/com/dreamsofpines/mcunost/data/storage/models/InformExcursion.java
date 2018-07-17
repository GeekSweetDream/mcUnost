package com.dreamsofpines.mcunost.data.storage.models;

import java.util.List;

/**
 * Created by ThePupsick on 04.08.17.
 */

public class InformExcursion {
    private String tittle, description, shortDesc,city,count, day, nameImage,
            id, inTour, addService;
    private boolean check = false;

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public InformExcursion(String day) {
        this.tittle = "Day";
        this.day = day;
    }

    public InformExcursion(String tittle, String nameImage) {
        this.tittle = tittle;
        this.nameImage = nameImage;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public String getNameImage() {
        return nameImage;
    }

    public InformExcursion(String id,String tittle, String count, String day,String description,
                           String shortDesc, String nameImage, String inTour, String addService) {
        this.id = id;
        this.tittle = tittle;
        this.description = description;
        this.count = count;
        this.shortDesc = shortDesc;
        this.day = day;
        this.nameImage = nameImage;
        this.addService = addService;
        this.inTour = inTour;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getInTour() {
        return inTour;
    }

    public void setInTour(String inTour) {
        this.inTour = inTour;
    }

    public String getAddService() {
        return addService;
    }

    public void setAddService(String addService) {
        this.addService = addService;
    }
}
