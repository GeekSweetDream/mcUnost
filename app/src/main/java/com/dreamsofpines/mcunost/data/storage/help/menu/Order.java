package com.dreamsofpines.mcunost.data.storage.help.menu;

/**
 * Created by ThePupsick on 20.08.17.
 */

public class Order {
    private String tour, date, cost, pupils,
            teachers, manager, phone, status,id;
    private int numberOrder;

    public Order(String tour, String date, String cost, String pupils, String teachers, String status, String manager, String phone) {
        this.tour = tour;
        this.date = date;
        this.cost = cost;
        this.pupils = pupils;
        this.teachers = teachers;
        this.manager = manager;
        this.phone = phone;
        this.status = status;
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

    public Order(String tour, String date, String pupils, String teachers) {
        this.tour = tour;
        this.date = date;
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

    public String getStatus() {
        return status;
    }

    public void setTour(String tour) {
        this.tour = tour;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public void setPupils(String pupils) {
        this.pupils = pupils;
    }

    public void setTeachers(String teachers) {
        this.teachers = teachers;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getNumberOrder() {
        return numberOrder;
    }

    public void setNumberOrder(int numberOrder) {
        this.numberOrder = numberOrder;
    }
}
