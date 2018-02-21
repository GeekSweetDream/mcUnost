package com.dreamsofpines.mcunost.data.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;
import android.widget.Toast;

import com.dreamsofpines.mcunost.data.storage.help.menu.InformExcursion;
import com.dreamsofpines.mcunost.data.storage.help.menu.Order;
import com.dreamsofpines.mcunost.data.storage.preference.GlobalPreferences;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ThePupsick on 04.08.17.
 */

public class MyDataBase extends SQLiteAssetHelper {
    private static final String DATABASE_NAME = "mcUnost.db";
    private static final int DATABASE_VERSION = 1;

    public MyDataBase(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public List<InformExcursion> getCategories(){
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        String [] sqSelect = {"0 _id", "name","path_image"};
        String sqlTables = "category";
        qb.setTables(sqlTables);
        Cursor cursor = qb.query(db,sqSelect,null,null,null,null,null);
        cursor.moveToFirst();
        final List<InformExcursion> excursions = new ArrayList<>();
        boolean fl = true;
        while(fl && cursor.getCount()!=0){
            excursions.add(new InformExcursion(cursor.getString(cursor.getColumnIndex("name")),
                    cursor.getString(cursor.getColumnIndex("path_image"))));
            fl = cursor.moveToNext();
        }
        return excursions;
    }

    public List<InformExcursion> getPackExcursion(String category,String region){
        Log.i("Myapp","Название до региона: "+ region);
        if(!region.equalsIgnoreCase("Москва") && !region.equalsIgnoreCase("Санкт-Петербург")){
            region = "Другой регион";
        }
        Log.i("Myapp","Название после региона: "+ region);
        String query = "select region.name,category.name, pack_excur.name_excursion, pack_excur.cost, " +
                "    pack_excur.day_count, pack_excur.description, pack_excur.short_description, pack_excur.path_image from un_category_pack_excur," +
                " un_pack_excur_region " +
                "    left join category" +
                "        on un_category_pack_excur.fk_categor = category._id" +
                "    left join pack_excur" +
                "        on un_category_pack_excur.fk_excur = pack_excur._id" +
                "    left join region" +
                "        on un_pack_excur_region.fk_region = region._id" +
                "    where (un_category_pack_excur.fk_excur = un_pack_excur_region.fk_pack_excur)\n" +
                "            and (region.name = \""+region+"\") \n" +
                "            and (category.name = \""+category+"\")";

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        Log.i("Myapp","Кол-во строк "+cursor.getCount());
        cursor.moveToFirst();
        final List<InformExcursion> excursions = new ArrayList<>();
        boolean fl = true;
//        while(fl && cursor.getCount() !=0){
//            excursions.add(new InformExcursion(cursor.getString(cursor.getColumnIndex("name_excursion")),
//                    ""+cursor.getInt(cursor.getColumnIndex("cost")),""+cursor.getInt(cursor.getColumnIndex("day_count")),
//                    cursor.getString(cursor.getColumnIndex("description")),cursor.getString(cursor.getColumnIndex("short_description")),
//                    cursor.getString(cursor.getColumnIndex("path_image"))));
//            fl = cursor.moveToNext();
//        }
        cursor.close();
        return excursions;
    }
    public List<String> getListExcursion(String namePack){
        String query = "SELECT pack_excur.name_excursion,\n" +
                "       excursion.name\n" +
                "  FROM un_pack_excur_excursion\n" +
                "       LEFT JOIN\n" +
                "       excursion ON un_pack_excur_excursion.fk_excursion = excursion._id\n" +
                "       LEFT JOIN\n" +
                "       pack_excur ON un_pack_excur_excursion.fk_pack_name = pack_excur._id\n" +
                "WHERE pack_excur.name_excursion = \""+namePack+"\"";

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        cursor.moveToFirst();
        Log.i("Myapp",""+cursor.getCount());
        final List<String> listExcursion = new ArrayList<>();
        boolean fl = true;

        while (fl && cursor.getCount()!=0){
            listExcursion.add(cursor.getString(cursor.getColumnIndex("name")));
            fl = cursor.moveToNext();
        }
        cursor.close();
        return listExcursion;
    }

    public void setNewOrderUser(String data, String cost, String teachers, String pupils, String tour){
        int idTour = getIdTour(tour);
        String query = "INSERT INTO order_user (fk_managers, cost, teachers, pupils, data, fk_tour)"+
                "VALUES (NULL,"+Integer.parseInt(cost)+","+Integer.parseInt(teachers)+","
                +Integer.parseInt(pupils)+",\""+data+"\","+idTour+")";
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(query);
        db.close();
    }

    private int getIdTour(String tour){
        String query = "select pack_excur._id from pack_excur "+
                        "where pack_excur.name_excursion = \""+tour+"\"";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        cursor.moveToFirst();
        int id = -1;
        if(cursor.getCount()!=0){
            id = cursor.getInt(cursor.getColumnIndex("_id"));
        }
        cursor.close();
        db.close();
        return id;
    }

    public List<Order> getOrdersUser(){
        String query = "SELECT pack_excur.name_excursion, order_user.data, order_user.pupils,\n" +
                "        order_user.teachers, order_user.cost, managers.name,\n" +
                "        managers.phone from order_user\n" +
                "        left join pack_excur\n" +
                "            on order_user.fk_tour = pack_excur._id \n" +
                "        left join managers\n" +
                "            on managers._id = order_user.fk_managers";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        cursor.moveToFirst();
        boolean fl = true;
        List<Order> ords = new ArrayList<>();
        Log.i("Myapp","заказов : "+cursor.getCount());
        while(fl && cursor.getCount()!=0){
//            ords.add(new Order(cursor.getString(cursor.getColumnIndex("name_excursion")),cursor.getString(cursor.getColumnIndex("data")),
//                    ""+cursor.getInt(cursor.getColumnIndex("cost")),""+cursor.getInt(cursor.getColumnIndex("pupils")),
//                    ""+cursor.getInt(cursor.getColumnIndex("teachers")),""+cursor.getString(cursor.getColumnIndex("name")),
//                    ""+cursor.getString(cursor.getColumnIndex("phone"))));
//            fl = cursor.moveToNext();
        }
        cursor.close();
        return ords;
    }

    public List<String> getDatesOrders(String tour){
        String query = "SELECT pack_excur.name_excursion, order_user.data from order_user\n" +
                "    left join pack_excur\n" +
                "        on order_user.fk_tour = pack_excur._id "+
                "     where pack_excur.name_excursion = \""+tour+"\"";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        cursor.moveToFirst();
        List<String> dates= new ArrayList<>();
        boolean fl = true;
        if(fl && cursor.getCount()!=0){
            dates.add(cursor.getString(cursor.getColumnIndex("data")));
            fl = cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return dates;
    }

}
//