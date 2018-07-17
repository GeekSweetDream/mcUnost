package com.dreamsofpines.mcunost.data.storage.models;

import android.content.Context;
import android.util.Log;

import com.dreamsofpines.mcunost.data.storage.preference.GlobalPreferences;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Date;
import java.util.List;

/**
 * Created by ThePupsick on 20.08.17.
 */

public class Order
{
    private String tour, cost, pupils, teachers, manager, phone, status,
            id, countDin, countBr, countLu,countBusMeet,countAllDayBus,count4Bus,
            idCity, hotel,countDay,idHotel,fromCity;
    private boolean addTrain = false;
    private List<Excursion> mExcursionList;
    private int numberOrder;
    private int addBusDays = 0;
    private Date dateTravelTourBegin;
    private Date dateTravelTourEnd;
    private Date dateCreate;
    private Context mContext;


    public Order(){}
    public Order(Context context){
        mContext = context;
    }

    public JSONObject getJson(){
        JSONObject js = new JSONObject();
        try {
            List<Excursion> mExcList = mExcursionList;
            JSONArray list = new JSONArray();
            for(Excursion exc: mExcList) {
                JSONObject jsObj = new JSONObject();
                jsObj.put("id",exc.getId());
                jsObj.put("name",exc.getName());
                list.put(jsObj);
            }
            js.put("nameTour",tour);
            js.put("idCity",idCity);
            js.put("list",list);
            js.put("cost",cost);
            js.put("fromCity",fromCity);
            js.put("quantityTeacher", teachers);
            js.put("quantityChildren", pupils);
            js.put("dateTravelTourBegin",dateTravelTourBegin);
            js.put("dateTravelTourEnd",dateTravelTourEnd);
            js.put("countBr",countBr);
            js.put("countDin",countDin);
            js.put("countLu",countLu);
            js.put("countMeetBus",countBusMeet);
            js.put("countAllDayBus",countAllDayBus);
            js.put("count4Bus",count4Bus);
            js.put("addTrain",addTrain?1:0);
            js.put("idCustomer", GlobalPreferences.getPrefIdUser(mContext));
            js.put("countDay",countDay);
            js.put("idHotel",idHotel);
            js.put("idStatus", 1);
            js.put("addBusDay",addBusDays);
        }catch (Exception e){
            Log.i("Order", "Error create json! Error text: "+e.getMessage());
        }
        return js;
    }


    public Context getContext() {
        return mContext;
    }

    public void setContext(Context context) {
        mContext = context;
    }

    public String getTour() {
        return tour;
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

    public List<Excursion> getExcursionList() {
        return mExcursionList;
    }

    public void setExcursionList(List<Excursion> excursionList) {
        mExcursionList = excursionList;
    }

    public String getCountDin() {
        return countDin;
    }

    public void setCountDin(int countDin) {
        this.countDin = String.valueOf(countDin);
    }

    public String getCountBr() {
        return countBr;
    }

    public void setCountBr(int countBr) {
        this.countBr = String.valueOf(countBr);
    }

    public String getCountLu() {
        return countLu;
    }

    public void setCountLu(int countLu) {
        this.countLu = String.valueOf(countLu);
    }

    public String getCountBusMeet() {
        return countBusMeet;
    }

    public void setCountBusMeet(String countBusMeet) {
        this.countBusMeet = countBusMeet;
    }

    public String getCountAllDayBus() {
        return countAllDayBus;
    }

    public void setCountAllDayBus(String countAllDayBus) {
        this.countAllDayBus = countAllDayBus;
    }

    public String getCount4Bus() {
        return count4Bus;
    }

    public void setCount4Bus(String count4Bus) {
        this.count4Bus = count4Bus;
    }

    public boolean isAddTrain() {
        return addTrain;
    }

    public void setAddTrain(boolean addTrain) {
        this.addTrain = addTrain;
    }

    public String getHotel() {
        return hotel;
    }

    public void setHotel(String hotel) {
        this.hotel = hotel;
    }

    public String getCountDay() {
        return countDay;
    }

    public void setCountDay(String countDay) {
        this.countDay = countDay;
    }

    public String getIdHotel() {
        return idHotel;
    }

    public void setIdHotel(String idHotel) {
        this.idHotel = idHotel;
    }

    public String getFromCity() {
        return fromCity;
    }

    public void setFromCity(String fromCity) {
        this.fromCity = fromCity;
    }

    public void setCountDin(String countDin) {
        this.countDin = countDin;
    }

    public void setCountBr(String countBr) {
        this.countBr = countBr;
    }

    public void setCountLu(String countLu) {
        this.countLu = countLu;
    }

    public String getIdCity() {
        return idCity;
    }

    public void setIdCity(String idCity) {
        this.idCity = idCity;
    }

    public void setAddBusDays(int days){
        addBusDays = days;
    }

    public int getAddBusDays() {
        return addBusDays;
    }

    public Date getDateTravelTourBegin() {
        return dateTravelTourBegin;
    }

    public void setDateTravelTourBegin(Date dateTravelTourBegin) {
        this.dateTravelTourBegin = dateTravelTourBegin;
    }

    public Date getDateTravelTourEnd() {
        return dateTravelTourEnd;
    }

    public void setDateTravelTourEnd(Date dateTravelTourEnd) {
        this.dateTravelTourEnd = dateTravelTourEnd;
    }

    public Date getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(Date dateCreate) {
        this.dateCreate = dateCreate;
    }
}
