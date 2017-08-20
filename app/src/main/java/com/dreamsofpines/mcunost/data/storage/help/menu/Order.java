package com.dreamsofpines.mcunost.data.storage.help.menu;

/**
 * Created by ThePupsick on 20.08.17.
 */

public class Order {
    private String tour, date, cost, pupils, teachers, manager, phone;

    public Order(String tour, String date, String cost, String pupils, String teachers, String manager, String phone) {
        this.tour = tour;
        this.date = date;
        this.cost = cost;
        this.pupils = pupils;
        this.teachers = teachers;
        this.manager = manager;
        this.phone = phone;
    }

    public Order(String tour, String date, String cost, String pupils, String teachers) {
        this.tour = tour;
        this.date = date;
        this.cost = cost;
        this.pupils = pupils;
        this.teachers = teachers;
        this.manager = "Неизвестно";
        this.phone = "Неизвестно";
    }

    public String getTour() {
        return tour;
    }

    public String getDate() {
        return date;
    }

    public String getCost() {
        return cost;
    }

    public String getPupils() {
        return pupils;
    }

    public String getTeachers() {
        return teachers;
    }

    public String getManager() {
        return manager;
    }

    public String getPhone() {
        return phone;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
